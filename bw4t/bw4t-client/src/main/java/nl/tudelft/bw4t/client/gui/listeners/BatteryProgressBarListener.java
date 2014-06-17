package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;

/**
 * ActionListener that performs the updates of the battery progress bar
 */
public class BatteryProgressBarListener implements ActionListener {
    BW4TClientGUI clientGUI;

    @Override
    public void actionPerformed(ActionEvent e) {
        int counter = (int) (Math.random()*10);
        
        JProgressBar progressBar = clientGUI.getBatteryProgressBar();
        
        progressBar.setValue(counter);
        if(counter < progressBar.getMaximum()/2) {
            progressBar.setForeground(Color.orange);
        }
        if(counter < progressBar.getMaximum()/4) {
            progressBar.setForeground(Color.red);
        }
        if (counter < 1) {
            JOptionPane.showMessageDialog(null, "Battery empty!");
        }
        
        clientGUI.setBatteryProgressBar(progressBar);
    }
}
