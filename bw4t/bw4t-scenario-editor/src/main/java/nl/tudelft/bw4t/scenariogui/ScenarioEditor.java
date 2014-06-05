package nl.tudelft.bw4t.scenariogui;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.editor.controller.ScenarioEditorController;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.panel.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.util.DefaultOptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.OptionPrompt;

/**
 * The ScenarioEditor class serves as the Frame for the MenuBar and MainPanel.
 * <p>
 * @author      Calvin Wong Loi Sing
 * @author      Xander Zonneveld
 * @version     0.1                
 * @since       12-05-2014        
 */
public class ScenarioEditor extends JFrame {

    private static final long serialVersionUID = 3291131921268747169L;
    private static String windowName = "Scenario Editor";
    private MainPanel mPanel;
    private MenuBar menuBar;
    private ScenarioEditorController controller;
    private static OptionPrompt option = new DefaultOptionPrompt();

    /**
     * Create the scenario editor frame, which will then hold the panels with
     * specific functions.
     */
    public ScenarioEditor() {
        setLookAndFeel();
        setWindowTitle("Untitled");

        setResizable(false);
        setLayout(null);

        // Attach the menu bar.
        menuBar = new MenuBar();
        setJMenuBar(menuBar);

        // Attach the MainPanel, consisting of the configuration- and botpanel.
        mPanel = new MainPanel(new ConfigurationPanel(), new EntityPanel());
        setActivePane(mPanel);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Gives window its size by inner components
        pack();

        // Setting the location relative to null centers the frame.
        setLocationRelativeTo(null);

        controller = new ScenarioEditorController(this);
        setVisible(true);
    }

    /**
     * Constructor where the panels are passed through as arguments. Useful for
     * testing when the panels have to be mocked or spied upon.
     *
     * @param configurationPanel The ConfigurationPanel object used in the frame.
     * @param entityPanel        The EntityPanel object used in the frame.
     */
    public ScenarioEditor(final ConfigurationPanel configurationPanel, final EntityPanel entityPanel) {
        this();
        mPanel.setConfigurationPanel(configurationPanel);
        mPanel.setEntityPanel(entityPanel);

        // Recreate the controllers.
        controller = new ScenarioEditorController(this);
    }

    /**
     * Main function to start the ScenarioEditor.
     *
     * @param args No arguments are required.
     */
    public static void main(final String[] args) {
        new ScenarioEditor();
    }


    /**
     * Set the content pane to the given panel. This changes which panel is
     * shown in the frame.
     *
     * @param panel The panel to be shown.
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
     * Returns the controllers object being used to handle all events on the GUI.
     *
     * @return The Controller used.
     */
    public ScenarioEditorController getController() {
        return controller;
    }

    /**
     * Handles the exceptions that are catched.
     * 
     * @param e The exception that is thrown.
     * @param s A description that is specific for why the error occurred.
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
     * Opens a dialog showing the description of the error and the error itself as a String.
     * 
     * @param e The exception that is thrown.
     * @param s A description that is specific for why the error occurred.
     */
    public static void showDialog(final Exception e, final String s) {

        ScenarioEditor.option.showMessageDialog(null, s + "\n" + e.toString());
    }
    
    /**
     * Used to set the OptionPrompt for the Scenario GUI.
     * 
     * @param o The OptionPrompt object to set option to.
     */
    public static void setOptionPrompt(OptionPrompt o) {
        option = o;
    }
    
    /**
     * Used to get the OptionPrompt for the Scenario GUI
     * @return option Used to handle all thread blocking GUI objects.
     */
    public static OptionPrompt getOptionPrompt() {
        return option;
    }

    /**
     * Closes the ScenarioEditor window and all child frames.
     */
    public void closeScenarioEditor() {
        System.exit(0);
    }

    public void setWindowTitle(String filenameBeingEdited) {
        setTitle(windowName + " - " + filenameBeingEdited);
    }
}
