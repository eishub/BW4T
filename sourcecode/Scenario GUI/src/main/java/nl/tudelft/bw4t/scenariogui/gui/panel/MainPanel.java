package nl.tudelft.bw4t.scenariogui.gui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

/**
 *
 * MainPanel which serves as the content pane for the ScenarioEditor frame. Creates a 1/3 - 2/3 division,
 * the former for the ConfigurationPanel, and the latter the EntityPanel
 * @since 13-05-2014
 * @author Joop Aue
 */
public class MainPanel extends JPanel {

    private ConfigurationPanel configurationPanel;
    private EntityPanel entityPanel;
    private GridBagLayout gbl;

    /**
     * Create a MainPanel consisting of a ConfigurationPanel and a EntityPanel
     * @param configurationPanel The configuration panel
     * @param entityPanel The entity panel
     */
    public MainPanel(ConfigurationPanel configurationPanel, EntityPanel entityPanel) {
        gbl = new GridBagLayout();
    	this.setLayout(gbl);
        this.setConfigurationPanel(configurationPanel);
        this.setEntityPanel(entityPanel);

        this.drawPanel();

        this.configurationPanel = configurationPanel;
        this.entityPanel = entityPanel;
    }

    /**
     * Draw the two panels unto the main panel.
     * These will have black borders around them.
     */
    public void drawPanel() {
        GridBagConstraints c = new GridBagConstraints();
        
        
        
      //  configurationPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      //  botPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        c.insets = new Insets(10, 10, 10, 10); 
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 0.1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        this.add(configurationPanel, c);

        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.8;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 0;
        this.add(entityPanel, c);
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
     * Returns the entity panel used by the MainPanel
     * @return The entity panel object.
     */
    public EntityPanel getBotPanel() {
        return entityPanel;
    }

    /**
     * Set the bot panel used by the MainPanel.
     * @param botPanel The bot panel object to be used.
     */
    public void setEntityPanel(EntityPanel entityPanel) {
        this.entityPanel = entityPanel;
    }

}
