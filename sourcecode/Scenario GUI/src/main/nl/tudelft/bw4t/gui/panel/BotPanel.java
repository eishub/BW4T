package nl.tudelft.bw4t.gui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
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
    private JPanel botCountInfo = new JPanel();
    private JPanel botOptions = new JPanel();
    private JScrollPane botTable;
    
    public BotPanel(){
        content.setLayout(new BorderLayout(5, 5));
        
        createBotCountPanel();
        createBotOptionPanel();
        createBotTablePanel();
        
        content.add(botCountInfo, BorderLayout.NORTH);
        content.add(botOptions, BorderLayout.EAST);
        content.add(botTable, BorderLayout.CENTER);
        
        
        /*for(int i = 0; i < 30; i++){
            String[] testRow = {"bot" + i, "type" + i};
            botTable.addRow(testRow);
        }*/
        
        add(content);
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
        botOptions.setLayout(new GridLayout(3, 1));
        
        JPanel rightBotPanel = new JPanel();
        rightBotPanel.setLayout(new GridLayout(5, 2));
        
        JButton newBot = new JButton("New bot");
        JButton modifyBot = new JButton("Modify bot");
        JButton renameBot = new JButton("Rename bot");
        JButton duplicateBot = new JButton("Duplicate bot");
        JButton deleteBot = new JButton("Delete bot");
        
        rightBotPanel.add(newBot);
        rightBotPanel.add(new JLabel(""));
        rightBotPanel.add(modifyBot);
        rightBotPanel.add(new JLabel(""));
        rightBotPanel.add(renameBot);
        rightBotPanel.add(new JTextField(20));
        rightBotPanel.add(duplicateBot);
        rightBotPanel.add(new JTextField(5));
        rightBotPanel.add(deleteBot);
        
        botOptions.add(rightBotPanel);
        botOptions.add(new JLabel(""));
        botOptions.add(new JLabel(""));
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
    
}
