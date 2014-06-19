package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.map.renderer.MapController;
import nl.tudelft.bw4t.map.view.ViewEntity;
import org.apache.log4j.Logger;

/**
 * ActionListener that performs the updates of the battery progress bar
 */
public class BatteryProgressBarListener  {

    public static List<BatteryProgressBarListener> listeners = new LinkedList<BatteryProgressBarListener>();

    BW4TClientGUI clientGUI;
    JProgressBar progressBar;

    public BatteryProgressBarListener(JProgressBar progressBar, BW4TClientGUI gui) {
        this.progressBar = progressBar;
        this.clientGUI = gui;
    }

    public void update() {
        ViewEntity bot = clientGUI.getController().getMapController().getTheBot();
        double counter = bot.getBatteryLevel();

        progressBar.setValue((int) counter);
        if(counter < progressBar.getMaximum()/2) {
            progressBar.setForeground(Color.orange);
        }
        if(counter < progressBar.getMaximum()/4) {
            progressBar.setForeground(Color.red);
        }

        clientGUI.setBatteryProgressBar(progressBar);
    }

    public static List<BatteryProgressBarListener> getListeners() {
        return listeners;
    }
}
