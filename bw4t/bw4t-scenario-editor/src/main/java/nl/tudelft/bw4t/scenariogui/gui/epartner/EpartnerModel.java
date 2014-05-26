package src.main.java.nl.tudelft.bw4t.scenariogui.gui.epartner;

import src.main.java.nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;
import src.main.java.nl.tudelft.bw4t.scenariogui.gui.botstore.BotStoreController;
import src.main.java.nl.tudelft.bw4t.scenariogui.gui.botstore.EmptyBorder;
import src.main.java.nl.tudelft.bw4t.scenariogui.gui.botstore.JFrame;
import src.main.java.nl.tudelft.bw4t.scenariogui.gui.botstore.String;

/**
 * This class serves as frame for the EpartnerPanel
 * @author Wendy
 *
 */

public class EpartnerModel extends JFrame {

	/** the name of the window */
	private String windowName = "Epartner";
	/** the window width*/
	private int width;
	/** the window height*/
	private int height;
	
	private EpartnerPanel ePanel;
	private EpartnerController controller;

	/**
	 * creates the EpartnerPanel frame
	 */
	public EpartnerModel(){
		setLookAndFeel();
		setTitle(windowName);
		setResizable(false);
		setLayout(null);
					
		ePanel = new EPartnerPanel();
		ePanel.setBorder(new EmptyBorder(15,15,15,15));
		setContentPane(ePanel);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		pack();
		
		width = this.getWidth();
		height = this.getHeight();
		
		setLocationRelativeTo(null);
		
		setVisible(true);
		
		controller = new EpartnerController(this);
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
     * Returns the EpartnerPanel
     * @return EpartnerPanel used
     */
	public EpartnerPanel getEpartnerPanel() {
		return ePanel;
	}
	
	public static void main(String[] args){
		new EpartnerModel();
	}
}
