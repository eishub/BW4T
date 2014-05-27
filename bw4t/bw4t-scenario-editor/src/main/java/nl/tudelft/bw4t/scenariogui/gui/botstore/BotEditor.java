package nl.tudelft.bw4t.scenariogui.gui.botstore;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.controllers.botstore.BotStoreController;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * This class serves as frame for the BotEditorPanel
 * @author Arun
 * @author Katia Asmoredjo
 */
public class BotEditor extends JFrame {
	
	/** the name of the window */
	private String windowName = "Bot Editor";
	
	private BotEditorPanel bPanel;
	private BotStoreController controller;
	private MainPanel view;
	private static int row;
	
	/**
	 * Creates the BotEditor.
	 * @param view The MainPanel.
	 * @param row The row number of the selected bot.
	 */
	public BotEditor(MainPanel view, int row){
		setLookAndFeel();
		setTitle(windowName);
		setResizable(false);
		setLayout(null);
		
		this.view = view;
		this.row = row;
			
		bPanel = new BotEditorPanel(this, this.view);
		bPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		setContentPane(bPanel);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		pack();
		
		setLocationRelativeTo(null);
		
		setVisible(true);
		
		controller = new BotStoreController(this);
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
     * Returns the BotEditorPanel
     * @return botEditorPanel used
     */
	public BotEditorPanel getBotEditorPanel() {
		return bPanel;
	}
	
	public static void main(String[] args){
		new BotEditor(null, row);
	}
}
