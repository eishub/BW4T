package nl.tudelft.bw4t.scenariogui.gui.botstore;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;

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
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
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
        }catch (ClassNotFoundException e) {
        	ScenarioEditor.handleException(e, "Error: Class has not been found");
        } catch (InstantiationException e) {
        	ScenarioEditor.handleException(e, "Error: Instantiaton could not be done");
		} catch (IllegalAccessException e) {
			ScenarioEditor.handleException(e, "Error: Illegal Access");
		} catch (UnsupportedLookAndFeelException e) {
			ScenarioEditor.handleException(e, "Error: Unsupported LookAndFeel");
		} 
    }

}
