package nl.tudelft.bw4t.scenariogui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import nl.tudelft.bw4t.scenariogui.editor.controller.ScenarioEditorController;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.util.DefaultOptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.OptionPrompt;

/**
 * The ScenarioEditor class serves as the Frame for the MenuBar and MainPanel.     
 */
public class ScenarioEditor extends JFrame {

    private static final long serialVersionUID = 3291131921268747169L;

    private String windowName = "Scenario Editor";

    private MainPanel mPanel;
    
    private MenuBar menuBar;
    
    private ScenarioEditorController controller;
    
    private BW4TClientConfig model;
    
    private static OptionPrompt option = new DefaultOptionPrompt();

    /**
     * Create the scenario editor frame, which will then hold the panels with
     * specific functions.
     */
    public ScenarioEditor() {
        setLookAndFeel();

        model = new BW4TClientConfig();
        
        setWindowTitle("Untitled");

        setResizable(false);
        setLayout(null);

        // Attach the menu bar.
        menuBar = new MenuBar();
        setJMenuBar(menuBar);

        // Attach the MainPanel, consisting of the configuration- and botpanel.
        mPanel = new MainPanel(this, new ConfigurationPanel(), new EntityPanel());
        setActivePane(mPanel);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Gives window its size by inner components
        pack();

        // Setting the location relative to null centers the frame.
        setLocationRelativeTo(null);

        controller = new ScenarioEditorController(this, model);
        setVisible(true);
    }

    /**
     * Constructor where the panels are passed through as arguments. Useful for
     * testing when the panels have to be mocked or spied upon.
     *
     * @param configurationPanel The ConfigurationPanel object used in the frame.
     * @param entityPanel        The EntityPanel object used in the frame.
     * @param model              The BW4TClientConfig object.
     */
    public ScenarioEditor(final ConfigurationPanel configurationPanel, 
            final EntityPanel entityPanel, BW4TClientConfig model) {
        this();
        mPanel.setConfigurationPanel(configurationPanel);
        mPanel.setEntityPanel(entityPanel);

        // Recreate the controllers.
        controller = new ScenarioEditorController(this, model);
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

    public final MainPanel getMainPanel() {
        return mPanel;
    }

    public final MenuBar getTopMenuBar() {
        return menuBar;
    }

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
        showDialog(e, s);
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
    
    public static void setOptionPrompt(OptionPrompt o) {
        option = o;
    }
    
    public static OptionPrompt getOptionPrompt() {
        return option;
    }

    /**
     * Closes the ScenarioEditor window and all child frames.
     */
    public void closeScenarioEditor() {
        // This exit call is used because the child frames won't be closed otherwise. 
        System.exit(0);
    }

    public void setWindowTitle(String filenameBeingEdited) {
        setTitle(windowName + " - " + filenameBeingEdited);
    }
}
