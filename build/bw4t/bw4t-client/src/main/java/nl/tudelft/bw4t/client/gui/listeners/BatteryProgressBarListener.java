package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JProgressBar;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.map.view.ViewEntity;

/**
 * ActionListener that performs the updates of the battery progress bar
 */
public class BatteryProgressBarListener  {

    private static final List<BatteryProgressBarListener> listeners = new LinkedList<BatteryProgressBarListener>();

    BW4TClientGUI clientGUI;
    JProgressBar progressBar;

    public BatteryProgressBarListener(JProgressBar progressBar, BW4TClientGUI gui) {
        this.progressBar = progressBar;
        this.clientGUI = gui;
        listeners.add(this);
    }

    public void update() {
    	progressBar.setForeground(Color.green);
    	
        ViewEntity bot = clientGUI.getController().getMapController().getTheBot();
        double counter = bot.getBatteryLevel();

        progressBar.setValue((int) counter);
        if (counter < progressBar.getMaximum() / 2) {
            progressBar.setForeground(Color.orange);
        }
        if (counter < progressBar.getMaximum() / 4) {
            progressBar.setForeground(Color.red);
        }

        clientGUI.setBatteryProgressBar(progressBar);
    }

    public static List<BatteryProgressBarListener> getListeners() {
        return listeners;
    }
}
