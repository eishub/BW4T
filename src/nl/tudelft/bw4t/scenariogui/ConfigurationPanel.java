package scenariogui;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author smto
 */
public class ConfigurationPanel extends JPanel {
    
    private JPanel pClient;
    private JPanel pServer;
    private JPanel pGoal;
    private JPanel pGUI;
    
    private JLabel lConfig = new JLabel("Configuration");
    private JLabel lClient = new JLabel("Client");
    private JLabel lServer = new JLabel("Server");
    private JLabel lGoal = new JLabel("Use Goal");
    private JLabel lGUI= new JLabel("Launch GUI");
    private JLabel lAgent = new JLabel("Agent class");
    private JLabel lMap = new JLabel("Map file");
    
    private JLabel lIP1 = new JLabel("IP");
    private JLabel lPort1 = new JLabel("Port");
    private JLabel lIP2 = new JLabel("IP");
    private JLabel lPort2 = new JLabel("Port");
    
    private JTextField tfIP1 = new JTextField(20);
    private JTextField tfPort1 = new JTextField(20);
    private JTextField tfIP2 = new JTextField(20);
    private JTextField tfPort2 = new JTextField(20);
    
    CheckboxGroup group1 = new CheckboxGroup();
    private Checkbox cbYes1 = new Checkbox("Yes", true, group1);
    private Checkbox cbNo1 = new Checkbox("No", false, group1);
    
    CheckboxGroup group2 = new CheckboxGroup();
    private Checkbox cbYes2 = new Checkbox("Yes", true, group2);
    private Checkbox cbNo2 = new Checkbox("No", false, group2);
    
    public ConfigurationPanel() {
        setLayout(new GridLayout(13, 1));
        
        pClient = new JPanel(new GridLayout(1, 4));
        pServer = new JPanel(new GridLayout(1, 4));
        pGoal = new JPanel(new GridLayout(1, 2));
        pGUI = new JPanel(new GridLayout(1, 2));
        
        add(lConfig);
        
        //add Client section
        pClient.add(lIP1);
        pClient.add(tfIP1);
        pClient.add(lPort1);
        pClient.add(tfPort1);
        
        add(lClient);
        add(pClient);
        
        //add Server section
        pServer.add(lIP2);
        pServer.add(tfIP2);
        pServer.add(lPort2);
        pServer.add(tfPort2);
        
        add(lServer);
        add(pServer);
        
        //add Use Goal section
        pGoal.add(cbYes1);
        pGoal.add(cbNo1);
        
        add(lGoal);
        add(pGoal);
        
        //add Launch GUI section
        pGUI.add(cbYes2);
        pGUI.add(cbNo2);
        
        add(lGUI);
        add(pGUI);
        
        //add Agent class section
        //add Map file section
    }
}
