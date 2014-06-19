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
    
    /**
     * Random generated serial version UID.
     */
    private static final long serialVersionUID = 8114982191029560097L;
    /** the name of the window */
    private String windowName = "Bot Editor";
    /** The parent of this frame. */
    private MainPanel parent;
    /** the panel in the frame*/
    private BotEditorPanel bPanel;

	/** the controller for the frame*/
    private BotController controller;

    /** The row number of the selected bot. */
    private int row;
    
    /**
     * creates the BotEditor frame
     * @param pparent the parent of the frame
     * @param row the row to be updated in the scenario gui
     */
    public BotEditor(MainPanel pparent, int row, BW4TClientConfig model) {
    	this(new BotController(pparent, row, model));
    }
    /**
     * creates the BotEditor frame
     * @param bc the BotController in control of this frame
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
     * Returns the row number of the bot that is currently selected.
     * @return The row number of the bot that is currently selected.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Get the main panel.
     * @return parent
     */
    public MainPanel getParent() {
        return parent;
    }
    
    public BotController getController() {
        return controller;
    }
    
    public BotEditorPanel getBoteditorPanel() {
		return bPanel;
	}
    
    /**
	 * Return the MainPanel.
	 * @return mainpanel
	 */
	public MainPanel getMainPanel() {
		return controller.getMainPanel();
	}
	
	/**
	 * Return the BotEditorPanel.
	 * @return botpanel
	 */
	public BotEditorPanel getBotEditorPanel() {
		return bPanel;
	}
	
	public void setBotEditorPanel(BotEditorPanel panel) {
		bPanel = panel;
	}
}
