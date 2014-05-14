package nl.tudelft.bw4t.gui.panel;

import nl.tudelft.bw4t.controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 *
 * MainPanel which serves as the content pane for the ScenarioEditor frame. Creates a 1/3 - 2/3 division,
 * the former for the ConfigurationPanel, and the latter the BotPanel
 * @since 13-05-2014
 * @author Joop Au�
 */
public class MainPanel extends JPanel {

    private ConfigurationPanel configurationPanel;
    private BotPanel botPanel;
    private Controller controller;
    private GridBagLayout gbl;

    /**
     * Create a MainPanel consisting of a ConfigurationPanel and a BotPanel
     * @param configurationPanel The configuration panel
     * @param botPanel The bot panel
     */
    public MainPanel(ConfigurationPanel configurationPanel, BotPanel botPanel) {
        gbl = new GridBagLayout();
    	this.setLayout(gbl);
        this.setConfigurationPanel(configurationPanel);
        this.setBotPanel(botPanel);

        this.drawPanel();

        this.configurationPanel = configurationPanel;
        this.botPanel = botPanel;

        controller = new Controller(this);
    }

    /**
     * Draw the two panels unto the main panel.
     * These will have black borders around them.
     */
    public void drawPanel() {
        GridBagConstraints c = new GridBagConstraints();
        
        //configurationPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        //botPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        c.insets = new Insets(10,10,10,10); 
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 0.2;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        this.add(configurationPanel, c);

        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.8;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 0;
        this.add(botPanel, c);
    }

    /**
     * Returns the configuration panel used by the MainPanel.
     * @return The configuration panel object.
     */
    public ConfigurationPanel getConfigurationPanel() {
        return configurationPanel;
    }

    /**
     * Set the configuration panel used by the MainPanel.
     * @param configurationPanel The configuration panel object to be used.
     */
    public void setConfigurationPanel(ConfigurationPanel configurationPanel) {
        this.configurationPanel = configurationPanel;
    }


    /**
     * Returns the bot panel used by the MainPanel
     * @return The bot panel object.
     */
    public BotPanel getBotPanel() {
        return botPanel;
    }

    /**
     * Set the bot panel used by the MainPanel.
     * @param botPanel The bot panel object to be used.
     */
    public void setBotPanel(BotPanel botPanel) {
        this.botPanel = botPanel;
    }

    /**
     * Returns the controller object being used to handle all events on the GUI.
     * @return The Controller used.
     */
    public Controller getController() {
        return controller;
    }
}
