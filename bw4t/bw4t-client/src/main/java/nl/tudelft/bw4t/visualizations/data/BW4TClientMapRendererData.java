package nl.tudelft.bw4t.visualizations.data;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import nl.tudelft.bw4t.agent.HumanAgent;
import nl.tudelft.bw4t.client.BW4TRemoteEnvironment;

public class BW4TClientMapRendererData {
    /**
     * Data needed for updating the graphical representation of the world
     */
    public EnvironmentDatabase environmentDatabase;
    public PerceptsInfo perceptsInfo;
    public boolean stop;
    public JFrame jFrame;
    public JPanel buttonPanel;
    public JTextArea chatSession;
    public JScrollPane chatPane;
    /**
     * Private variables only used for human player
     */
    public HumanAgent humanAgent;
    public JPopupMenu jPopupMenu;
    public Integer[] selectedLocation;
    public boolean goal;
    public boolean humanPlayer;
    /**
     * Most of the server interfacing goes through the std eis percepts
     */
    public BW4TRemoteEnvironment environment;

    public BW4TClientMapRendererData(JTextArea chatSession) {
        this.chatSession = chatSession;
    }
}