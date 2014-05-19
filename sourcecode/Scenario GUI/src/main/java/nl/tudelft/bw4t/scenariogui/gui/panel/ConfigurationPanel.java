package nl.tudelft.bw4t.scenariogui.gui.panel;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import nl.tudelft.bw4t.scenariogui.util.Format;

/**
 * The ConfigurationPanel class represents the left pane of the MainPanel.
 * It shows the options the user can configure.
 */
public class ConfigurationPanel extends JPanel {
	
	public static enum DEFAULT_VALUES {
		DEFAULT_IP("127.0.0.1"),
		DEFAULT_PORT("9000"),
		USE_GUI("true"),
		USE_GOAL("true"),
		MAP_FILE("");
		
		String value;
		
		DEFAULT_VALUES(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
		public int getIntValue() {
			return Integer.parseInt(value);
		}
		
		public boolean getBooleanValue() {
			return Boolean.parseBoolean(value);
		}
		
	}

    private JTextField clientIP = new JTextField(DEFAULT_VALUES.DEFAULT_IP.getValue(), 15);
    private JTextField clientPort = new JTextField(DEFAULT_VALUES.DEFAULT_PORT.getValue(), 6);
    {
    	Format.addIntegerDocumentFilterForTextField(clientPort);
    }
    private JTextField serverIP = new JTextField(DEFAULT_VALUES.DEFAULT_IP.getValue(), 15);
    private JTextField serverPort = new JTextField(DEFAULT_VALUES.DEFAULT_PORT.getValue(), 6);
    {
    	Format.addIntegerDocumentFilterForTextField(serverPort);
    }
    private JTextField mapFileTextField = new JTextField(DEFAULT_VALUES.MAP_FILE.getValue());
    
    CheckboxGroup goalCheckBox = new CheckboxGroup();
    private Checkbox goalYes = new Checkbox("Yes", DEFAULT_VALUES.USE_GOAL.getBooleanValue(), goalCheckBox);
    private Checkbox goalNo = new Checkbox("No", !DEFAULT_VALUES.USE_GOAL.getBooleanValue(), goalCheckBox);
    
    CheckboxGroup guiCheckBox = new CheckboxGroup();
    private Checkbox guiYes = new Checkbox("Yes", DEFAULT_VALUES.USE_GUI.getBooleanValue(), guiCheckBox);
    private Checkbox guiNo = new Checkbox("No", !DEFAULT_VALUES.USE_GUI.getBooleanValue(), guiCheckBox);

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
        
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        TitledBorder title = BorderFactory.createTitledBorder(loweredetched, "Configuration");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font("Sans-Serif", Font.BOLD, 16));
        setBorder(title);
        
        //showConfigLabel();
        showClientOptions();
        showServerOptions();
        showGoalOptions();
        showGuiOptions();
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
     * Show the client configuration options in the panel.
     */
    private void showClientOptions(){
        c.insets = new Insets(8, 8, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel client = new JLabel("Client");
        client.setFont(new Font("Sans-Serif", Font.BOLD, 14));
    	add(client, c);

        c.insets = new Insets(0,8,0,0);

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
     * Show the server configuration options in the panel.
     */
    private void showServerOptions(){
        c.insets = new Insets(8, 8, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel server = new JLabel("Server");
        server.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        add(server, c);

        c.insets = new Insets(0,8,0,0);

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
        c.insets = new Insets(8, 8, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel goal = new JLabel("Use GOAL");
        goal.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        add(goal, c);

        c.insets = new Insets(0,8,0,0);

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
        c.insets = new Insets(8, 8, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel gui = new JLabel("Launch GUI");
        gui.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        add(gui, c);

        c.insets = new Insets(0,8,0,0);

        c.gridx = 0;
        c.gridy += 1;
        add(guiYes, c);

        c.gridx = 1;
        add(guiNo, c);
    }
    
    /**
     * Show the options to add a map file in the panel.
     */
    private void showMapOptions(){
        c.insets = new Insets(8,8, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel map = new JLabel("Map File");
        map.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        add(map, c);

        c.insets = new Insets(4,8,8,8);

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
     * Sets the value of the text field of the client IP
     * @param clientIP The IP of the client
     */
    public void setClientIP(String clientIP) {
        this.clientIP.setText(clientIP);
    }
    
    /**
     * Returns the client port.
     * @return The client port.
     */
    public int getClientPort(){
    	return Integer.parseInt(clientPort.getText());
    }

    /**
     * Sets the value of the text field of the client port
     * @param clientPort The port of the client
     */
    public void setClientPort(String clientPort) {
        this.clientPort.setText(clientPort);
    }
    
    /**
     * Returns the server IP.
     * @return The server IP.
     */
    public String getServerIP(){
    	return serverIP.getText();
    }

    /**
     * Sets the value of the text field of the server IP
     * @param serverIP The IP of the server
     */
    public void setServerIP(String serverIP) {
        this.serverIP.setText(serverIP);
    }
    
    /**
     * Returns the server port.
     * @return The server port.
     */
    public int getServerPort(){
    	return Integer.parseInt(serverPort.getText());
    }

    /**
     * Sets the value of the text field of the server port
     * @param serverPort The port of the server
     */
    public void setServerPort(String serverPort) {
        this.serverPort.setText(serverPort);
    }
    
    /**
     * Returns if GOAL needs to be used.
     * @return The use of GOAL.
     */
    public boolean useGoal(){
    	if(goalCheckBox.getSelectedCheckbox() == goalYes)
    		return true;
    	else
    		return false;
    }
    
    /**
     * Sets if GOAL needs to be used.
     * @param The use of GOAL.
     */
    public void setUseGoal(boolean useGoal){
    	if(useGoal)
    		goalCheckBox.setSelectedCheckbox(goalYes);
    	else
    		goalCheckBox.setSelectedCheckbox(goalNo);
    }
    
    /**
     * Returns if a GUI needs to be displayed.
     * @return The use of a GUI.
     */
    public boolean useGui(){
    	if(guiCheckBox.getSelectedCheckbox() == guiYes)
    		return true;
    	else
    		return false;
    }
    
    /**
     * Sets if GOAL needs to be used.
     * @param The use of GOAL.
     */
    public void setUseGui(boolean useGui){
    	if(useGui)
    		guiCheckBox.setSelectedCheckbox(guiYes);
    	else
    		guiCheckBox.setSelectedCheckbox(guiNo);
    }

    /**
     * Returns the button to choose a map file
     * @return The button to choose a map file
     */
    public JButton getChooseMapFile() {
        return chooseMapFile;
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

    /**
     * Returns if changes has been made to the default configuration
     * @return whether changes have been made
     */
    public boolean isDefault() {
    	boolean isDefault = true;
    	
    	if(!this.getClientIP().equals(DEFAULT_VALUES.DEFAULT_IP.getValue()) && isDefault)
    		isDefault = false;
    	if(this.getClientPort() != DEFAULT_VALUES.DEFAULT_PORT.getIntValue() && isDefault)
    		isDefault = false;
    	if(!this.getServerIP().equals(DEFAULT_VALUES.DEFAULT_IP.getValue()) && isDefault)
    		isDefault = false;
    	if(this.getServerPort() != DEFAULT_VALUES.DEFAULT_PORT.getIntValue() && isDefault)
    		isDefault = false;
    	if(this.useGui() != DEFAULT_VALUES.USE_GUI.getBooleanValue() && isDefault)
    		isDefault = false;
    	if(this.useGoal() != DEFAULT_VALUES.USE_GOAL.getBooleanValue() && isDefault)
    		isDefault = false;
    	if(!this.getMapFile().equals(DEFAULT_VALUES.MAP_FILE.getValue()) && isDefault)
    		isDefault = false;
    	
    	//TODO: check if the bot list is empty (since that is default too)
    	
    	return isDefault;
    }
}
