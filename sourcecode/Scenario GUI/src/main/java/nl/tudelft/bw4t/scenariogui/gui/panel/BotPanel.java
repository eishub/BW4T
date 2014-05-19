package nl.tudelft.bw4t.scenariogui.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 * The BotPanel class represents right pane of the MainPanel.
 * It shows a list of bots and the actions that are possible to edit bots.
 *
 * @author Katia
 */
public class BotPanel extends JPanel {

    private JPanel botCountInfo = new JPanel();
    private JPanel botOptions = new JPanel();
    private JPanel entityLists = new JPanel();
    private DefaultTableModel botList;
    private JScrollPane botScrollPane;
    private DefaultTableModel epartnerList;
    private JScrollPane epartnerScrollPane;

    private JTextField newBotName = new JTextField();

    /* Initial value 1, maximum 100, minimum 1, steps of 1 */
    private SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
    private JSpinner duplicateAmount = new JSpinner(model);

    /**
     * Create a BotPanel object.
     */
    public BotPanel() {
        setLayout(new BorderLayout(5, 5));
       
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        TitledBorder title = BorderFactory.createTitledBorder(loweredetched, "Entities");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font("Sans-Serif", Font.BOLD, 16));
        setBorder(title);

        createBotOptionPanel();
        createBotTablePanel();

        add(botOptions, BorderLayout.EAST);
        add(entityLists, BorderLayout.CENTER);
        add(new JLabel("  "), BorderLayout.WEST); //margin
        add(new JLabel("  "), BorderLayout.SOUTH); //margin
    }
    
    /**
     * Create the panel that shows the actions that can be done.
     */
    private void createBotOptionPanel() {
        botOptions.setLayout(new GridLayout(15, 2));
        Border margin = BorderFactory.createEmptyBorder(8, 8, 8, 8);
        botOptions.setBorder(margin);
    }

    /**
     * Create the panel that shows the list of bots.
     */
    private void createBotTablePanel() {
        entityLists.setLayout(new BorderLayout(5, 5));
        
        JTable botTable = new JTable();
        botList = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //make all cells not editable
                return false;
            }
        };

        botTable.setModel(botList);
        botList.addColumn("Robot");
        botList.addColumn("Controller");
        botTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        botTable.getColumnModel().getColumn(1).setPreferredWidth(100);

        JTable epartnerTable = new JTable();
        epartnerList = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //make all cells not editable
                return false;
            }
        };

        epartnerTable.setModel(epartnerList);
        epartnerList.addColumn("E-partner");
        epartnerTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        
        botScrollPane = new JScrollPane(botTable);
        botScrollPane.setPreferredSize(new Dimension(300, 300));
        epartnerScrollPane = new JScrollPane(epartnerTable);
        epartnerScrollPane.setPreferredSize(new Dimension(200, 300));
        
        entityLists.add(botScrollPane, BorderLayout.WEST);
        entityLists.add(epartnerScrollPane, BorderLayout.EAST);
    }
        
    /**
     * Executes action that needs to happen when the "New bot" button is pressed.
     * TODO Open BotStore window
     */
    public void addNewAction() {
        System.out.println("Go to Bot Store");
    }

    /**
     * Executes action that needs to happen when the "Modify bot" button is pressed.
     * TODO Open BotStore window
     */
    public void modifyAction() {
        System.out.println("Go to Bot Store");
    }

    /**
     * Executes action that needs to happen when the "Rename bot" button is pressed.
     * TODO Save name change
     */
    public void renameAction() {
        if (newBotName.getText().length() == 0) {
            showMessageDialog(null, "Please enter a name.");
        } else {
            System.out.println("Name set to " + newBotName.getText());
        }
    }

    /**
     * Executes action that needs to happen when the "Duplicate bot" button is pressed.
     * TODO Create new bot(s) and add to table
     */
    public void duplicateAction() {
        int count = (Integer) duplicateAmount.getValue();
        int response = showConfirmDialog(null, "Are you sure you want to duplicate this bot " + count + " times?",
                "Duplicate Bot Confirmation", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            System.out.println("Bot duplicated " + count + " times.");
        }
    }

    /**
     * Executes action that needs to happen when the "Delete bot" button is pressed.
     * TODO Delete bot from table
     */
    public void deleteAction() {
        int response = showConfirmDialog(null, "Are you sure you want to delete this bot?", "", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            System.out.println("Bot deleted");
        }
    }

    /**
     * Create a pop up message with the the given text. The user has to press OK to continue.
     *
     * @param parent The parent component who's frame is to be used. If null then a frame is created.
     * @param text   The text to be displayed in the message dialog.
     */
    protected void showMessageDialog(Component parent, String text) {
        JOptionPane.showMessageDialog(parent, text);
    }

    /**
     * @param parent     Determines the Frame in which the dialog is displayed; if null, or if the parentComponent has no Frame, a default Frame is used
     * @param text       The text to display
     * @param title      The title for the dialog
     * @param optionType An int designating the options available on the dialog: JOptionPane.YES_NO_OPTIONS,
     *                   JOptionPane.YES_NO_CANCEL_OPTION or JOptionPane.OK_CANCEL_OPTION,
     * @return An int indicating the option selected by the user
     */
    protected int showConfirmDialog(Component parent, String text, String title, int optionType) {
        return JOptionPane.showConfirmDialog(parent, text, title, optionType);
    }

    /**
     * Returns the table with the list of bots.
     *
     * @return The table that contains the bots.
     */
    public DefaultTableModel getTable() {
        return botList;
    }

    /**
     * Returns the new bot name
     *
     * @return The new bot name
     */
    public String getNewBotNameLabelText() {
        return newBotName.getText();
    }

    /**
     * Sets the new bot name
     *
     * @param newBotName The new bot name
     */
    public void setNewBotNameLabelText(String newBotName) {
        this.newBotName.setText(newBotName);
    }

    /**
     * Return the JSpinner which users can use to select the amount of duplicates.
     *
     * @return The JSpinner counting the duplicate amount.
     */
    public JSpinner getDuplicateAmount() {
        return duplicateAmount;
    }
}

