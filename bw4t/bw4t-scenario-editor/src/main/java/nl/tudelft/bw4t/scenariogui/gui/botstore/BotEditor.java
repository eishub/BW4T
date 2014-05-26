package nl.tudelft.bw4t.scenariogui.gui.botstore;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import nl.tudelft.bw4t.scenariogui.controllers.botstore.BotStoreController;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * This class serves as frame for the BotEditorPanel
 * @author Arun
 */
public class BotEditor extends JFrame{
	
	/** the name of the window */
	private String windowName = "Bot Editor";
	/** the windwow width*/
	private int width;
	/** the window height*/
	private int height;
	/** The parent of this frame. */
	private MainPanel parent;
	private BotEditorPanel bPanel;
	private BotStoreController controller;
	
	/**
	 * creates the BotEditor frame
	 */
	public BotEditor(MainPanel parent){
		setLookAndFeel();
		setTitle(windowName);
		setResizable(false);
		setLayout(null);
		this.parent = parent;
		bPanel = new BotEditorPanel();
		bPanel.setBorder(new EmptyBorder(15,15,15,15));
		setContentPane(bPanel);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		pack();
		
		width = this.getWidth();
		height = this.getHeight();
		
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
     * Returns the BotEditorPanel
     * @return botEditorPanel used
     */
	public BotEditorPanel getBotEditorPanel() {
		return bPanel;
	}
	/**
	 * Get the main panel.
	 */
	public MainPanel getParent() {
		return parent;
	}
	/**
	 * Set the main panel.
	 * @param parent the main panel.
	 */
	public void setParent(MainPanel parent) {
		this.parent = parent;
	}

	public static void main(String[] args){
		new BotEditor(null);
	}
}
