package nl.tudelft.bw4t.scenariogui.gui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

/**
 *
 * MainPanel which serves as the content pane for the ScenarioEditor frame.
 * Creates a 1/3 - 2/3 division, the former for the ConfigurationPanel, and the
 * latter the EntityPanel.
 * <p>
 * @author      Katia Asmoredjo
 * @author      Joop Aué
 * @author      Nick Feddes  
 * @version     0.1                
 * @since       12-05-2014        
 */
public class MainPanel extends JPanel {
    
    /** The inset used. */
    private static final int INSET = 10;
    /** The weights used. */
    private static final double WEIGHT_1 = 0.1;
    /** The bigger weight used. */
    private static final double WEIGHT_2 = 0.8;

    /** Randomly generated serial version. */
    private static final long serialVersionUID = 475250876795906302L;
    /** The configuration panel. */
    private ConfigurationPanel configurationPanel;
    /** The entity panel. */
    private EntityPanel entityPanel;
    /**  */
    private GridBagLayout gbl;

    /**
     * Create a MainPanel consisting of a ConfigurationPanel and a EntityPanel.
     *
     * @param newConfigurationPanel The configuration panel
     * @param newEntityPanel    The entity panel
     */
    public MainPanel(final ConfigurationPanel newConfigurationPanel,
            final EntityPanel newEntityPanel) {
        gbl = new GridBagLayout();
        this.setLayout(gbl);
        this.setConfigurationPanel(newConfigurationPanel);
        this.setEntityPanel(newEntityPanel);

        this.drawPanel();

        this.configurationPanel = newConfigurationPanel;
        this.entityPanel = newEntityPanel;
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
    public final EntityPanel getEntityPanel() {
        return entityPanel;
    }

    /**
     * Set the bot panel used by the MainPanel.
     *
     * @param newEntityPanel    The bot panel object to be used.
     */
    public final void setEntityPanel(final EntityPanel newEntityPanel) {
        this.entityPanel = newEntityPanel;
    }

}
