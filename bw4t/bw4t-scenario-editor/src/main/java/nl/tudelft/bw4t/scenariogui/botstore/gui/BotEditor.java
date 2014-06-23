package nl.tudelft.bw4t.scenariogui.botstore.gui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.botstore.controller.BotController;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * This class serves as frame for the BotEditorPanel
 */
public class BotEditor extends JFrame {
    
    private BW4TClientConfig model;
    
    private static final long serialVersionUID = 8114982191029560097L;

    private String windowName = "Bot Editor";

    private MainPanel parent;

    private BotEditorPanel bPanel;

    private BotController controller;

    private int row;
    
    /**
     * Creates the BotEditor.
     * @param pparent The parent of the BotEditor.
     * @param row The row to be updated in the ScenarioEditor.
     */
    public BotEditor(MainPanel pparent, int row, BW4TClientConfig model) {
        this(new BotController(pparent, row, model));
    }
    
    /**
     * Creates the BotEditor.
     * @param bc The BotController in control of this frame.
     */
    public BotEditor(BotController bc) {
        controller = bc;
        
        setLookAndFeel();
        setTitle(windowName);
        setResizable(false);
        setLayout(null);
      
        bPanel = new BotEditorPanel(bc);
        bPanel.setBotEditor(this);
        bPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(bPanel);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
    }

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
    
    public int getRow() {
        return this.row;
    }

    public MainPanel getParent() {
        return parent;
    }
    
    public BotController getController() {
        return controller;
    }
    
    public MainPanel getMainPanel() {
        return controller.getMainPanel();
    }
    
    public BotEditorPanel getBotEditorPanel() {
        return bPanel;
    }
    
    public void setBotEditorPanel(BotEditorPanel panel) {
        bPanel = panel;
    }
}
