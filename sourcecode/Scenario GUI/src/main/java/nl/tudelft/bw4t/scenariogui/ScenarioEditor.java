package nl.tudelft.bw4t.scenariogui;

import java.io.FileNotFoundException;

import javax.swing.*;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.controller.Controller;
import nl.tudelft.bw4t.scenariogui.gui.panel.BotPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;




/**
 * The ScenarioEditor class serves as the Frame for the MenuBar and MainPanel
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
     * Constructor where the panels are passed through as arguments. Useful for testing when the panels
     * have to be mocked or spied upon.
     * @param configurationPanel The ConfigurationPanel object used in the frame.
     * @param botPanel The BotPanel to be used in the frame.
     */
    public ScenarioEditor(ConfigurationPanel configurationPanel, BotPanel botPanel) {
        this();
        mPanel.setConfigurationPanel(configurationPanel);
        mPanel.setBotPanel(botPanel);

        // Recreate the controller.
        controller = new Controller(this);
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
     * @return The Controller used.
     */
    public Controller getController() {
        return controller;
    }
    
	/**
	 * 
	 * @param e contains the exception thrown by a method
	 * @param s is a description that is specific for the place the error occured
	 */
    public static void handleException(Exception e, String s){
    	if (e instanceof FileNotFoundException) {
			showDialog(e,s);			
		}
    	if (e instanceof JAXBException) {
			showDialog(e,s);			
		}
    	if (e instanceof ClassNotFoundException) {
			showDialog(e,s);				
		}
    	if (e instanceof InstantiationException) {
			showDialog(e,s);				
		}
    	if (e instanceof IllegalAccessException) {
			showDialog(e,s);				
		}
    	if (e instanceof UnsupportedLookAndFeelException) {
			showDialog(e,s);				
		}
    
    }
    
    /**
     * 
     * @param e contains the exception to print
     * @param s is a description of the error e that will be shown.
     */
    public static void showDialog(Exception e, String s){
    	
    	JOptionPane.showMessageDialog(null, s + "\n" + e.toString());
    }
}
