package nl.tudelft.bw4t.scenariogui.gui.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;

/**
 * The EntityPanel class represents right pane of the MainPanel. It shows a list
 * of bots and a list of e-partners and the actions that are possible to edit
 * bots and e-partners.
 *
 * @author Katia
 */
public class EntityPanel extends JPanel {

    /**
     * The number of bots column.
     */
    private static final String NUMBER_BOTS_COLUMN = "Number of bots";

    /**
     * The number of e-partners
     */
    private static final String NUMBER_EPARTNERS_COLUMN = "Number of e-partners";

    /**
     * The maximum value for the spinner.
     */
    private static final int MAXIMUM_SPINNER_VALUE = 100;

    /**
     * The spinner model.
     */
    private SpinnerModel model = new SpinnerNumberModel(1, 1,
            MAXIMUM_SPINNER_VALUE, 1);

    /**
     * The amount to duplicate a bot by when duplicating it.
     */
    private JSpinner duplicateAmount = new JSpinner(model);

    /**
     * The font size.
     */
    private static final int FONT_SIZE = 16;

    /**
     * The amount of rows in the grid for the bot option panel.
     */
    private static final int BOT_OPTION_PANEL_GRID_ROWS_AMT = 15;

    /**
     * The margin width.
     */
    private static final int BOT_OPTION_PANEL_MARGIN_WIDTH = 8;

    /**
     * The width of the scroll pane.
     */
    private static final int SCROLL_PANE_WIDTH = 500;

    /**
     * The height of the scroll pane.
     */
    private static final int SCROLL_PANE_HEIGHT = 200;

    /**
     * String representation of the number 0.
     */
    private static final String ZERO = "0";

    /**
     * The bot options.
     */
    private JPanel botOptions = new JPanel();

    /**
     * The bot toolbar.
     */
    private JPanel botToolbar = new JPanel();

    /**
     * The dropdown menu for adding a new bot.
     */
    private JPanel botMenu = new JPanel();

    /**
     * The bot pane.
     */
    private JPanel botPane = new JPanel();
    //TODO Add menuitems for the standardbots

    /**
     * The bot counter.
     */
    private JPanel botCounter = new JPanel();

    /**
     * The list of bots.
     */
    private DefaultTableModel botList;
    //TODO add actionlisteners for the menu items

    /**
     * The GUI element for the table
     */
    private JTable botTable;

    /**
     * The scrollpane of the bot list.
     */
    private JScrollPane botScrollPane;

    /**
     * The bot count field.
     */
    private JTextField botCountField = new JTextField();

    /**
     * The button for adding a new bot.
     */
    private JButton newBot = new JButton("New Bot");

    /**
     * The button to open the dropdown menu for adding one of the standard bots.
     */
    private JButton botDropDownButton = new BasicArrowButton(SwingConstants.SOUTH);

    /**
     * The dropdown menu for the standard bots.
     */
    private JPopupMenu botDropDownMenu = new JPopupMenu();

    /**
     * The standardBot1 menu item
     */
    private JMenuItem standardBot1 = new JMenuItem("StandardBot1");

    /**
     * The standardBot2 menu item
     */
    private JMenuItem standardBot2 = new JMenuItem("StandardBot2");

    /**
     * The button to modify a bot.
     */
    private JButton modifyBot = new JButton("Modify Bot");

    /**
     * The button to delete a bot.
     */
    private JButton deleteBot = new JButton("Delete Bot");

    /**
     * The toolbar for the e-partner.
     */
    private JPanel epartnerToolbar = new JPanel();

    /**
     * The panel for the e-partner.
     */
    private JPanel epartnerPane = new JPanel();

    /* Initial value 1, maximum 100, minimum 1, steps of 1 */

    /**
     * The counter for the e-partner.
     */
    private JPanel epartnerCounter = new JPanel();

    /**
     * The table for the e-partner.
     */
    private DefaultTableModel epartnerList;

    /**
     * The GUI element for the table
     */
    private JTable ePartnerTable;

    /**
     * The scrollpane for the e-partner table.
     */
    private JScrollPane epartnerScrollPane;

    /**
     * The e-partner counter.
     */
    private JTextField epartnerCountField = new JTextField();

    /**
     * The button for adding a new e-partner.
     */
    private JButton newEpartner = new JButton("New E-partner");

    /**
     * The button for modifying an e-partner.
     */
    private JButton modifyEpartner = new JButton("Modify E-partner");

    /**
     * The button for deleting an e-partner.
     */
    private JButton deleteEpartner = new JButton("Delete E-partner");

    /**
     * Create an EntityPanel object.
     */
    public EntityPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Border loweredetched = BorderFactory
                .createEtchedBorder(EtchedBorder.LOWERED);
        TitledBorder title = BorderFactory.createTitledBorder(loweredetched,
                "Entities");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font("Sans-Serif", Font.BOLD, FONT_SIZE));
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
        botOptions.setLayout(new GridLayout(BOT_OPTION_PANEL_GRID_ROWS_AMT, 2));
        Border margin = BorderFactory.createEmptyBorder(
                BOT_OPTION_PANEL_MARGIN_WIDTH, BOT_OPTION_PANEL_MARGIN_WIDTH,
                BOT_OPTION_PANEL_MARGIN_WIDTH, BOT_OPTION_PANEL_MARGIN_WIDTH);
        botOptions.setBorder(margin);
    }

    /**
     * Create the panel in which the bot list and options will be.
     */
    private void createBotPane() {
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

        createDropDownMenuButtons();

        botToolbar.add(botMenu);
        botToolbar.add(new JLabel());
        botToolbar.add(modifyBot);
        botToolbar.add(new JLabel());
        botToolbar.add(deleteBot);
    }

    /**
     * Create the dropdown menu for adding standard bots.
     */
    private void createDropDownMenuButtons() {
        botMenu.setLayout(new BorderLayout());

        botDropDownMenu.add(standardBot1);
        botDropDownMenu.add(standardBot2);
        botDropDownButton.add(botDropDownMenu);

        botMenu.add(botDropDownButton, BorderLayout.EAST);
        botMenu.add(newBot, BorderLayout.CENTER);
    }

    /**
     * Executes the action that needs to happen when the "arrow" button is pressed.
     */
    public void showBotDropDown() {
        botDropDownMenu.show(newBot, 0, botDropDownButton.getHeight());
    }

    /**
     * Create the table that contains the list of bots.
     */
    private void createBotTable() {

        botTable = new JTable();
        botTable.getTableHeader().setReorderingAllowed(false);

        botList = new DefaultTableModel() {
            
            /*@Override
            public boolean isCellEditable(final int row, final int column) {
                if (column == 0) {
                    return false;
                }
                return true;
            }*/

            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 2) {
                    return Integer.class;
                }

                return String.class;
            }

        };

        botTable.setModel(botList);
        botList.addColumn("Bot");
        botList.addColumn("Controller");
        botList.addColumn(NUMBER_BOTS_COLUMN);

        botScrollPane = new JScrollPane(botTable);
        botScrollPane.setPreferredSize(new Dimension(
                SCROLL_PANE_WIDTH, SCROLL_PANE_HEIGHT));
        setUpControllerColumn();

        botList.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                updateBotCount();
            }
        });
    }

    /**
     * Create the dropdown lists in the controllers column.
     */
    public void setUpControllerColumn() {
        JComboBox controllers = new JComboBox();
        controllers.addItem("Agent");
        controllers.addItem("Human");
        botTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(controllers));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Testtext");
        botTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
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
        botCountField.setText(ZERO);
        botCountField.setEditable(false);
        botCounter.add(botCountField);
    }

    /**
     * Create the panel in which the E-partner list and toolbar will be.
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
     * Create the toolbar for the E-partners.
     */
    private void createEpartnerToolbar() {
        epartnerToolbar.setLayout(new GridLayout(1, 0));

        epartnerToolbar.add(newEpartner);
        epartnerToolbar.add(new JLabel());
        epartnerToolbar.add(modifyEpartner);
        epartnerToolbar.add(new JLabel());
        epartnerToolbar.add(deleteEpartner);
    }

    /**
     * Create the table that contains the list of E-partners.
     */
    private void createEpartnerTable() {

        ePartnerTable = new JTable();
        ePartnerTable.getTableHeader().setReorderingAllowed(false);
        epartnerList = new DefaultTableModel() {
            /*@Override
            public boolean isCellEditable(final int row, final int column) {
                if (column == 1) {
                    return true;
                }
                return false;
            }*/

            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 1) {
                    return Integer.class;
                }

                return String.class;
            }
        };

        ePartnerTable.setModel(epartnerList);
        epartnerList.addColumn("E-partner");
        epartnerList.addColumn(NUMBER_EPARTNERS_COLUMN);

        epartnerScrollPane = new JScrollPane(ePartnerTable);
        epartnerScrollPane.setPreferredSize(new Dimension(
                SCROLL_PANE_WIDTH, SCROLL_PANE_HEIGHT));

        epartnerList.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                updateEPartnerCount();
            }
        });
    }

    /**
     * Create a E-partner counter.
     */
    private void createEpartnerCounter() {
        epartnerCounter.setLayout(new GridLayout(1, 0));

        epartnerCounter.add(new JLabel());
        epartnerCounter.add(new JLabel());
        epartnerCounter.add(new JLabel());
        epartnerCounter.add(new JLabel("Total number of e-partners:"));
        epartnerCountField.setText(ZERO);
        epartnerCountField.setEditable(false);
        epartnerCounter.add(epartnerCountField);
    }

    /**
     * Create a pop up message with the the given text. The user has to press OK
     * to continue.
     *
     * @param parent The parent component who's frame is to be used. If null then a
     *               frame is created.
     * @param text   The text to be displayed in the message dialog.
     */
    protected final void showMessageDialog(
            final Component parent, final String text) {
        ScenarioEditor.option.showMessageDialog(parent, text);
    }

    /**
     * @param parent     Determines the Frame in which the dialog is displayed; if
     *                   null, or if the parentComponent has no Frame, a default Frame
     *                   is used
     * @param text       The text to display
     * @param title      The title for the dialog
     * @param optionType An int designating the options available on the dialog:
     *                   JOptionPane.YES_NO_OPTIONS, JOptionPane.YES_NO_CANCEL_OPTION
     *                   or JOptionPane.OK_CANCEL_OPTION,
     * @return An int indicating the option selected by the user
     */
    public int showConfirmDialog(final Component parent,
                                 final String text,
                                 final String title, final int optionType, final int messageType) {
        return ScenarioEditor.option.showConfirmDialog(parent, text, title, optionType, messageType);
    }

    /**
     * Returns the table with the list of bots.
     *
     * @return The table that contains the bots.
     */
    public final JTable getBotTable() {
        return botTable;
    }

    /**
     * Returns the table model with the list of bots.
     *
     * @return The table model that contains the bots.
     */
    public final DefaultTableModel getBotTableModel() {
        return botList;
    }

    /**
     * Returns the table with the list of E-partners.
     *
     * @return The table that contains the E-partners.
     */
    public final JTable getEPartnerTable() {
        return ePartnerTable;
    }

    /**
     * Returns the table with the list of E-partners.
     *
     * @return The table that contains the E-partners.
     */
    public final DefaultTableModel getEPartnerTableModel() {
        return epartnerList;
    }

    /**
     * Returns the "arrow" button.
     *
     * @return The "arrow" button.
     */
    public JButton getDropDownButton() {
        return botDropDownButton;
    }


    /**
     * Returns the new bot name
     *
     * @return The new bot name
     */
    /*
     * public String getNewBotNameLabelText() { return newBotName.getText(); }
     */

    /**
     * Sets the new bot name
     *
     * @param newBotName
     *            The new bot name
     */
    /*
     * public void setNewBotNameLabelText(String newBotName) {
     * this.newBotName.setText(newBotName); }
     */

    /**
     * Return the JSpinner users can use to select the amount of duplicates.
     *
     * @return The JSpinner counting the duplicate amount.
     */
    public final JSpinner getDuplicateAmount() {
        return duplicateAmount;
    }

    /**
     * Returns the amount of bots.
     *
     * @return The amount of bots.
     */
    public final int getBotCount() {
        int numBots = 0;

        for (int i = 0; i < botList.getRowCount(); i++) {
            numBots += Integer.valueOf(botList.getValueAt(i, 2).toString());
        }
        return numBots;
    }

    /**
     * Updates the bot count on the EntityPanel.
     */
    public final void updateBotCount() {
        Integer bots = getBotCount();
        botCountField.setText(bots.toString());
    }

    /**
     * Returns the amount of epartners.
     *
     * @return The amount of epartners.
     */
    public final int getEPartnerCount() {
        int numEPartner = 0;

        for (int i = 0; i < epartnerList.getRowCount(); i++) {
            numEPartner += Integer.valueOf(epartnerList.getValueAt(i, 1).toString());
        }
        return numEPartner;
    }

    /**
     * Updates the entities count on the EntityPanel.
     */
    public final void updateEPartnerCount() {
        Integer epartners = getEPartnerCount();
        epartnerCountField.setText(epartners.toString());
    }

    /**
     * Returns the button to create a new E-partner.
     *
     * @return The button to create a new E-partner.
     */
    public JButton getNewEPartnerButton() {
        return newEpartner;
    }

    /**
     * Returns the button to delete an E-partner.
     *
     * @return The button to delete an E-partner.
     */
    public JButton getDeleteEPartnerButton() {
        return deleteEpartner;
    }

    /**
     * Returns the button to modify an E-partner.
     *
     * @return The button to modify an E-partner.
     */
    public JButton getModifyEPartnerButton() {
        return modifyEpartner;
    }

    /**
     * Returns the button to add a bot.
     *
     * @return The add bot button.
     */
    public JButton getNewBotButton() {
        return newBot;
    }

    /**
     * Returns the button to modify a bot.
     *
     * @return The modify bot button.
     */
    public JButton getModifyBotButton() {
        return modifyBot;
    }

    /**
     * Returns the button to delete a bot.
     *
     * @return The delete bot button.
     */
    public JButton getDeleteBotButton() {
        return deleteBot;
    }

    /**
     * Returns the selected row in the bot table.
     *
     * @return The selected row.
     */
    public final int getSelectedBotRow() {
        return botTable.getSelectedRow();
    }

    /**
     * Returns the selected row in the E-partner table.
     *
     * @return The selected row.
     */
    public final int getSelectedEPartnerRow() {
        return ePartnerTable.getSelectedRow();
    }
}
