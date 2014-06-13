package nl.tudelft.bw4t.scenariogui.editor.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;

/**
 * MainPanel which serves as the content pane for the ScenarioEditor frame.
 * Creates a 1/3 - 2/3 division, the former for the ConfigurationPanel, and the
 * latter the EntityPanel.
 *
 * @version 0.1
 * @since 12-05-2014
 */
public class MainPanel extends JPanel {

    private static final int INSET = 10;
    
    private static final double WEIGHT_1 = 0.1;
    
    private static final double WEIGHT_2 = 0.8;
    
    private static final long serialVersionUID = 475250876795906302L;

    private ScenarioEditor parent;
    
    private ConfigurationPanel configurationPanel;
    
    private EntityPanel entityPanel;
    
    private GridBagLayout gbl;

    /**
     * Create a MainPanel consisting of a ConfigurationPanel and a EntityPanel.
     *
     * @param newConfigurationPanel The configuration panel
     * @param newEntityPanel        The entity panel
     */
    public MainPanel(final ScenarioEditor parent, final ConfigurationPanel newConfigurationPanel,
                     final EntityPanel newEntityPanel) {
        gbl = new GridBagLayout();
        this.setLayout(gbl);
        this.setConfigurationPanel(newConfigurationPanel);
        this.setEntityPanel(newEntityPanel);

        this.drawPanel();

        this.configurationPanel = newConfigurationPanel;
        this.entityPanel = newEntityPanel;
        this.parent = parent;
    }

    /**
     * Draw the two panels unto the main panel. These will have black borders
     * around them.
     */
    public final void drawPanel() {
        GridBagConstraints c = new GridBagConstraints();

        // configurationPanel.setBorder(
        //      BorderFactory.createLineBorder(Color.black));
        // botPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        c.insets = new Insets(INSET, INSET, INSET, INSET);
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = WEIGHT_1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        this.add(configurationPanel, c);

        c.fill = GridBagConstraints.NONE;
        c.weightx = WEIGHT_2;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 0;
        this.add(entityPanel, c);
    }

    /**
     * Returns the configuration panel used by the MainPanel.
     *
     * @return The configuration panel object.
     */
    public final ConfigurationPanel getConfigurationPanel() {
        return configurationPanel;
    }

    /**
     * Set the configuration panel used by the MainPanel.
     *
     * @param newConfigurationPanel The configuration panel object to be used.
     */
    public final void setConfigurationPanel(
            final ConfigurationPanel newConfigurationPanel) {
        this.configurationPanel = newConfigurationPanel;
    }

    /**
     * Returns the entity panel used by the MainPanel.
     *
     * @return The entity panel object.
     */
    public EntityPanel getEntityPanel() {
        return entityPanel;
    }

    /**
     * Set the bot panel used by the MainPanel.
     *
     * @param newEntityPanel The bot panel object to be used.
     */
    public final void setEntityPanel(final EntityPanel newEntityPanel) {
        this.entityPanel = newEntityPanel;
    }

    public BW4TClientConfig getClientConfig() {
        return parent.getController().getModel();
    }
    
}
