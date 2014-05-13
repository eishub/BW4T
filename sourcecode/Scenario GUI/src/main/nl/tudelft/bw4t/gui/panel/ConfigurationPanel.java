package nl.tudelft.bw4t.gui.panel;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

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
    private JTextField tfFile1 = new JTextField(100);
    private JTextField tfFile2 = new JTextField(100);
    
    CheckboxGroup group1 = new CheckboxGroup();
    private Checkbox cbYes1 = new Checkbox("Yes", true, group1);
    private Checkbox cbNo1 = new Checkbox("No", false, group1);
    
    CheckboxGroup group2 = new CheckboxGroup();
    private Checkbox cbYes2 = new Checkbox("Yes", true, group2);
    private Checkbox cbNo2 = new Checkbox("No", false, group2);
    
    private JButton openFile1 = new JButton("Open File");
    private JButton openFile2 = new JButton("Open File");
    
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
        add(tfFile1); add(openFile1); add(new JLabel(" ")); add(new JLabel(" "));
        
        //add Map file section
        add(lMap); add(new JLabel(" ")); add(new JLabel(" ")); add(new JLabel(" "));
        add(tfFile2); add(openFile2); add(new JLabel(" ")); add(new JLabel(" "));
    }
}
