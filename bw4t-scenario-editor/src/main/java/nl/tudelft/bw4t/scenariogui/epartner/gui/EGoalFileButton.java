package nl.tudelft.bw4t.scenariogui.epartner.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import nl.tudelft.bw4t.scenariogui.util.FileFilters;

/**
 * Handles actions of the GoalFileButton.
 */
public class EGoalFileButton implements ActionListener {
    
    private EpartnerFrame view;

    private JFileChooser jfc;

    /**
     * The constructor for this action listener.
     * 
     * @param view
     *            The frame with the button in it.
     */
    public EGoalFileButton(EpartnerFrame view) {
        this.view = view;
    }

    /**
     * Performs the action (open the filechooser).
     * 
     * @param ae
     *            The action event triggering this method.
     */
    public void actionPerformed(ActionEvent ae) {
        jfc = new JFileChooser();
        jfc.setFileFilter(FileFilters.goalFilter());
        if (jfc.showOpenDialog(view) == jfc.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            String path = f.getAbsolutePath();
            view.getEpartnerGoalFileField().setText(path);
        }
    }
}
