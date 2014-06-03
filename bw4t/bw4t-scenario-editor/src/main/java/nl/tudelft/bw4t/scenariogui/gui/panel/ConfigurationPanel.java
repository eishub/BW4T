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

import nl.tudelft.bw4t.scenariogui.util.FileFilters;
import nl.tudelft.bw4t.scenariogui.util.Format;
import nl.tudelft.bw4t.scenariogui.util.MapSpec;

/**
 * The ConfigurationPanel class represents the left pane of the MainPanel. It
 * shows the options the user can configure.
 */
public class ConfigurationPanel extends JPanel {
    
    /** Randomly generated serial version. */
    private static final long serialVersionUID = 2925174902776539436L;

    /** Localhost. */
    private static final String LOCALHOST = "localhost";
    /** True. */
    private static final String TRUE = "true";
    
    /** The font name. */
    private static final String FONT_NAME = "Sans-Serif";
    
    /** The insets used. */
    private static final int INSET = 8;

    /** The weight used for the grid bag constraints. */
    private static final double GRID_BAG_CONSTRAINTS_WEIGHT = 0.5;

    /**
     * The default values for in the GUI.
     * @author Nick
     *
     */
    public static enum DEFAULT_VALUES {
        /** The default server ip. */
        DEFAULT_SERVER_IP(LOCALHOST),
        /** The default client ip. */
        DEFAULT_CLIENT_IP(LOCALHOST),
        /** The default server port. */
        DEFAULT_SERVER_PORT("8000"),
        /** The default client port. */
        DEFAULT_CLIENT_PORT("2000"),
        /** Whether to use a GUI's for the client on default. */
        USE_GUI(TRUE),
        /** Whether the goal is used on default. */
        USE_GOAL(TRUE),
        /** The default location of the map file. */
        MAP_FILE("");

        /** The string value of the default value. */
        private String value;

        /**
         * Constructs a new default configuration.
         * @param newValue The new default value.
         */
        DEFAULT_VALUES(final String newValue) {
            this.value = newValue;
        }

        /**
         * Gets the value as a string.
         * @return The value.
         */
        public String getValue() {
            return value;
        }

        /**
         * Gets the value as an int.
         * @return The value as an int.
         */
        public int getIntValue() {
            return Integer.parseInt(value);
        }

        /**
         * Gets the value as a boolean.
         * @return The value as a boolean.
         */
        public boolean getBooleanValue() {
            return Boolean.parseBoolean(value);
        }

    }

    /** The column size of the big text fields. */
    private static final int TEXT_FIELD_COLUMN_SIZE_BIG = 15;
    /** The column size of the small text fields. */
    private static final int TEXT_FIELD_COLUMN_SIZE_SMALL = 6;

    /** The specifications of the currently selected map file. */
    private MapSpec mapSpec;

    /** The text field holding the client ip. */
    private JTextField clientIP = new JTextField(
            DEFAULT_VALUES.DEFAULT_CLIENT_IP.getValue(),
            TEXT_FIELD_COLUMN_SIZE_BIG);
    /** The text field for the client port. */
    private JTextField clientPort = new JTextField(
            DEFAULT_VALUES.DEFAULT_CLIENT_PORT.getValue(),
            TEXT_FIELD_COLUMN_SIZE_SMALL);
    {
        Format.addIntegerDocumentFilterForTextField(clientPort);
    }
    /** The text field holding the server ip. */
    private JTextField serverIP = new JTextField(
            DEFAULT_VALUES.DEFAULT_SERVER_IP.getValue(),
            TEXT_FIELD_COLUMN_SIZE_BIG);
    /** The text field holding the server port. */
    private JTextField serverPort = new JTextField(
            DEFAULT_VALUES.DEFAULT_SERVER_PORT.getValue(),
            TEXT_FIELD_COLUMN_SIZE_SMALL);
    {
        Format.addIntegerDocumentFilterForTextField(serverPort);
    }
    /** The text filed holding the location to the map file. */
    private JTextField mapFileTextField = new JTextField(
            DEFAULT_VALUES.MAP_FILE.getValue());

    /** The checkbox group for enabling the GUI. */
    private CheckboxGroup guiCheckBox = new CheckboxGroup();
    /** The yes option of the checkbox. */
    private Checkbox guiYes = new Checkbox("Yes",
            DEFAULT_VALUES.USE_GUI.getBooleanValue(), guiCheckBox);
    /** The no option of the checkbox. */
    private Checkbox guiNo = new Checkbox("No",
            !DEFAULT_VALUES.USE_GUI.getBooleanValue(), guiCheckBox);

    /** The button to open a file chooser. */
    private JButton chooseMapFile = new JButton("Open File");

    /** A file chooser (to for instance select a map). */
    private JFileChooser fileChooser;

    /** The gridbag constraints. */
    private GridBagConstraints c;

    /** The normal font size used. */
    private static final int FONT_SIZE = 16;
    /** The small font size used. */
    private static final int FONT_SIZE_SMALL = 14;

    /** The old client IP. */
    private String oldClientIP = clientIP.getText();

    /** The old client port. */
    private String oldClientPort = clientPort.getText();

    /** The old server IP. */
    private String oldServerIP = serverIP.getText();

    /** The old server port. */
    private String oldServerPort = serverPort.getText();

    /** The old checked box. */
    private Checkbox oldGui = guiCheckBox.getSelectedCheckbox();

    /** The old map file. */
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

        mapSpec = new MapSpec(DEFAULT_VALUES.MAP_FILE.getValue());

        // showConfigLabel();
        showClientOptions();
        showServerOptions();
        showGuiOptions();
        showMapOptions();

    }

    /**
     * Show the "Configuration" label in the panel.
     */
    private void showConfigLabel() {
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        add(new JLabel("Configuration"), c);
    }

    /**
     * Show the client configuration options in the panel.
     */
    private void showClientOptions() {
        c.insets = new Insets(INSET, INSET, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel client = new JLabel("Client");
        client.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE_SMALL));
        add(client, c);

        c.insets = new Insets(0, INSET, 0, 0);

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

    /**
     * Show the server configuration options in the panel.
     */
    private void showServerOptions() {
        c.insets = new Insets(INSET, INSET, 0, 0);

        c.gridx = 0;
        c.gridy += 1;

        JLabel server = new JLabel("Server");
        server.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE_SMALL));
        add(server, c);

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

    /**
     * Show the option to use a GUI in the panel.
     */
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

    /**
     * Show the options to add a map file in the panel.
     */
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

    /**
     * Returns the client IP.
     *
     * @return The client IP.
     */
    public final String getClientIP() {
        return clientIP.getText();
    }

    /**
     * Sets the value of the text field of the client IP.
     *
     * @param newClientIP
     *            The IP of the client
     */
    public final void setClientIP(final String newClientIP) {
        this.clientIP.setText(newClientIP);
    }

    /**
     * Returns the client port.
     *
     * @return The client port.
     */
    public final int getClientPort() {
        return Integer.parseInt(clientPort.getText());
    }

    /**
     * Sets the value of the text field of the client port.
     *
     * @param newClientPort
     *            The port of the client
     */
    public final void setClientPort(final String newClientPort) {
        this.clientPort.setText(newClientPort);
    }

    /**
     * Returns the server IP.
     *
     * @return The server IP.
     */
    public final String getServerIP() {
        return serverIP.getText();
    }

    /**
     * Sets the value of the text field of the server IP.
     *
     * @param newServerIP
     *            The IP of the server
     */
    public final void setServerIP(final String newServerIP) {
        this.serverIP.setText(newServerIP);
    }

    /**
     * Returns the server port.
     *
     * @return The server port.
     */
    public final int getServerPort() {
        return Integer.parseInt(serverPort.getText());
    }

    /**
     * Sets the value of the text field of the server port.
     *
     * @param newServerPort
     *            The port of the server
     */
    public final void setServerPort(final String newServerPort) {
        this.serverPort.setText(newServerPort);
    }

    /**
     * Returns if a GUI needs to be displayed.
     *
     * @return The use of a GUI.
     */
    public final boolean useGui() {
        return guiCheckBox.getSelectedCheckbox() == guiYes;
    }

    /**
     * Sets if GOAL needs to be used.
     *
     * @param useGui
     *           The  use of GOAL.
     */
    public final void setUseGui(final boolean useGui) {
        if (useGui) {
            guiCheckBox.setSelectedCheckbox(guiYes);
        }
        else {
            guiCheckBox.setSelectedCheckbox(guiNo);
        }
    }

    /**
     * Returns the button to choose a map file.
     *
     * @return The button to choose a map file.
     */
    public final JButton getChooseMapFile() {
        return chooseMapFile;
    }

    /**
     * Returns the path to the Map file.
     *
     * @return The path to the Map file.
     */
    public final String getMapFile() {
        return mapFileTextField.getText();
    }

    /**
     * Sets the value of the text field to the path of the Map file.
     *
     * @param mapFile
     *            The path of the Map file
     */
    public final void setMapFile(final String mapFile) {
        mapSpec.setMapFileLocation(mapFile);
        this.mapFileTextField.setText(mapFile);
    }

    /**
     * Returns a File Chooser.
     *
     * @return A File Chooser.
     */
    public final JFileChooser getFileChooser() {
        return fileChooser;
    }

    /**
     * Sets the new file chooser.
     * @param newFileChooser The new file chooser.
     */
    public final void setFileChooser(final JFileChooser newFileChooser) {
        fileChooser = newFileChooser;
    }

    /**
     * Returns if changes has been made to the default configuration.
     *
     * @return whether changes have been made.
     */
    public final boolean isDefault() {
        boolean isDefault = true;

        if (!this.getClientIP().equals(DEFAULT_VALUES.DEFAULT_CLIENT_IP.getValue())) {
            isDefault = false;
        }
        else if (this.getClientPort() != DEFAULT_VALUES.DEFAULT_CLIENT_PORT.getIntValue()) {
            isDefault = false;
        }
        else if (!this.getServerIP().equals(DEFAULT_VALUES.DEFAULT_SERVER_IP.getValue())) {
            isDefault = false;
        }
        else if (this.getServerPort() != DEFAULT_VALUES.DEFAULT_SERVER_PORT.getIntValue()) {
            isDefault = false;
        }
        else if (this.useGui() != DEFAULT_VALUES.USE_GUI.getBooleanValue()) {
            isDefault = false;
        // if(this.useGoal() != DEFAULT_VALUES.USE_GOAL.getBooleanValue() &&
        // isDefault)
        // isDefault = false;
        }
        else if (!this.getMapFile().equals(DEFAULT_VALUES.MAP_FILE.getValue())) {
            isDefault = false;
        }

        // TODO: check if the bot list is empty (since that is default too)

        return isDefault;
    }

    /**
     * Returns the values that have been saved previously.
     * @return The values that have been saved previously.
     */
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

        /** The old client port. */
        this.oldClientPort = clientPort.getText();

        /** The old server IP. */
        this.oldServerIP = serverIP.getText();

        /** The old server port. */
        this.oldServerPort = serverPort.getText();

        /** The old checked box. */
        this.oldGui = guiCheckBox.getSelectedCheckbox();

        /** The old map file. */
        this.oldMapFile = mapFileTextField.getText();
    }

    /**
     * Returns the current values.
     * @return The current values.
     */
    public String getCurrentValues() {
        return this.clientIP.getText()
                + this.clientPort.getText()
                + this.serverIP.getText() + this.serverPort.getText()
                + this.guiCheckBox.getSelectedCheckbox()
                + this.mapFileTextField.getText();
    }
    
    /**
     * Returns the map specifications for the selected map.
     * @return The map specifications for the selected map.
     */
    public MapSpec getMapSpecifications() {
        return mapSpec;
    }
    
}
