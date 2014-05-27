package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;

/**
 * The WindowExit class makes sure that on clicking the default exit button,
 * a warning pops up.
 * <p>
 * @author 		Seu Man To
 * @version 	0.1
 * @since		26-05-2014
 *
 */
public class WindowExit implements WindowListener {
	
	/**
	 * The view being controlled.
	 */
	private ScenarioEditor view;

	/**
	 * Creates a WindowExit object.
	 * @param newView The view.
	 */
	public WindowExit(ScenarioEditor newView) {
		this.view = newView;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		ConfigurationPanel configPanel = 
				view.getController().getMainView().getMainPanel().getConfigurationPanel();
		
		// Check if current config is different from last saved config
        if (!configPanel.getOldValues().equals(configPanel.getCurrentValues())) {
        	int response = JOptionPane.showOptionDialog(
    				null,
    				"You have not saved your current configuration. \n"
    				+
					"If you exit now, your changes will be lost. \n"
    				+
					"Are you sure you want to exit the program? \n",
    				"",
    				JOptionPane.YES_NO_OPTION,
    				JOptionPane.QUESTION_MESSAGE,
    				null,
    				null,
    				null);
        	
        	if (response == JOptionPane.YES_OPTION) {
        		System.exit(0);
        	}
        } else {
        	int response = JOptionPane.showOptionDialog(
        			null,
        			"Are you sure you want to exit the program?",
        			"",
        			JOptionPane.YES_NO_OPTION,
        			JOptionPane.QUESTION_MESSAGE,
        			null,
        			null,
        			null);
		
        	if (response == JOptionPane.YES_OPTION) {
        		System.exit(0);
        	}
        }
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		return;
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		return;
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		return;
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		return;
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		return;
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		return;
		
	}
}
