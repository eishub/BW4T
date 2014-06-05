package nl.tudelft.bw4t.scenariogui.panel.gui;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import nl.tudelft.bw4t.agent.EntityType;
import nl.tudelft.bw4t.scenariogui.util.EntityTableModel;

/**
 * The EntityPanel class represents right pane of the MainPanel. It shows a list
 * of bots and a list of e-partners and the actions that are possible to edit
 * bots and e-partners.
 * <p>
 * 
 * @version 0.1
 * @since 12-05-2014
 */
public class EntityPanel extends JPanel {

	private static final long serialVersionUID = 6488182242349086899L;

	private static final String NUMBER_BOTS_COLUMN = "Number of bots";

	private static final String NUMBER_EPARTNERS_COLUMN = "Number of e-partners";

	private static final int FONT_SIZE = 16;

	private static final int BOT_OPTION_PANEL_GRID_ROWS_AMT = 15;

	private static final int SCROLL_PANE_WIDTH = 500;

	private static final int SCROLL_PANE_HEIGHT = 200;

	private static final String ZERO = "0";

	private JPanel botOptions = new JPanel();

	private JPanel botToolbar = new JPanel();

	private JPanel botMenu = new JPanel();

	private JPanel botPane = new JPanel();
	// TODO Add menuitems for the standardbots

	private JPanel botCounter = new JPanel();

	private DefaultTableModel botList;
	// TODO add actionlisteners for the menu items

	private JTable botTable;

	private JScrollPane botScrollPane;

	private JTextField botCountField = new JTextField();

	private JButton newBot = new JButton("New Bot");

	private JButton botDropDownButton = new BasicArrowButton(
			SwingConstants.SOUTH);

	private JPopupMenu botDropDownMenu = new JPopupMenu();

	private JMenuItem standardBot1 = new JMenuItem("StandardBot1");

	private JMenuItem standardBot2 = new JMenuItem("StandardBot2");

	private JButton modifyBot = new JButton("Modify Bot");

	private JButton deleteBot = new JButton("Delete Bot");

	private JPanel epartnerToolbar = new JPanel();

	private JPanel epartnerPane = new JPanel();

	private JPanel epartnerCounter = new JPanel();

	private DefaultTableModel epartnerList;

	private JTable ePartnerTable;

	private JScrollPane epartnerScrollPane;

	private JTextField epartnerCountField = new JTextField();

	private JButton newEpartner = new JButton("New E-partner");

	private JButton modifyEpartner = new JButton("Modify E-partner");

	private JButton deleteEpartner = new JButton("Delete E-partner");

	private static final int BOT_OPTION_PANEL_MARGIN_WIDTH = 8;	

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
	 * Executes the action that needs to happen when the "arrow" button is
	 * pressed.
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

		botList = new EntityTableModel(EntityType.AGENT);

		botTable.setModel(botList);
		botList.addColumn("Bot");
		botList.addColumn("Controller");
		botList.addColumn(NUMBER_BOTS_COLUMN);

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
		epartnerList = new EntityTableModel(EntityType.EPARTNER);

		ePartnerTable.setModel(epartnerList);
		epartnerList.addColumn("E-partner");
		epartnerList.addColumn(NUMBER_EPARTNERS_COLUMN);

		epartnerScrollPane = new JScrollPane(ePartnerTable);
		epartnerScrollPane.setPreferredSize(new Dimension(SCROLL_PANE_WIDTH,
				SCROLL_PANE_HEIGHT));
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
	public int getSelectedBotRow() {
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

	/**
	 * Returns if changes has been made to the default configuration.
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
}
