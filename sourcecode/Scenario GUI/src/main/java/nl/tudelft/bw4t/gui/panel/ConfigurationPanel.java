package nl.tudelft.bw4t.gui.panel;

import java.awt.*;
import javax.swing.*;

/**
 * The ConfigurationPanel class represents the left pane of the MainPanel.
 * It shows the options the user can configure.
 */
public class ConfigurationPanel extends JPanel {
	
    private JTextField clientIP = new JTextField();
    private JTextField clientPort = new JTextField();
    private JTextField serverIP = new JTextField();
    private JTextField serverPort = new JTextField();
    private JTextField agentClassFileTextField = new JTextField();
    private JTextField mapFileTextField = new JTextField();
    
    CheckboxGroup goalCheckBox = new CheckboxGroup();
    private Checkbox goalYes = new Checkbox("Yes", true, goalCheckBox);
    private Checkbox goalNo = new Checkbox("No", false, goalCheckBox);
    
    CheckboxGroup guiCheckBox = new CheckboxGroup();
    private Checkbox guiYes = new Checkbox("Yes", true, guiCheckBox);
    private Checkbox guiNo = new Checkbox("No", false, guiCheckBox);

    private JButton chooseAgentFile = new JButton("Open File");
    private JButton chooseMapFile = new JButton("Open File");
    
    protected JFileChooser fileChooser = new JFileChooser();
    
    /**
     * Create a ConfigurationPanel object.
     */
    public ConfigurationPanel() {
        setLayout(new GridLayout(0, 4));
        
        showConfigLabel();
        showClientOptions();
        showServerOptions();
        showGoalOptions();
        showGuiOptions();
        showAgentOptions();
        showMapOptions();
        
    }
    
    /**
     * Show the "Configuration" label in the panel.
     */
    private void showConfigLabel(){
    	add(new JLabel("Configuration")); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
    }
    
    /**
     * Show the client config options in the panel.
     */
    private void showClientOptions(){
    	add(new JLabel("Client")); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
        add(new JLabel("IP")); add(clientIP); add(new JLabel("Port")); add(clientPort);
    }
    
    /**
     * Show the serer config options in the panel.
     */
    private void showServerOptions(){
        add(new JLabel("Server")); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
        add(new JLabel("IP")); add(serverIP); add(new JLabel("Port")); add(serverPort);
    }
    
    /**
     * Show the option to use GOAL in the panel.
     */
    private void showGoalOptions(){
        add(new JLabel("Use GOAL")); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
        add(goalYes); add(goalNo); add(new JLabel(" ")); add(new JLabel(" "));
    }
    
    /**
     * Show the option to use a GUI in the panel.
     */
    private void showGuiOptions(){
        add(new JLabel("Launch GUI")); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
        add(guiYes); add(guiNo); add(new JLabel(" ")); add(new JLabel(" "));
    }
    
    /**
     * Show the option to add an agent class in the panel.
     */
    private void showAgentOptions(){
        add(new JLabel("Agent class")); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
        add(agentClassFileTextField); add(chooseAgentFile); add(new JLabel(" ")); add(new JLabel(" "));
    }
    
    /**
     * Show the options to add a map file in the panel.
     */
    private void showMapOptions(){
        add(new JLabel("Map file")); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
        add(mapFileTextField); add(chooseMapFile); add(new JLabel(" ")); add(new JLabel(" "));
    }
    
    /**
     * Returns the client IP.
     * @return The client IP.
     */
    public String getClientIP(){
    	return clientIP.getText();
    }
    
    /**
     * Returns the client port.
     * @return The client port.
     */
    public String getClientPort(){
    	return clientPort.getText();
    }
    
    /**
     * Returns the server IP.
     * @return The server IP.
     */
    public String getServerIP(){
    	return serverIP.getText();
    }
    
    /**
     * Returns the server port.
     * @return The server port.
     */
    public String getServerPort(){
    	return serverPort.getText();
    }
    
    /**
     * Returns if GOAL needs to be used.
     * @return The use of GOAL.
     */
    public boolean useGoal(){
    	return goalYes.isEnabled();
    }
    
    /**
     * Returns if a GUI needs to be displayed.
     * @return The use of a GUI.
     */
    public boolean useGui(){
    	return guiYes.isEnabled();
    }

    /**
     * Returns the button to choose an agent file
     * @return The button to choose an agent file
     */
    public JButton getChooseAgentFile() {
        return chooseAgentFile;
    }

    /**
     * Returns the button to choose a map file
     * @return The button to choose a map file
     */
    public JButton getChooseMapFile() {
        return chooseMapFile;
    }

    /**
     * Returns the path of the Agent Class file
     * @return The path of the Agent Class file
     */
    public String getAgentClassFile() {
        return agentClassFileTextField.getText();
    }

    /**
     * Sets the value of the text field to the path of the Agent Class file
     * @param agentClassFile The path of the Agent Class file
     */
    public void setAgentClassFile(String agentClassFile) {
        this.agentClassFileTextField.setText(agentClassFile);
    }

    /**
     * Returns the path to the Map file
     * @return The path to the Map file
     */
    public String getMapFile() {
        return mapFileTextField.getText();
    }

    /**
     * Sets the value of the text field to the path of the Map file
     * @param mapFile The path of the Map file
     */
    public void setMapFile(String mapFile) {
        this.mapFileTextField.setText(mapFile);
    }

    /**
     * Returns a File Chooser
     * @return A File Chooser
     */
    public JFileChooser getFileChooser() {
        return fileChooser;
    }
}
