package nl.tudelft.bw4t.scenariogui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Katia
 */
public class BotPanel extends JPanel {
    
    private JPanel content = new JPanel();
    private JPanel botNumInfo = new JPanel();
    private JPanel botOptions = new JPanel();
    private JPanel botInfo = new JPanel();
    
    public BotPanel(){
        content.setLayout(new BorderLayout(5, 5));
        
        //info about the number of bots created
        botNumInfo.setLayout(new GridLayout(1, 6));
        
        JLabel botLabel = new JLabel("Bots");
        JTextField botCount = new JTextField();
        botCount.setEditable(false);
        JLabel epartnerLabel = new JLabel("E-partners");
        JTextField epartnerCount = new JTextField();
        epartnerCount.setEditable(false);
        JLabel humanLabel = new JLabel("Humans");
        JTextField humanCount = new JTextField();
        humanCount.setEditable(false);
        
        botNumInfo.add(botLabel);
        botNumInfo.add(botCount);
        botNumInfo.add(epartnerLabel);
        botNumInfo.add(epartnerCount);
        botNumInfo.add(humanLabel);
        botNumInfo.add(humanCount);
        
        //bot options
        botOptions.setLayout(new GridLayout(5, 2));
        
        JButton newBot = new JButton("New bot");
        JButton modifyBot = new JButton("Modify bot");
        JButton renameBot = new JButton("Rename bot");
        JButton duplicateBot = new JButton("Duplicate bot");
        JButton deleteBot = new JButton("Delete bot");
        
        botOptions.add(newBot);
        botOptions.add(new JLabel(""));
        botOptions.add(modifyBot);
        botOptions.add(new JLabel(""));
        botOptions.add(renameBot);
        botOptions.add(new JTextField(20));
        botOptions.add(duplicateBot);
        botOptions.add(new JTextField(5));
        botOptions.add(deleteBot);
        
        //list with bots
        JTable table = new JTable();
        DefaultTableModel botTable = new DefaultTableModel();
        table.setModel(botTable);
        botTable.addColumn("Bot");
        botTable.addColumn("Type");
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        content.add(botNumInfo, BorderLayout.NORTH);
        content.add(botOptions, BorderLayout.EAST);
        content.add(scrollPane, BorderLayout.CENTER);
        
        add(content);
    }
    
}
