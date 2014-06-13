package nl.tudelft.bw4t.map.editor.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StandardBasisButton implements ActionListener {

	private SizeDialog view;

	/**
	 * The constructor for this action listener.
	 * 
	 * @param pview
	 *            The frame with the button in it.
	 */
	
	public StandardBasisButton(SizeDialog pview) {
		this.view = pview;
	}

	/**
	 * Perform the required action 
	 * 
	 * @param ae
	 *            The action event triggering this method.
	 */
	public void actionPerformed(ActionEvent ae) {
		// TODO fill in the right frame that has to be opened
		SizeDialog dialog = new SizeDialog();
    	dialog.setVisible(true);
	}
}
