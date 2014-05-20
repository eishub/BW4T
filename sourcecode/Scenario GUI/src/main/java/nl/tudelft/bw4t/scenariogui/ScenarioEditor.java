package nl.tudelft.bw4t.scenariogui;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.controller.Controller;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * The ScenarioEditor class serves as the Frame for the MenuBar and MainPanel.
 */
public class ScenarioEditor extends JFrame {

    /** Randomly generated serial version. */
    private static final long serialVersionUID = 3291131921268747169L;
    /** The name of the window, as displayed in the title. */
    private String windowName = "Scenario Editor";
    /** The <code>MainPanel</code> serving as the content pane. */
    private MainPanel mPanel;
    /** The <code>MenuBar</code> at the top of the screen. */
    private MenuBar menuBar;
    /** The <code>Controller</code> containing all the ActionEvents. */
    private Controller controller;
    
    /**
     * Create the scenario editor frame, which will then hold the panels with
     * specific functions.
     */
    public ScenarioEditor() {
        setLookAndFeel();
        // setSize(width, height);
        setTitle(windowName);

        setResizable(false);
        setLayout(null);

        // Attach the menu bar.
        menuBar = new MenuBar();
        setJMenuBar(menuBar);

        // Attach the MainPanel, consisting of the configuration- and botpanel.
        mPanel = new MainPanel(new ConfigurationPanel(), new EntityPanel());
        setActivePane(mPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Gives window its size by inner components
        pack();

        // Setting the location relative to null centers the frame.
        setLocationRelativeTo(null);

        controller = new Controller(this);
        setVisible(true);
    }

    /**
     * Constructor where the panels are passed through as arguments. Useful for
     * testing when the panels have to be mocked or spied upon.
     *
     * @param configurationPanel The ConfigurationPanel object used in the frame
     * @param entityPanel The EntityPanel object used in the frame
     */

    public ScenarioEditor(final ConfigurationPanel configurationPanel, final EntityPanel entityPanel) {
        this();
        mPanel.setConfigurationPanel(configurationPanel);
        mPanel.setEntityPanel(entityPanel);

        // Recreate the controller.
        controller = new Controller(this);
    }

    /**
     * Main function to start the ScenarioEditor.
     *
     * @param args
     *            No arguments are required.
     */
    public static void main(final String[] args) {
        new ScenarioEditor();
    }
    
    //TODO: remove below method
    /**
     * Paints the background of the GUI.
     * @param g The graphics.
     */
    @Override
    public void paint(Graphics g) {
        if (new Random().nextInt(7) == 0) {
            try {
                g.drawImage(ImageIO.read(new File(System.getProperty("user.dir")
                        + "/src/main/resources/boss.jpg")), 0, 50, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        super.paint(g);
    }

    /**
     * Set the content pane to the given panel. This changes which panel is
     * shown in the frame.
     *
     * @param panel
     *            The panel to be shown.
     */
    protected final void setActivePane(final JPanel panel) {
        setContentPane(panel);
    }

    /**
     * Return the currently active content pane.
     *
     * @return The JPanel that is currently the active content pane.
     */
    protected final JPanel getActivePane() {
        /*
         * Type cast it to JPanel since it cannot be anything other than a
         * JPanel due to the type check in the setActivePane method.
         */
        return (JPanel) getContentPane();
    }

    /**
     * Returns the main panel.
     *
     * @return The main panel.
     */
    public final MainPanel getMainPanel() {
        return mPanel;
    }

    /**
     * Returns the menu bar.
     *
     * @return The menu bar.
     */
    public final MenuBar getTopMenuBar() {
        return menuBar;
    }

    /**
     * Function to set the look and feel of the frame to the default look and
     * feel of the system. Throws exceptions which are passed over since the
     * failure to set the look and feel is not considered harmful.
     */
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            handleException(e, "Error: Class has not been found");
        } catch (InstantiationException e) {
            handleException(e, "Error: Instantiaton could not be done");
        } catch (IllegalAccessException e) {
            handleException(e, "Error: Illegal Access");
        } catch (UnsupportedLookAndFeelException e) {
            handleException(e, "Error: Unsupported LookAndFeel");
        }
    }

    /**
     * Returns the controller object being used to handle all events on the GUI.
     *
     * @return The Controller used.
     */
    public final Controller getController() {
        return controller;
    }

    /**
     *
     * @param e
     *            contains the exception thrown by a method
     * @param s
     *            is a description that is specific for the place the error
     *            occured.
     */
    public static void handleException(final Exception e, final String s) {
        if (e instanceof FileNotFoundException) {
            showDialog(e, s);
        }
        if (e instanceof JAXBException) {
            showDialog(e, s);
        }
        if (e instanceof ClassNotFoundException) {
            showDialog(e, s);
        }
        if (e instanceof InstantiationException) {
            showDialog(e, s);
        }
        if (e instanceof IllegalAccessException) {
            showDialog(e, s);
        }
        if (e instanceof UnsupportedLookAndFeelException) {
            showDialog(e, s);
        }

    }

    /**
     *
     * @param e
     *            contains the exception to print
     * @param s
     *            is a description of the error e that will be shown.
     */
    public static void showDialog(final Exception e, final String s) {

        JOptionPane.showMessageDialog(null, s + "\n" + e.toString());
    }
}
