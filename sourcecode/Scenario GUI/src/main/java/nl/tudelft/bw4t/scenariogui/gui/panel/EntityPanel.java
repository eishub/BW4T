package nl.tudelft.bw4t.scenariogui.gui.panel;

import java.awt.BorderLayout;
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
 * The EntityPanel class represents right pane of the MainPanel.
 * It shows a list of bots and a list of e-partners and the actions
 * that are possible to edit bots and e-partners.
 *
 * @author Katia
 */
public class EntityPanel extends JPanel {

    private JPanel botOptions = new JPanel();

    private JPanel botToolbar = new JPanel();
    private JPanel botPane = new JPanel();
    private JPanel botCounter = new JPanel();
    private DefaultTableModel botList;
    private JScrollPane botScrollPane;
    private JTextField botCountField = new JTextField();

    private JButton newBot = new JButton("New Bot");
    private JButton modifyBot = new JButton("Modify Bot");
    private JButton deleteBot = new JButton("Delete Bot");

    private JPanel epartnerToolbar = new JPanel();
    private JPanel epartnerPane = new JPanel();
    private JPanel epartnerCounter = new JPanel();
    private DefaultTableModel epartnerList;
    private JScrollPane epartnerScrollPane;
    private JTextField epartnerCountField = new JTextField();

    private JButton newEpartner = new JButton("New E-partner");
    private JButton modifyEpartner = new JButton("Modify E-partner");
    private JButton deleteEpartner = new JButton("Delete E-partner");


    /* Initial value 1, maximum 100, minimum 1, steps of 1 */
    private SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
    private JSpinner duplicateAmount = new JSpinner(model);

    /**
     * Create a EntityPanel object.
     */
    public EntityPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        TitledBorder title = BorderFactory.createTitledBorder(loweredetched, "Entities");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font("Sans-Serif", Font.BOLD, 16));
        setBorder(title);

        createBotOptionPanel();
        createBotPane();
        createEpartnerPane();

        add(botPane);
        add(new JLabel("  "));
        add(epartnerPane);

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
     * Create the panel in which the bot list and options will be.
     */
    private void createBotPane(){
        botPane.setLayout(new BorderLayout());

        createBotToolbar();
        createBotTable();
        createBotCounter();

        botPane.add(botToolbar, BorderLayout.NORTH);
    	botPane.add(botScrollPane, BorderLayout.CENTER);
    	botPane.add(botCounter, BorderLayout.SOUTH);
    }

    /**
     * Create the toolbar for the bots.
     */
    private void createBotToolbar() {
    	botToolbar.setLayout(new GridLayout(1, 0));

    	botToolbar.add(newBot);
    	botToolbar.add(new JLabel());
    	botToolbar.add(modifyBot);
    	botToolbar.add(new JLabel());
    	botToolbar.add(deleteBot);
    }

    /**
     * Create the table that contains the list of bots.
     */
    private void createBotTable() {

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
        botList.addColumn("Controller");
        botList.addColumn("Nr");

        botScrollPane = new JScrollPane(botTable);
        botScrollPane.setPreferredSize(new Dimension(500, 200));
    }

    /**
     * Create a bot counter.
     */
    private void createBotCounter() {
    	botCounter.setLayout(new GridLayout(1, 0));

    	botCounter.add(new JLabel());
    	botCounter.add(new JLabel());
    	botCounter.add(new JLabel());
    	botCounter.add(new JLabel("Total number of bots:"));
    	botCountField.setEditable(false);
    	botCounter.add(botCountField);
    }

    /**
     * Create the panel in which the e-partner list and toolbar will be.
     */
    private void createEpartnerPane() {
    	epartnerPane.setLayout(new BorderLayout());

    	createEpartnerToolbar();
    	createEpartnerTable();
    	createEpartnerCounter();

    	epartnerPane.add(epartnerToolbar, BorderLayout.NORTH);
    	epartnerPane.add(epartnerScrollPane, BorderLayout.CENTER);
    	epartnerPane.add(epartnerCounter, BorderLayout.SOUTH);
    }

    /**
     * Create the toolbar for the e-partners.
     */
    private void createEpartnerToolbar(){
    	epartnerToolbar.setLayout(new GridLayout(1, 0));

    	epartnerToolbar.add(newEpartner);
    	epartnerToolbar.add(new JLabel());
    	epartnerToolbar.add(modifyEpartner);
    	epartnerToolbar.add(new JLabel());
    	epartnerToolbar.add(deleteEpartner);
    }

    /**
     * Create the table that contains the list of e-partners.
     */
    private void createEpartnerTable() {

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
        epartnerList.addColumn("Nr");

        epartnerScrollPane = new JScrollPane(epartnerTable);
        epartnerScrollPane.setPreferredSize(new Dimension(500, 200));
    }

    /**
     * Create a e-partner counter.
     */
    private void createEpartnerCounter() {
    	epartnerCounter.setLayout(new GridLayout(1, 0));

    	epartnerCounter.add(new JLabel());
    	epartnerCounter.add(new JLabel());
    	epartnerCounter.add(new JLabel());
    	epartnerCounter.add(new JLabel("Total number of e-partners:"));
    	epartnerCountField.setEditable(false);
    	epartnerCounter.add(epartnerCountField);
    }

    /**
     * Executes action that needs to happen when the "New bot" button is
     * pressed.
     * TODO Open BotStore window
     */
    public void addBotAction() {
        System.out.println("Go to Bot Store");
    }

    /**
     * Executes action that needs to happen when the "Modify bot" button is
     * pressed.
     * TODO Open BotStore window
     */
    public void modifyBotAction() {
        System.out.println("Go to Bot Store");
    }

    /**
     * Executes action that needs to happen when the "Rename bot" button is
     * pressed.
     * TODO Save name change
     */
    /*public void renameAction() {
        if (newBotName.getText().length() == 0) {
            showMessageDialog(null, "Please enter a name.");
        } else {
            System.out.println("Name set to " + newBotName.getText());
        }
    }*/

    /**
     * Executes action that needs to happen when the "Duplicate bot" button is
     * pressed.
     * TODO Create new bot(s) and add to table
     */
    public void duplicateAction() {
        int count = (Integer) duplicateAmount.getValue();
        int response = showConfirmDialog(null,
        		"Are you sure you want to duplicate this bot " + count + " times?",
                "Duplicate Bot Confirmation", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            System.out.println("Bot duplicated " + count + " times.");
        }
    }

    /**
     * Executes action that needs to happen when the "Delete bot" button is
     * pressed.
     * TODO Delete bot from table
     */
    public void deleteBotAction() {
        int response = showConfirmDialog(null,
        		"Are you sure you want to delete this bot?", "",
        		JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            System.out.println("Bot deleted");
        }
    }

    /**
     * Create a pop up message with the the given text.
     * The user has to press OK to continue.
     *
     * @param parent The parent component who's frame is to be used.
     * If null then a frame is created.
     * @param text   The text to be displayed in the message dialog.
     */
    protected void showMessageDialog(Component parent, String text) {
        JOptionPane.showMessageDialog(parent, text);
    }

    /**
     * @param parent     Determines the Frame in which the dialog is displayed;
     * if null, or if the parentComponent has no Frame, a default Frame is used
     * @param text       The text to display
     * @param title      The title for the dialog
     * @param optionType An int designating the options available on the dialog:
     * JOptionPane.YES_NO_OPTIONS, JOptionPane.YES_NO_CANCEL_OPTION or
     * JOptionPane.OK_CANCEL_OPTION,
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
    public DefaultTableModel getBotTable() {
        return botList;
    }

    /**
     * Returns the table with the list of epartners
     *
     * @return The table that contains the epartners
     */
    public DefaultTableModel getEPartnerTable() { return epartnerList; }

    /**
     * Returns the new bot name
     *
     * @return The new bot name
     */
    /*public String getNewBotNameLabelText() {
        return newBotName.getText();
    }*/

    /**
     * Sets the new bot name
     *
     * @param newBotName The new bot name
     */
    /*public void setNewBotNameLabelText(String newBotName) {
        this.newBotName.setText(newBotName);
    }*/

    /**
     * Return the JSpinner users can use to select the amount of duplicates.
     *
     * @return The JSpinner counting the duplicate amount.
     */
    public JSpinner getDuplicateAmount() {
        return duplicateAmount;
    }

    /**
     * Updates the entities count on the EntityPanel
     */
    public void updateEntitiesCount() {
        Integer bots = botList.getRowCount();
        Integer epartners = epartnerList.getRowCount();

        botCountField.setText(bots.toString());
        epartnerCountField.setText(epartners.toString());
    }

    /**
     * Returns the amount of bots.
     * @return The amount of bots.
     */
    public int getBotCount() {
        return botList.getRowCount();
    }

    /**
     * Returns the amount of epartners.
     * @return The amount of epartners.
     */
    public int getEPartnerCount() {
        return epartnerList.getRowCount();
    }

    /**
     * Returns the button to add a bot.
     * @return The add bot button.
     */
    public JButton getNewBotButton() {
        return newBot;
    }

    /**
     * Returns the button to modify a bot.
     * @return The modify bot button.
     */
    public JButton getModifyBotButton() {
        return modifyBot;
    }

    /**
     * Returns the button to delete a bot.
     * @return The delete bot button.
     */
    public JButton getDeleteBotButton() {
        return deleteBot;
    }
}

