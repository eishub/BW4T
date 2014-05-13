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
    private JPanel botPanel;
    private Controller controller;
    private GridBagLayout gbl;
    
    public MainPanel(ConfigurationPanel configurationPanel, JPanel botPanel) {
        gbl = new GridBagLayout();
    	this.setLayout(gbl);
        this.setConfigurationPanel(configurationPanel);
        this.setBotPanel(botPanel);

        this.drawPanel();

        this.configurationPanel = configurationPanel;
        this.botPanel = botPanel;

        controller = new Controller(this);
    }

    public void drawPanel() {
        GridBagConstraints c = new GridBagConstraints();
        
        configurationPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        botPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
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
        //this.add(botPanel, c);
    }

    public ConfigurationPanel getConfigurationPanel() {
        return configurationPanel;
    }

    public void setConfigurationPanel(ConfigurationPanel configurationPanel) {
        this.configurationPanel = configurationPanel;
    }

    public JPanel getBotPanel() {
        return botPanel;
    }

    public void setBotPanel(JPanel botPanel) {
        this.botPanel = botPanel;
    }

    public Controller getController() {
        return controller;
    }
}
