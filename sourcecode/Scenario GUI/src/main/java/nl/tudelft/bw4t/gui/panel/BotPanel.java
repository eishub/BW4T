package nl.tudelft.bw4t.gui.panel;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
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
    private DefaultTableModel botList;
    private JScrollPane botScrollPane;

    private JButton newBot = new JButton("New bot");
    private JButton modifyBot = new JButton("Modify bot");
    private JButton renameBot = new JButton("Rename bot");
    private JButton duplicateBot = new JButton("Duplicate bot");
    private JButton deleteBot = new JButton("Delete bot");

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
        TitledBorder title = BorderFactory.createTitledBorder(loweredetched, "Bot Panel");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font("Sans-Serif", Font.BOLD, 16));
        setBorder(title);
             
        createBotCountPanel();
        createBotOptionPanel();
        createBotTablePanel();


        add(botCountInfo, BorderLayout.NORTH);
        add(botOptions, BorderLayout.EAST);
        add(botScrollPane, BorderLayout.CENTER);
    }

    /**
     * Create the panel that shows the current bot count.
     */
    private void createBotCountPanel() {
        botCountInfo.setLayout(new GridLayout(1, 6));
        Border margin = BorderFactory.createEmptyBorder(8, 8, 8, 8);
        botCountInfo.setBorder(margin);

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

    /**
     * Create the panel that shows the actions that can be done.
     */
    private void createBotOptionPanel() {
        botOptions.setLayout(new GridLayout(15, 2));
        Border margin = BorderFactory.createEmptyBorder(8, 8, 8, 8);
        botOptions.setBorder(margin);

        botOptions.add(newBot);
        botOptions.add(new JLabel(""));
        botOptions.add(modifyBot);
        botOptions.add(new JLabel(""));
        botOptions.add(newBotName);
        botOptions.add(renameBot);
        botOptions.add(new JLabel(""));
        botOptions.add(duplicateAmount);
        botOptions.add(duplicateBot);
        botOptions.add(new JLabel(""));
        botOptions.add(deleteBot);
    }

    /**
     * Create the panel that shows the list of bots.
     */
    private void createBotTablePanel() {
        JTable botTable = new JTable();
        botList = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //make all cells not editable
                return false;
            }
        };

        botTable.setModel(botList);
        botList.addColumn("Bot");
        botList.addColumn("Type");

        botScrollPane = new JScrollPane(botTable);
        Border margin = BorderFactory.createEmptyBorder(0, 8, 8, 0);
        botScrollPane.setBorder(margin);
    }
        
    /*public void addBot(String botName, String botType){
        String[] botEntry = {botName, botType};
    	botList.addRow(botEntry);
    }*/

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
     * Returns the button to create a new bot.
     *
     * @return The button to create a new bot.
     */
    public JButton getNewBot() {
        return newBot;
    }


    /**
     * Returns the button to modify a bot.
     *
     * @return The button to modify a bot.
     */
    public JButton getModifyBot() {
        return modifyBot;
    }

    /**
     * Returns the button to rename a bot.
     *
     * @return The button to rename a bot.
     */
    public JButton getRenameBot() {
        return renameBot;
    }

    /**
     * Returns the button to duplicate a bot.
     *
     * @return The button to duplicate a bot.
     */
    public JButton getDuplicateBot() {
        return duplicateBot;
    }

    /**
     * Returns the button to delete a bot.
     *
     * @return The button to delete a bot.
     */
    public JButton getDeleteBot() {
        return deleteBot;
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

