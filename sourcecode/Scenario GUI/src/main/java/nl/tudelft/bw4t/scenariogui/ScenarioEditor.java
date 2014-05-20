package nl.tudelft.bw4t.scenariogui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import nl.tudelft.bw4t.scenariogui.controller.Controller;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.gui.panel.BotPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;


/**
 * The ScenarioEditor class serves as the Frame for the MenuBar and MainPanel.
 */
public class ScenarioEditor extends JFrame {

    /** Randomly generated serial version. */
    private static final long serialVersionUID = 3291131921268747169L;
    /** The name of the window, as displayed in the title. */
    private String windowName = "Scenario Editor";
    /** The <code>MainPanel</code> serving as the content pane.*/
    private MainPanel mPanel;
    /** The <code>MenuBar</code> at the top of the screen. */
    private MenuBar menuBar;
    /** The <code>Controller</code> containing all the ActionEvents. */
    private Controller controller;

    /**
     * Main function to start the ScenarioEditor.
     * @param args No arguments are required.
     */
    public static void main(final String[] args) {
        new ScenarioEditor();
    }

    /**
     * Create the scenario editor frame,
     * which will then hold the panels with specific functions.
     */
    public ScenarioEditor() {
        setLookAndFeel();
//      setSize(width, height);
        setTitle(windowName);

        setResizable(false);
        setLayout(null);

        // Attach the menu bar.
        menuBar = new MenuBar();
        setJMenuBar(menuBar);

        // Attach the MainPanel, consisting of the configuration- and botpanel.
        mPanel = new MainPanel(new ConfigurationPanel(), new BotPanel());
        setActivePane(mPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Gives window its size by inner components
        pack();

        // Setting the location relative to null centers the frame.
        setLocationRelativeTo(null);

        controller = new Controller(this);
        setVisible(true);
    }

    /**
     * Constructor where the panels are passed through as arguments.
     * Useful for testing when the panels have to be mocked or spied upon.
     * @param configurationPanel The ConfigurationPanel object used in the frame
     * @param botPanel The BotPanel to be used in the frame
     */
    public ScenarioEditor(final ConfigurationPanel configurationPanel,
            final BotPanel botPanel) {
        this();
        mPanel.setConfigurationPanel(configurationPanel);
        mPanel.setBotPanel(botPanel);

        // Recreate the controller.
        controller = new Controller(this);
    }

    /**
     * Set the content pane to the given panel.
     * This changes which panel is shown in the frame.
     * @param panel The panel to be shown.
     */
    protected final void setActivePane(final JPanel panel) {
        setContentPane(panel);
    }

    /**
     * Return the currently active content pane.
     * @return The JPanel that is currently the active content pane.
     */
    protected final JPanel getActivePane() {
        /*
         * Type cast it to JPanel since it cannot be anything other than
         * a JPanel due to the type check in the setActivePane method.
         */
        return (JPanel) getContentPane();
    }

    /**
     * Returns the main panel.
     * @return The main panel.
     */
    public final MainPanel getMainPanel() {
        return mPanel;
    }

    /**
     * Returns the menu bar.
     * @return The menu bar.
     */
    public final MenuBar getTopMenuBar() {
        return menuBar;
    }

    /**
     *  Function to set the look and feel of the frame to the default look and feel of the system.
     *  Throws exceptions which are passed over since the failure to set the look and feel is not
     *  considered harmful.
     */
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            // pass
        } catch (InstantiationException e) {
            // pass
        } catch (IllegalAccessException e) {
            // pass
        } catch (UnsupportedLookAndFeelException e) {
            // pass
        }
    }

    /**
     * Returns the controller object being used to handle all events on the GUI.
     * @return The Controller used.
     */
    public final Controller getController() {
        return controller;
    }
}
