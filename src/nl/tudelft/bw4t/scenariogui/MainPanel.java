package nl.tudelft.bw4t.scenariogui;

import javax.swing.*;
import java.awt.*;

/**
 * Created on 13-5-2014.
 * MainPanel which serves as the content pane for the ScenarioEditor frame. Creates a 1/3 - 2/3 division,
 * the former for the ConfigurationPanel, and the latter the BotPanel
 */
public class MainPanel extends JPanel {

    private JPanel configurationPanel;
    private JPanel botPanel;

    public MainPanel(JPanel configurationPanel, JPanel botPanel) {
        setLayout(new GridBagLayout());
        setConfigurationPanel(configurationPanel);
        setBotPanel(botPanel);

        drawPanel();
    }

    public void drawPanel() {
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(configurationPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.7;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        add(botPanel, c);
    }

    public JPanel getConfigurationPanel() {
        return configurationPanel;
    }

    public void setConfigurationPanel(JPanel configurationPanel) {
        this.configurationPanel = configurationPanel;
    }

    public JPanel getBotPanel() {
        return botPanel;
    }

    public void setBotPanel(JPanel botPanel) {
        this.botPanel = botPanel;
    }

}
