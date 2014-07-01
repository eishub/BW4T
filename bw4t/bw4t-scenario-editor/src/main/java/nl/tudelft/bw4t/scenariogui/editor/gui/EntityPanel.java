package nl.tudelft.bw4t.scenariogui.editor.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import nl.tudelft.bw4t.map.EntityType;
import nl.tudelft.bw4t.scenariogui.util.AbstractTableModel;
import nl.tudelft.bw4t.scenariogui.util.EntityJTable;
import nl.tudelft.bw4t.scenariogui.util.EntityTableModel;
import nl.tudelft.bw4t.scenariogui.util.RobotTableModel;

/**
 * The EntityPanel class represents right pane of the MainPanel. It shows a list
 * of bots and a list of e-partners and the actions that are possible to edit
 * bots and e-partners.
 */
public class EntityPanel extends JPanel {
    private static final long serialVersionUID = 6488182242349086899L;

    public static final String AGENT_FILE = "Agent file";

    public static final String NUMBER_BOTS_COLUMN = "Number of bots";

    public static final String NUMBER_EPARTNERS_COLUMN = "Number of e-partners";

    private static final int FONT_SIZE = 16;

    private static final int BOT_OPTION_PANEL_GRID_ROWS_AMT = 15;

    private static final int SCROLL_PANE_WIDTH = 500;

    private static final int SCROLL_PANE_HEIGHT = 200;

    private static final String ZERO = "0";

    private JPanel botOptions = new JPanel();

    private JPanel botToolbar = new JPanel();

    private JPanel botMenu = new JPanel();

    private JPanel botPane = new JPanel();

    private JPanel botCounter = new JPanel();

    private AbstractTableModel botList;

    private EntityJTable botTable;
    
    private static String botTableName;

    private JScrollPane botScrollPane;

    private JTextField botCountField = new JTextField();

    private JButton newBot = new JButton("New Bot");

    private JButton botDropDownButton = new BasicArrowButton(
            SwingConstants.SOUTH);

    private JPopupMenu botDropDownMenu = new JPopupMenu();

    private JMenuItem standardBot = new JMenuItem("Standard Bot");

    private JMenuItem standardBotBig = new JMenuItem("Big Standard Bot");

    private JMenuItem standardBotGripper = new JMenuItem("Gripper Bot");

    private JMenuItem standardBotBigGripper = new JMenuItem("Big Gripper Bot");

    private JMenuItem standardBotSeeer = new JMenuItem("Seeer Bot");

    private JMenuItem standardBotBigSeeer = new JMenuItem("Big Seeer Bot");

    private JMenuItem standardBotCommunicator = new JMenuItem("Communicator Bot");

    private JMenuItem standardBotBigCommunicator = new JMenuItem("Big Communicator Bot");

    private JButton modifyBot = new JButton("Modify Bot");

    private JButton deleteBot = new JButton("Delete Bot");

    private JPanel epartnerToolbar = new JPanel();

    private JPanel epartnerPane = new JPanel();

    private JPanel epartnerCounter = new JPanel();

    private DefaultTableModel epartnerList;

    private EntityJTable ePartnerTable;
    
    private static String ePartnerTableName;

    private JScrollPane epartnerScrollPane;

    private JTextField epartnerCountField = new JTextField();

    private JButton newEpartner = new JButton("New E-partner");

    private JButton modifyEpartner = new JButton("Modify E-partner");

    private JButton deleteEpartner = new JButton("Delete E-partner");

    private static final int BOT_OPTION_PANEL_MARGIN_WIDTH = 8;
    
    //This is just for the modifyBot/modifyEpartner test,
    //to see if the bot/epartner store actually opened
    private boolean bs = false;
    private boolean es = false;
    
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

    private void createBotOptionPanel() {
        botOptions.setLayout(new GridLayout(BOT_OPTION_PANEL_GRID_ROWS_AMT, 2));
        Border margin = BorderFactory.createEmptyBorder(
                BOT_OPTION_PANEL_MARGIN_WIDTH, BOT_OPTION_PANEL_MARGIN_WIDTH,
                BOT_OPTION_PANEL_MARGIN_WIDTH, BOT_OPTION_PANEL_MARGIN_WIDTH);
        botOptions.setBorder(margin);
    }

    private void createBotPane() {
        botPane.setLayout(new BorderLayout());

        createBotToolbar();
        createBotTable();
        createBotCounter();

        botPane.add(botToolbar, BorderLayout.NORTH);
        botPane.add(botScrollPane, BorderLayout.CENTER);
        botPane.add(botCounter, BorderLayout.SOUTH);
    }

    private void createBotToolbar() {
        botToolbar.setLayout(new GridLayout(1, 0));

        createDropDownMenuButtons();

        botToolbar.add(botMenu);
        botToolbar.add(new JLabel());
        botToolbar.add(modifyBot);
        botToolbar.add(new JLabel());
        botToolbar.add(deleteBot);
    }

    private void createDropDownMenuButtons() {
        botMenu.setLayout(new BorderLayout());

        botDropDownMenu.add(standardBot);
        botDropDownMenu.add(standardBotBig);
        botDropDownMenu.add(standardBotGripper);
        botDropDownMenu.add(standardBotBigGripper);
        botDropDownMenu.add(standardBotSeeer);
        botDropDownMenu.add(standardBotBigSeeer);
        botDropDownMenu.add(standardBotCommunicator);
        botDropDownMenu.add(standardBotBigCommunicator);
        botDropDownButton.add(botDropDownMenu);

        botMenu.add(botDropDownButton, BorderLayout.EAST);
        botMenu.add(newBot, BorderLayout.CENTER);
    }

    /**
     * Executes the action that needs to happen when the "arrow" button is
     * pressed.
     */
    public void showBotDropDown() {
        botDropDownMenu.show(newBot, 0, botDropDownButton.getHeight());
    }

    private void createBotTable() {

        botTable = new EntityJTable();
        botTable.getTableHeader().setReorderingAllowed(false);
        botTableName = "botTable";
        botTable.setName(botTableName);
        botList = new RobotTableModel();
        botTable.setModel(botList);

        botScrollPane = new JScrollPane(botTable);
        botScrollPane.setPreferredSize(new Dimension(SCROLL_PANE_WIDTH,
                SCROLL_PANE_HEIGHT));
        setUpControllerColumn();
    }

    /**
     * Create the dropdown lists in the controllers column.
     */
    public void setUpControllerColumn() {
        JComboBox<String> controllers = new JComboBox<String>();
        controllers.addItem(EntityType.AGENT.toString());
        controllers.addItem(EntityType.HUMAN.toString());
        botTable.getColumnModel().getColumn(1)
                .setCellEditor(new DefaultCellEditor(controllers));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        botTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
    }

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

    private void createEpartnerPane() {
        epartnerPane.setLayout(new BorderLayout());

        createEpartnerToolbar();
        createEpartnerTable();
        createEpartnerCounter();

        epartnerPane.add(epartnerToolbar, BorderLayout.NORTH);
        epartnerPane.add(epartnerScrollPane, BorderLayout.CENTER);
        epartnerPane.add(epartnerCounter, BorderLayout.SOUTH);
    }

    private void createEpartnerToolbar() {
        epartnerToolbar.setLayout(new GridLayout(1, 0));

        epartnerToolbar.add(newEpartner);
        epartnerToolbar.add(new JLabel());
        epartnerToolbar.add(modifyEpartner);
        epartnerToolbar.add(new JLabel());
        epartnerToolbar.add(deleteEpartner);
    }

    private void createEpartnerTable() {

        ePartnerTable = new EntityJTable();
        ePartnerTableName = "eparterTable";
        ePartnerTable.setName(ePartnerTableName);
        ePartnerTable.getTableHeader().setReorderingAllowed(false);
        epartnerList = new EntityTableModel(EntityType.EPARTNER);

        ePartnerTable.setModel(epartnerList);
        epartnerList.addColumn("E-partner");
        epartnerList.addColumn(AGENT_FILE);
        epartnerList.addColumn(NUMBER_EPARTNERS_COLUMN);

        epartnerScrollPane = new JScrollPane(ePartnerTable);
        epartnerScrollPane.setPreferredSize(new Dimension(SCROLL_PANE_WIDTH,
                SCROLL_PANE_HEIGHT));
    }

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

    public final EntityJTable getBotTable() {
        return botTable;
    }

    public void setBotTable(EntityJTable botTable) {
        this.botTable = botTable;
    }

    public final AbstractTableModel getBotTableModel() {
        return botList;
    }

    public final EntityJTable getEPartnerTable() {
        return ePartnerTable;
    }

    public void setePartnerTable(EntityJTable ePartnerTable) {
        this.ePartnerTable = ePartnerTable;
    }
    
    public final DefaultTableModel getEPartnerTableModel() {
        return epartnerList;
    }

    public JButton getDropDownButton() {
        return botDropDownButton;
    }
    
    public JMenuItem getAddNewStandardBotMenuItem() {
        return standardBot;
    }
    
    public JMenuItem getAddNewStandardBotBigMenuItem() {
        return standardBotBig;
    }
    
    public JMenuItem getAddNewStandardBotGripperMenuItem() {
        return standardBotGripper;
    }
    
    public JMenuItem getAddNewStandardBotBigGripperMenuItem() {
        return standardBotBigGripper;
    }
    
    public JMenuItem getAddNewStandardBotSeeerMenuItem() {
        return standardBotSeeer;
    }
    
    public JMenuItem getAddNewStandardBotBigSeeerMenuItem() {
        return standardBotBigSeeer;
    }
    
    public JMenuItem getAddNewStandardBotCommunicatorMenuItem() {
        return standardBotCommunicator;
    }
    
    public JMenuItem getAddNewStandardBotBigCommunicatorMenuItem() {
        return standardBotBigCommunicator;
    }

    /**
     * Updates the bot count on the EntityPanel.
     * 
     * @param count The total bot count.
     */
    public final void updateBotCount(Integer count) {
        botCountField.setText(count.toString());
    }

    /**
     * Updates the entities count on the EntityPanel.
     * 
     * @param count The total epartner count.
     */
    public final void updateEPartnerCount(Integer count) {
        epartnerCountField.setText(count.toString());
    }

    public JButton getNewEPartnerButton() {
        return newEpartner;
    }

    public JButton getDeleteEPartnerButton() {
        return deleteEpartner;
    }

    public JButton getModifyEPartnerButton() {
        return modifyEpartner;
    }
    
    public JButton getNewBotButton() {
        return newBot;
    }

    public JButton getModifyBotButton() {
        return modifyBot;
    }

    public JButton getDeleteBotButton() {
        return deleteBot;
    }

    public int getSelectedBotRow() {
        return botTable.getSelectedRow();
    }

    public final int getSelectedEPartnerRow() {
        return ePartnerTable.getSelectedRow();
    }

    /**
     * Returns if changes has been made to the last configuration.
     *
     * @return whether changes have been made.
     */
    public final boolean isDefault() {
        boolean isDefault = true;

        if (this.botList.getRowCount() != 0) {
            isDefault = false;
        } else if (this.epartnerList.getRowCount() != 0) {
            isDefault = false;
        }

        return isDefault;
    }
    
    public static String getBotTableName() {
        return botTableName;
    }
    
    public static String getePartnerTableName() {
        return ePartnerTableName;
    }

    public boolean isBotStore() {
        return bs;
    }

    public void setBotStore(boolean bs) {
        this.bs = bs;
    }

    public boolean isEpartnerStore() {
        return es;
    }

    public void setEpartnerStore(boolean es) {
        this.es = es;
    }
    
    public void addNewBotController(ActionListener controller) {
        getNewBotButton().addActionListener(controller);
    }

    public void addModifyBotController(ActionListener controller) {
        getModifyBotButton().addActionListener(controller);
    }

    public void addDeleteBotController(ActionListener controller) {
        getDeleteBotButton().addActionListener(controller);
    }

    public void addNewEpartnerController(ActionListener controller) {
        getNewEPartnerButton().addActionListener(controller);
    }
    
    public void addModifyEpartnerController(ActionListener controller) {
        getModifyEPartnerButton().addActionListener(controller);
    }

    public void addDeleteEpartnerController(ActionListener controller) {
        getDeleteEPartnerButton().addActionListener(controller);
    }

    public void addDropDownController(ActionListener controller) {
        getDropDownButton().addActionListener(controller);
    }
    
    public void addNewStandardBotController(ActionListener controller) {
        getAddNewStandardBotMenuItem().addActionListener(controller);
    }
    
    public void addNewStandardBotBigController(ActionListener controller) {
        getAddNewStandardBotBigMenuItem().addActionListener(controller);
    }
    
    public void addNewStandardBotGripperController(ActionListener controller) {
        getAddNewStandardBotGripperMenuItem().addActionListener(controller);
    }
    
    public void addNewStandardBotBigGripperController(ActionListener controller) {
        getAddNewStandardBotBigGripperMenuItem().addActionListener(controller);
    }
    
    public void addNewStandardBotSeeerController(ActionListener controller) {
        getAddNewStandardBotSeeerMenuItem().addActionListener(controller);
    }
    
    public void addNewStandardBotBigSeeerController(ActionListener controller) {
        getAddNewStandardBotBigSeeerMenuItem().addActionListener(controller);
    }
    
    public void addNewStandardBotCommunicatorController(ActionListener controller) {
        getAddNewStandardBotCommunicatorMenuItem().addActionListener(controller);
    }
    
    public void addNewStandardBotBigCommunicatorController(ActionListener controller) {
        getAddNewStandardBotBigCommunicatorMenuItem().addActionListener(controller);
    }

    public void addBotTableModelController(TableModelListener controller) {
        getBotTableModel().addTableModelListener(controller);
    }

    public void addEpartnerTableModelController(TableModelListener controller) {
        getEPartnerTableModel().addTableModelListener(controller);
    }
    
    public void addBotTableController(TableModelListener controller) {
        getBotTable().getModel().addTableModelListener(controller);
    }

    public void addEpartnerTableController(TableModelListener controller) {
        getEPartnerTable().getModel().addTableModelListener(controller);
    }

}
