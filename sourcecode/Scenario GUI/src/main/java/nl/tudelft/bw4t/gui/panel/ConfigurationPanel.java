package nl.tudelft.bw4t.gui.panel;

import java.awt.*;
import javax.swing.*;

/**
 * The ConfigurationPanel class represents the left pane of the MainPanel.
 * It shows the options the user can configure.
 */
public class ConfigurationPanel extends JPanel {


    private JTextField clientIP = new JTextField("127.0.0.1", 15);
    private JTextField clientPort = new JTextField("9000", 6);
    private JTextField serverIP = new JTextField("127.0.0.1", 15);
    private JTextField serverPort = new JTextField("9000", 6);
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

    private GridBagConstraints c;

    /**
     * Create a ConfigurationPanel object.
     */
    public ConfigurationPanel() {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;

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
        c.gridx = 0;
        c.gridy = 0;
        c.weightx  = 1;
    	add(new JLabel("Configuration"), c);
    }
    
    /**
     * Show the client config options in the panel.
     */
    private void showClientOptions(){
        c.insets = new Insets(10, 0, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel client = new JLabel("Client");
        client.setFont(new Font("Sans-Serif", Font.BOLD, 14));
    	add(client, c);

        c.insets = new Insets(0,0,0,0);

        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy += 1;
        add(new JLabel("IP"), c);

        c.gridx = 1;
        c.weightx = 2;
        c.weighty = 1;
        add(clientIP, c);

        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy += 1;
        add(new JLabel("Port"), c);

        c.gridx = 1;
        c.weightx = 2;
        c.weighty = 1;
        add(clientPort, c);
    }
    
    /**
     * Show the serer config options in the panel.
     */
    private void showServerOptions(){
        c.insets = new Insets(10, 0, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel server = new JLabel("Server");
        server.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        add(server, c);

        c.insets = new Insets(0,0,0,0);

        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy += 1;
        add(new JLabel("IP"), c);

        c.gridx = 1;
        c.weightx = 2;
        c.weighty = 1;
        add(serverIP, c);

        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy += 1;
        add(new JLabel("Port"), c);

        c.gridx = 1;
        c.weightx = 2;
        c.weighty = 1;
        add(serverPort, c);
    }
    
    /**
     * Show the option to use GOAL in the panel.
     */
    private void showGoalOptions(){
        c.insets = new Insets(10, 0, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel goal = new JLabel("Use GOAL");
        goal.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        add(goal, c);

        c.insets = new Insets(0,0,0,0);

        c.gridx = 0;
        c.gridy += 1;
        add(goalYes, c);

        c.gridx = 1;
        add(goalNo, c);
    }
    
    /**
     * Show the option to use a GUI in the panel.
     */
    private void showGuiOptions(){
        c.insets = new Insets(10, 0, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel gui = new JLabel("Launch GUI");
        gui.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        add(gui, c);

        c.insets = new Insets(0,0,0,0);

        c.gridx = 0;
        c.gridy += 1;
        add(guiYes, c);

        c.gridx = 1;
        add(guiNo, c);
    }
    
    /**
     * Show the option to add an agent class in the panel.
     */
    private void showAgentOptions(){
        c.insets = new Insets(10, 0, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel agent = new JLabel("Agent Class File");
        agent.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        add(agent, c);

        c.insets = new Insets(4,0,0,0);

        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy += 1;
        add(agentClassFileTextField, c);

        agentClassFileTextField.setPreferredSize(chooseAgentFile.getPreferredSize());

        c.gridx = 2;
        c.gridwidth = 1;
        add(chooseAgentFile, c);

    }
    
    /**
     * Show the options to add a map file in the panel.
     */
    private void showMapOptions(){
        c.insets = new Insets(10, 0, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel map = new JLabel("Map File");
        map.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        add(map, c);

        c.insets = new Insets(4,0,0,0);

        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy += 1;
        add(mapFileTextField, c);

        mapFileTextField.setPreferredSize(chooseMapFile.getPreferredSize());

        c.gridx = 2;
        c.gridwidth = 1;
        add(chooseMapFile, c);
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
