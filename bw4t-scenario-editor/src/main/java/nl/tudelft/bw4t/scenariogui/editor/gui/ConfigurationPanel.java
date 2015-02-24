package nl.tudelft.bw4t.scenariogui.editor.gui;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;

import nl.tudelft.bw4t.scenariogui.DefaultConfigurationValues;
import nl.tudelft.bw4t.scenariogui.util.FileFilters;
import nl.tudelft.bw4t.scenariogui.util.Format;
import nl.tudelft.bw4t.scenariogui.util.MapSpec;

/**
 * The ConfigurationPanel class represents the left pane of the MainPanel. It
 * shows the options the user can configure.
 * 
 */
public class ConfigurationPanel extends JPanel {
    
    private static final long serialVersionUID = 2925174902776539436L;

    private static final String FONT_NAME = "Sans-Serif";

    private static final int INSET = 8;

    private static final double GRID_BAG_CONSTRAINTS_WEIGHT = 0.5;

    private static final int TEXT_FIELD_COLUMN_SIZE_BIG = 15;

    private static final int TEXT_FIELD_COLUMN_SIZE_SMALL = 6;

    private MapSpec mapSpec;

    private JTextField clientIP = new JTextField(
            DefaultConfigurationValues.DEFAULT_CLIENT_IP.getValue(),
            TEXT_FIELD_COLUMN_SIZE_BIG);

    private JTextField clientPort = new JTextField(
            DefaultConfigurationValues.DEFAULT_CLIENT_PORT.getValue(),
            TEXT_FIELD_COLUMN_SIZE_SMALL);
    {
        Format.addIntegerDocumentFilterForTextField(clientPort);
    }

    private JTextField serverIP = new JTextField(
            DefaultConfigurationValues.DEFAULT_SERVER_IP.getValue(),
            TEXT_FIELD_COLUMN_SIZE_BIG);

    private JTextField serverPort = new JTextField(
            DefaultConfigurationValues.DEFAULT_SERVER_PORT.getValue(),
            TEXT_FIELD_COLUMN_SIZE_SMALL);
    {
        Format.addIntegerDocumentFilterForTextField(serverPort);
    }

    private JTextField mapFileTextField = new JTextField(
            DefaultConfigurationValues.MAP_FILE.getValue());

    private CheckboxGroup guiCheckBox = new CheckboxGroup();

    private Checkbox guiYes = new Checkbox("Yes",
            DefaultConfigurationValues.USE_GUI.getBooleanValue(), guiCheckBox);

    private Checkbox guiNo = new Checkbox("No",
            !DefaultConfigurationValues.USE_GUI.getBooleanValue(), guiCheckBox);

    private CheckboxGroup pathsCheckBox = new CheckboxGroup();

    private Checkbox pathsYes = new Checkbox("Yes",
            DefaultConfigurationValues.USE_GUI.getBooleanValue(), pathsCheckBox);

    private Checkbox pathsNo = new Checkbox("No",
            !DefaultConfigurationValues.USE_GUI.getBooleanValue(), pathsCheckBox);

    private CheckboxGroup collisionsCheckBox = new CheckboxGroup();

    private Checkbox collisionsYes = new Checkbox("Yes",
            DefaultConfigurationValues.USE_GUI.getBooleanValue(), collisionsCheckBox);

    private Checkbox collisionsNo = new Checkbox("No",
            !DefaultConfigurationValues.USE_GUI.getBooleanValue(), collisionsCheckBox);

    private JButton chooseMapFile = new JButton("Open File");

    private JFileChooser fileChooser;

    private GridBagConstraints c;

    private static final int FONT_SIZE = 16;

    private static final int FONT_SIZE_SMALL = 14;

    private String oldClientIP = clientIP.getText();

    private String oldClientPort = clientPort.getText();

    private String oldServerIP = serverIP.getText();

    private String oldServerPort = serverPort.getText();

    private Checkbox oldGui = guiCheckBox.getSelectedCheckbox();

    private String oldMapFile = mapFileTextField.getText();

    /**
     * Create a ConfigurationPanel object.
     */
    public ConfigurationPanel() {
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(FileFilters.mapFilter());
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;

        Border loweredetched = BorderFactory
                .createEtchedBorder(EtchedBorder.LOWERED);
        TitledBorder title = BorderFactory.createTitledBorder(loweredetched,
                "Configuration ");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));
        setBorder(title);

        mapSpec = new MapSpec(DefaultConfigurationValues.MAP_FILE.getValue());

        showClientOptions();
        showServerOptions();
        showGuiOptions();
        visualizePathsOptions();
        enableCollisionsOptions();
        showMapOptions();

    }

    private void showClientOptions() {
        c.insets = new Insets(INSET, INSET, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel client = new JLabel("Client");
        client.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE_SMALL));
        add(client, c);

        c.insets = new Insets(0, INSET, 0, 0);

        showClientBoxContent();
    }

    private void showClientBoxContent() {
        c.weightx = GRID_BAG_CONSTRAINTS_WEIGHT;
        c.gridx = 0;
        c.gridy += 1;
        add(new JLabel("IP "), c);

        c.gridx = 1;
        c.weightx = 2;
        c.weighty = 1;
        add(clientIP, c);

        c.weightx = GRID_BAG_CONSTRAINTS_WEIGHT;
        c.gridx = 0;
        c.gridy += 1;
        add(new JLabel("Port "), c);

        c.gridx = 1;
        c.weightx = 2;
        c.weighty = 1;
        add(clientPort, c);
    }

    private void showServerOptions() {
        c.insets = new Insets(INSET, INSET, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel server = new JLabel("Server");
        server.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE_SMALL));
        add(server, c);

        showServerBoxContent();
    }

    private void showServerBoxContent() {
        c.insets = new Insets(0, INSET, 0, 0);

        c.weightx = GRID_BAG_CONSTRAINTS_WEIGHT;
        c.gridx = 0;
        c.gridy += 1;
        add(new JLabel("IP"), c);

        c.gridx = 1;
        c.weightx = 2;
        c.weighty = 1;
        add(serverIP, c);

        c.weightx = GRID_BAG_CONSTRAINTS_WEIGHT;
        c.gridx = 0;
        c.gridy += 1;
        add(new JLabel("Port"), c);

        c.gridx = 1;
        c.weightx = 2;
        c.weighty = 1;
        add(serverPort, c);
    }

    private void showGuiOptions() {
        c.insets = new Insets(INSET, INSET, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel gui = new JLabel("Launch GUI");
        gui.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE_SMALL));
        add(gui, c);

        c.insets = new Insets(0, INSET, 0, 0);

        c.gridx = 0;
        c.gridy += 1;
        add(guiYes, c);

        c.gridx = 1;
        add(guiNo, c);
    }

    private void visualizePathsOptions() {
        c.insets = new Insets(INSET, INSET, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel gui = new JLabel("Visualize Paths");
        gui.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE_SMALL));
        add(gui, c);

        c.insets = new Insets(INSET / 2, INSET, INSET, INSET);

        c.gridx = 0;
        c.gridy += 1;
        add(pathsYes, c);

        c.gridx = 1;
        add(pathsNo, c);
    }

    private void enableCollisionsOptions() {
        c.insets = new Insets(INSET, INSET, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel gui = new JLabel("Enable Collisions");
        gui.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE_SMALL));
        add(gui, c);

        c.insets = new Insets(INSET / 2, INSET, INSET, INSET);

        c.gridx = 0;
        c.gridy += 1;
        add(collisionsYes, c);

        c.gridx = 1;
        add(collisionsNo, c);
    }

    private void showMapOptions() {
        c.insets = new Insets(INSET, INSET, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel map = new JLabel("Map File");
        map.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE_SMALL));
        add(map, c);

        c.insets = new Insets(INSET / 2, INSET, INSET, INSET);

        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy += 1;
        add(mapFileTextField, c);

        mapFileTextField.setPreferredSize(chooseMapFile.getPreferredSize());

        c.gridx = 2;
        c.gridwidth = 1;
        add(chooseMapFile, c);
    }

    public final String getClientIP() {
        return clientIP.getText();
    }

    public final void setClientIP(final String newClientIP) {
        this.clientIP.setText(newClientIP);
    }

    public final int getClientPort() {
        return Integer.parseInt(clientPort.getText());
    }
    
    public final void setClientPort(final String newClientPort) {
        this.clientPort.setText(newClientPort);
    }

    public final String getServerIP() {
        return serverIP.getText();
    }

    public final void setServerIP(final String newServerIP) {
        this.serverIP.setText(newServerIP);
    }

    public final int getServerPort() {
        return Integer.parseInt(serverPort.getText());
    }

    public final void setServerPort(final String newServerPort) {
        this.serverPort.setText(newServerPort);
    }

    public final boolean useGui() {
        return guiCheckBox.getSelectedCheckbox() == guiYes;
    }

    public final void setUseGui(final boolean useGui) {
        if (useGui) {
            guiCheckBox.setSelectedCheckbox(guiYes);
        } else {
            guiCheckBox.setSelectedCheckbox(guiNo);
        }
    }

    public final boolean isVisualizePaths() {
        return pathsCheckBox.getSelectedCheckbox() == pathsYes;
    }

    public final void setVisualizePaths(final boolean visualizePaths) {
        if (visualizePaths) {
            pathsCheckBox.setSelectedCheckbox(pathsYes);
        } else {
            pathsCheckBox.setSelectedCheckbox(pathsNo);
        }
    }

    public final boolean isEnableCollisions() {
        return collisionsCheckBox.getSelectedCheckbox() == collisionsYes;
    }

    public final void setEnableCollisions(final boolean enableCollisions) {
        if (enableCollisions) {
            collisionsCheckBox.setSelectedCheckbox(collisionsYes);
        } else {
            collisionsCheckBox.setSelectedCheckbox(collisionsNo);
        }
    }

    public final JButton getChooseMapFile() {
        return chooseMapFile;
    }
   
    public final String getMapFile() {
        return mapFileTextField.getText();
    }

    public final void setMapFile(final String mapFile) {
        mapSpec.setMapFileLocation(mapFile);
        this.mapFileTextField.setText(mapFile);
    }

    public final JFileChooser getFileChooser() {
        return fileChooser;
    }

    public final void setFileChooser(final JFileChooser newFileChooser) {
        fileChooser = newFileChooser;
    }

    /**
     * Returns if changes has been made to the default configuration.
     *
     * @return whether changes have been made.
     */
    public final boolean isDefault() {
        return this.getClientIP().equals(DefaultConfigurationValues.DEFAULT_CLIENT_IP.getValue())
                && this.getClientPort() == DefaultConfigurationValues.DEFAULT_CLIENT_PORT.getIntValue()
                && this.getServerIP().equals(DefaultConfigurationValues.DEFAULT_SERVER_IP.getValue())
                && this.getServerPort() == DefaultConfigurationValues.DEFAULT_SERVER_PORT.getIntValue()
                && this.useGui() == DefaultConfigurationValues.USE_GUI.getBooleanValue()
                && this.isVisualizePaths() == DefaultConfigurationValues.VISUALIZE_PATHS.getBooleanValue()
                && this.isEnableCollisions() == DefaultConfigurationValues.ENABLE_COLLISIONS.getBooleanValue()
                && this.getMapFile().equals(DefaultConfigurationValues.MAP_FILE.getValue());
    }

    public String getOldValues() {
        return this.oldClientIP + this.oldClientPort
                + this.oldServerIP + this.oldServerPort + this.oldGui
                + this.oldMapFile;
    }

    /**
     * Updates the "old" values after a file has been saved.
     */
    public void updateOldValues() {
        this.oldClientIP = clientIP.getText();
        this.oldClientPort = clientPort.getText();
        this.oldServerIP = serverIP.getText();
        this.oldServerPort = serverPort.getText();
        this.oldGui = guiCheckBox.getSelectedCheckbox();
        this.oldMapFile = mapFileTextField.getText();
    }

    public String getCurrentValues() {
        return this.clientIP.getText()
                + this.clientPort.getText()
                + this.serverIP.getText() + this.serverPort.getText()
                + this.guiCheckBox.getSelectedCheckbox()
                + this.mapFileTextField.getText();
    }
    
    public MapSpec getMapSpecifications() {
        return mapSpec;
    }
    
    public JTextField getClientIPTextField() {
        return clientIP;
    }
    
    public JTextField getClientPortTextField() {
        return clientPort;
    }
    
    public JTextField getServerIPTextField() {
        return serverIP;
    }
    
    public JTextField getServerPortTextField() {
        return serverPort;
    }
    
    public Checkbox getGUIYesCheckbox() {
        return guiYes;
    }
    
    public Checkbox getGUINoCheckbox() {
        return guiNo;
    }
    
    public Checkbox getPathsYesCheckbox() {
        return pathsYes;
    }
    
    public Checkbox getPathsNoCheckbox() {
        return pathsNo;
    }
    
    public Checkbox getCollisionsYesCheckbox() {
        return pathsYes;
    }
    
    public Checkbox getCollisionsNoCheckbox() {
        return pathsNo;
    }
    
    public JTextField getMapFileTextField() {
        return mapFileTextField;
    }
    
    public void addClientIPController(DocumentListener controller) {
        getClientIPTextField().getDocument().addDocumentListener(controller);
    }
    
    public void addClientPortController(DocumentListener controller) {
        getClientPortTextField().getDocument().addDocumentListener(controller);
    }
    
    public void addServerIPController(DocumentListener controller) {
        getServerIPTextField().getDocument().addDocumentListener(controller);
    }
    
    public void addServerPortController(DocumentListener controller) {
        getServerPortTextField().getDocument().addDocumentListener(controller);
    }
    
    public void addGUIYesCheckboxController(ItemListener controller) {
        getGUIYesCheckbox().addItemListener(controller);
    }
    
    public void addGUINoCheckboxController(ItemListener controller) {
        getGUINoCheckbox().addItemListener(controller);
    }
    
    public void addPathsYesCheckboxController(ItemListener controller) {
        getPathsYesCheckbox().addItemListener(controller);
    }
    
    public void addPathsNoCheckboxController(ItemListener controller) {
        getPathsNoCheckbox().addItemListener(controller);
    }
    
    public void addCollisionsYesCheckboxController(ItemListener controller) {
        getCollisionsYesCheckbox().addItemListener(controller);
    }
    
    public void addCollisionsNoCheckboxController(ItemListener controller) {
        getCollisionsNoCheckbox().addItemListener(controller);
    }
    
    public void addMapFileController(DocumentListener controller) {
        getMapFileTextField().getDocument().addDocumentListener(controller);
    }

    public void addMapFileButtonController(ActionListener controller) {
        getChooseMapFile().addActionListener(controller);
    }
    
}
