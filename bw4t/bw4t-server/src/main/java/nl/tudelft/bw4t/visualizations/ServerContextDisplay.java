package nl.tudelft.bw4t.visualizations;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.tudelft.bw4t.BW4TBuilder;
import nl.tudelft.bw4t.controller.ServerMapController;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.environment.Launcher;
import nl.tudelft.bw4t.server.environment.Stepper;
import nl.tudelft.bw4t.view.MapRenderer;

import org.apache.log4j.Logger;

import repast.simphony.context.Context;
import eis.exceptions.ManagementException;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

/**
 * Used for directly displaying the simulation from the context, unlike BW4TRenderer does not use percepts and can show
 * all entities. Only used on the server side (BW4TEnvironment side).
 * <p>
 * Note, this renderer is largely independent of repast, so even though Repast has its own rendering tools we don't use
 * that.
 * <p>
 * Also note that this is a runnable and runs in its own thread with a refresh rate of 10Hz, started by
 * {@link BW4TBuilder}, see {@link #run()}.
 * 
 * @author trens
 * 
 * @modified W.Pasman 11mar13 pulled out the {@link ServerMapRenderer}.
 */
@SuppressWarnings("serial")
public class ServerContextDisplay extends JFrame {

	/**
	 * The log4j logger, logs to the console.
	 */
	private static final Logger LOGGER = Logger.getLogger(ServerContextDisplay.class);

	private final MapRenderer myRenderer;
	private final ServerMapController controller;

	/**
	 * Create a new instance of this class and initialize it
	 * 
	 * @param context
	 *            the central data model of Repast
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws FileNotFoundException
	 */
	public ServerContextDisplay(Context context) throws InstantiationException, IllegalAccessException,
	FileNotFoundException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			LOGGER.warn("failed to setup java look and feel", e);
		}
		setTitle("BW4T");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		controller = new ServerMapController(Launcher.getEnvironment().getMap(), context);
		myRenderer = new MapRenderer(controller);
		add(new JScrollPane(myRenderer), BorderLayout.CENTER);
		//		add(new ServerMapRenderer(context), BorderLayout.CENTER);
		add(new ControlPanel(this), BorderLayout.NORTH);
		pack();
		setVisible(true);
	}

	/**
	 * We close ourselves if user clicks on reset. We need to since we are created from {@link BW4TBuilder} which does
	 * not assume ownership; and nobody else can create us it seems.
	 */
	public void close() {
		myRenderer.getController().setRunning(false);
		setVisible(false);
	}

}

/**
 * local speed panel at top of the window.
 * 
 * @author W.Pasman
 * 
 */
@SuppressWarnings("serial")
class ControlPanel extends JPanel {

	/**
	 * The log4j logger, logs to the console.
	 */
	private static final Logger LOGGER = Logger.getLogger(ControlPanel.class);

	/**
	 * used to close the window when user presses reset.
	 */
	final ServerContextDisplay displayer;

	/**
	 * @param disp
	 *            is used to close the window when user presses reset.
	 * @throws FileNotFoundException
	 */
	public ControlPanel(ServerContextDisplay disp) throws FileNotFoundException {
		this.displayer = disp;
		setLayout(new BorderLayout());
		add(new JLabel("Speed"), BorderLayout.WEST);
		// slider goes in percentage, 100 is fastest
		final JSlider slider = new JSlider(0, 100, 90);
		final JButton resetbutton = new JButton("Reset");
		add(slider, BorderLayout.CENTER);
		add(resetbutton, BorderLayout.EAST);
		add(new MapSelector(displayer), BorderLayout.NORTH);

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				int sliderval = slider.getValue();
				// now we have speed (on Hz axis) and we need delay (s axis).
				// first get interpolated speed.
				double minf = 1. / Stepper.MAX_DELAY;
				double maxf = 1. / Stepper.MIN_DELAY;
				double f = minf + (maxf - minf) * sliderval / 100.;
				BW4TEnvironment.getInstance().setDelay((int) (1. / f));
			}
		});

		resetbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					// displayer.close(); now part of reset
					BW4TEnvironment.getInstance().reset();
				} catch (Exception e) {
					LOGGER.error("failed to reset the environment", e);
				}
			}
		});
	}
}

/**
 * This combo box allows user to select a new map. Doing that will reset the server and reload the new map.
 * <p>
 * We assume that a directory named "Maps" is available in the current directory, and that it only contains maps.
 * 
 * @author W.Pasman 13mar13
 * 
 */
@SuppressWarnings("serial")
class MapSelector extends JPanel {

	/**
	 * The log4j logger, logs to the console.
	 */
	private static final Logger LOGGER = Logger.getLogger(MapSelector.class);

	public MapSelector(final ServerContextDisplay displayer) throws FileNotFoundException {
		setLayout(new BorderLayout());
		add(new JLabel("Change Map"), BorderLayout.WEST);
		Vector<String> maps = getMaps();
		final JComboBox mapselector = new JComboBox(maps);

		// find the current map in the list and highlight it
		String mapname = BW4TEnvironment.getMapLocation();
		mapselector.setSelectedItem(mapname);

		add(mapselector, BorderLayout.CENTER);
		mapselector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Map<String, Parameter> parameters = new HashMap<String, Parameter>();
				parameters.put("map", new Identifier((String) mapselector.getSelectedItem()));
				try {
					// displayer.close(); now part of reset
					BW4TEnvironment.getInstance().reset(parameters);
				} catch (ManagementException e) {
					LOGGER.error("failed to reset the environment", e);
				}
			}
		});
	}

	/**
	 * get list of available map names.
	 * 
	 * @return vector with all available map names in the Maps directory.
	 * @throws FileNotFoundException
	 */
	private Vector<String> getMaps() throws FileNotFoundException {
		File f = new File(System.getProperty("user.dir") + "/maps");
		if (f.list() == null) {
			throw new FileNotFoundException("maps directory not found");
		}
		Vector<String> maps = new Vector<String>(Arrays.asList(f.list()));
		return maps;
	}
}
