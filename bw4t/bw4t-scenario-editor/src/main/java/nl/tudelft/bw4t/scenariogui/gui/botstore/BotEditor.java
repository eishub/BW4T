
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import nl.tudelft.bw4t.scenariogui.controller.BotStoreController;

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
	
	private BotEditorPanel bPanel;
	private BotStoreController controller;
	
	/**
	 * creates the BotEditor frame
	 */
	public BotEditor(){
		setLookAndFeel();
		setTitle(windowName);
		setResizable(false);
		setLayout(null);
			
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
	
	public static void main(String[] args){
		new BotEditor();
	}
}
