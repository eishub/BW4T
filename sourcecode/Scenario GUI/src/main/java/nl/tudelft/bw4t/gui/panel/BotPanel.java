package nl.tudelft.bw4t.gui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Katia
 */
public class BotPanel extends JPanel implements ActionListener {
    
    private JPanel botCountInfo = new JPanel();
    private JPanel botOptions = new JPanel();
    private JScrollPane botTable;
    
    private JButton newBot = new JButton("New bot");
    private JButton modifyBot = new JButton("Modify bot");
    private JButton renameBot = new JButton("Rename bot");
    private JButton duplicateBot = new JButton("Duplicate bot");
    private JButton deleteBot = new JButton("Delete bot");
    
    public BotPanel(){
        setLayout(new BorderLayout(5, 5));
        
        createBotCountPanel();
        createBotOptionPanel();
        createBotTablePanel();
        
        
        add(botCountInfo, BorderLayout.NORTH);
        add(botOptions, BorderLayout.EAST);
        add(botTable, BorderLayout.CENTER);
    }
    
    private void createBotCountPanel(){
        botCountInfo.setLayout(new GridLayout(1, 6));
        
        JLabel botLabel = new JLabel("Bots");
        JTextField botCount = new JTextField();
        botCount.setText("0");
        botCount.setEditable(false);
        JLabel epartnerLabel = new JLabel("E-partners");
        JTextField epartnerCount = new JTextField();
        epartnerCount.setText("0");
        epartnerCount.setEditable(false);
        JLabel humanLabel = new JLabel("Humans");
        JTextField humanCount = new JTextField();
        humanCount.setText("0");
        humanCount.setEditable(false);
        
        botCountInfo.add(botLabel);
        botCountInfo.add(botCount);
        botCountInfo.add(epartnerLabel);
        botCountInfo.add(epartnerCount);
        botCountInfo.add(humanLabel);
        botCountInfo.add(humanCount);
    }
    
    private void createBotOptionPanel(){
    	botOptions.setLayout(new GridLayout(15, 2));
        
        //JPanel rightBotPanel = new JPanel();
        //rightBotPanel.setLayout(new GridLayout(5, 2));
        
        newBot.addActionListener(this);
        modifyBot.addActionListener(this);
        renameBot.addActionListener(this);
        duplicateBot.addActionListener(this);
        deleteBot.addActionListener(this);
        
        botOptions.add(newBot);
        botOptions.add(new JLabel(""));
        botOptions.add(modifyBot);
        botOptions.add(new JLabel(""));
        botOptions.add(new JTextField(20));
        botOptions.add(renameBot);
        botOptions.add(new JLabel(""));
        botOptions.add(new JTextField(5));
        botOptions.add(duplicateBot);
        botOptions.add(new JLabel(""));
        botOptions.add(deleteBot);
    }
    
    private void createBotTablePanel(){
        JTable table = new JTable();
        DefaultTableModel botInfo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                //make all cells not editable
                return false;
            }
        };
        
        table.setModel(botInfo);
        botInfo.addColumn("Bot");
        botInfo.addColumn("Type");
        
        botTable = new JScrollPane(table);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == newBot){
            System.out.println("Go to Bot Store");
        }
        
        if(ae.getSource() == modifyBot){
            System.out.println("Got to Bot Store");
        }
        
        if(ae.getSource() == renameBot){}
        
        if(ae.getSource() == duplicateBot){}
        
        if(ae.getSource() == deleteBot){
            JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this bot?", "", JOptionPane.YES_NO_OPTION);
        }
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
