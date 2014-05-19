package nl.tudelft.bw4t.gui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 * This class serves as frame for the BotEditorPanel
 * @author Arun
 */
public class BotEditor extends JFrame{
	
	private String windowName = "Bot Editor";
	private int width;
	private int height;
	
	
	
	private BotEditorPanel bPanel;
	
	public BotEditor(){
		setLookAndFeel();
		setTitle(windowName);
		setResizable(false);
		setLayout(null);
		
		bPanel = new BotEditorPanel();
		setContentPane(bPanel);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		pack();
		
		width = this.getWidth();
		height = this.getHeight();
		
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
	
	public static void main(String[] args){
		new BotEditor();
		
	}
}
