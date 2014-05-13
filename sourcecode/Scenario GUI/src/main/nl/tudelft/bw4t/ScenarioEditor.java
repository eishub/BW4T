package nl.tudelft.bw4t;

import javax.swing.*;

import nl.tudelft.bw4t.gui.panel.BotPanel;
import nl.tudelft.bw4t.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.gui.panel.MainPanel;



import java.awt.*;

/**
 *
 */
public class ScenarioEditor extends JFrame {

    private String windowName = "Scenario Editor";
    private int width = 1000;
    private int height = 700;

    /**
     * Create the scenario editor frame, which will then hold the panels with specific functions.
     */
    public ScenarioEditor() {
        setSize(width, height);
        setTitle(windowName);

        // Setting the location relative to null centers the frame.
        setLocationRelativeTo(null);

        setResizable(false);
        setLayout(null);

        // Attach the menu bar.
        //setJMenuBar(new MenuBar());

        // Attach the MainPanel that consists of the configuration and the botpanel.
        MainPanel panel = new MainPanel(new ConfigurationPanel(), new BotPanel());
        setActivePane(panel);

        setLookAndFeel();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /* This is about how a panel would be "started" on the JFrame.
    */
    protected void setActivePane(JPanel panel) {
        setContentPane(panel);
    }

    /*
     *  Function to set the look and feel of the frame to the default look and feel of the system.
     */
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            // pass
        } catch (InstantiationException e) {
            // pass
        } catch (IllegalAccessException e) {
            // pass
        } catch (UnsupportedLookAndFeelException e) {
            // pass
        }
    }

    public static void main(String[] args) {
        new ScenarioEditor();
    }
}
