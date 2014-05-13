package tudelft.bw4t.gui.panel;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author smto
 */
public class ConfigurationPanel extends JPanel {
    
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
        setLayout(new GridLayout(0, 4));
        setVisible(true);
        
        add(lConfig); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
        
        //add Client section
        add(lClient); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
        add(lIP1); add(tfIP1); add(lPort1); add(tfPort1);
        
        //add Server section
        add(lServer); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
        add(lIP2); add(tfIP2); add(lPort2); add(tfPort2);
        
        //add Use Goal section
        add(lGoal); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
        add(cbYes1); add(cbNo1); add(new JLabel(" ")); add(new JLabel(" "));
        
        //add Launch GUI section
        add(lGUI); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
        add(cbYes2); add(cbNo2); add(new JLabel(" ")); add(new JLabel(" "));
        
        //add Agent class section
        add(lAgent); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
        
        /*JFileChooser agentChooser = new JFileChooser();
        int returnVal = agentChooser.showOpenDialog(parent);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           System.out.println("File: " +
                agentChooser.getSelectedFile().getName());
        }*/
        
        
        //add Map file section
    }
}
