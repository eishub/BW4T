package nl.tudelft.bw4t;

import javax.swing.*;

import nl.tudelft.bw4t.gui.panel.BotPanel;
import nl.tudelft.bw4t.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.gui.panel.MainPanel;
import nl.tudelft.bw4t.gui.MenuBar;




/**
 *
 */
public class ScenarioEditor extends JFrame {

    /** The name of the window, as displayed in the title */
    private String windowName = "Scenario Editor";
    /** The window width */
    private int width = 800;
    /** The window height */
    private int height = 600;

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
        setJMenuBar(new MenuBar());

        // Attach the MainPanel that consists of the configuration and the botpanel.
        MainPanel mPanel = new MainPanel(new ConfigurationPanel(), new BotPanel());
        setActivePane(mPanel);

        setLookAndFeel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pack();

        setVisible(true);
    }

    /**
     * Set the content pane to the given panel. This changes which panel is shown in the frame.
     * @param panel The panel to be shown.
     */
    protected void setActivePane(JPanel panel) {
        setContentPane(panel);
    }

    /**
     * Return the currently active content pane.
     * @return The JPanel that is currently the active content pane.
     */
    protected JPanel getActivePane() {
        /* Type cast it to JPanel since it cannot be anything other than a JPanel due to the type check
         * in the setActivePane method.
         */
        return (JPanel)getContentPane();
    }

    /**
     *  Function to set the look and feel of the frame to the default look and feel of the system.
     *  Throws exceptions which are passed over since the failure to set the look and feel is not
     *  considered harmful.
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

    /**
     * Main function to start the ScenarioEditor.
     * @param args No arguments are required.
     */
    public static void main(String[] args) {
        new ScenarioEditor();
    }
}
