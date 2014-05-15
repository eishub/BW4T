package nl.tudelft.bw4t;

import javax.swing.*;

import nl.tudelft.bw4t.controller.Controller;
import nl.tudelft.bw4t.gui.panel.BotPanel;
import nl.tudelft.bw4t.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.gui.panel.MainPanel;
import nl.tudelft.bw4t.gui.MenuBar;




/**
 *
 */
public class ScenarioEditor extends JFrame {

    /** The name of the window, as displayed in the title */
    private String windowName = "Scenario Editor";
    /** The window width */
    private int width;
    /** The window height */
    private int height;
    
    private MainPanel mPanel;
    private MenuBar menuBar;
    
    private Controller controller;
    
    /**
     * Main function to start the ScenarioEditor.
     * @param args No arguments are required.
     */
    public static void main(String[] args) {
        new ScenarioEditor();
    }

    /**
     * Create the scenario editor frame, which will then hold the panels with specific functions.
     */
    public ScenarioEditor() {
    	setLookAndFeel();
    //    setSize(width, height);
        setTitle(windowName);
        
        setResizable(false);
        setLayout(null);

        // Attach the menu bar.
        setJMenuBar(menuBar = new MenuBar());

        // Attach the MainPanel that consists of the configuration and the botpanel.
        mPanel = new MainPanel(new ConfigurationPanel(), new BotPanel());
        setActivePane(mPanel);

        

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //Gives window its size by inner components
        pack();
        
        //Set size for the centering of the frame
        width = this.getWidth();
        height = this.getHeight();
        // Setting the location relative to null centers the frame.
        setLocationRelativeTo(null);
        
        controller = new Controller(this);
        setVisible(true);
    }

    /**
     * Set the content pane to the given panel. This changes which panel is shown in the frame.
     * @param panel The panel to be shown.
     */
    protected void setActivePane(JPanel panel) {
        setContentPane(panel);
    }

    /**
     * Return the currently active content pane.
     * @return The JPanel that is currently the active content pane.
     */
    protected JPanel getActivePane() {
        /* Type cast it to JPanel since it cannot be anything other than a JPanel due to the type check
         * in the setActivePane method.
         */
        return (JPanel)getContentPane();
    }
    
    /**
     * Returns the main panel.
     * @return The main panel.
     */
    public MainPanel getMainPanel() {
    	return mPanel;
    }
    
    /**
     * Returns the menu bar.
     * @return The menu bar.
     */
    public MenuBar getTopMenuBar() {
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
    public Controller getController() {
        return controller;
    }
    
}
