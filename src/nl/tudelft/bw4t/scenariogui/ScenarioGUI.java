package nl.tudelft.bw4t.scenariogui;


import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ScenarioGUI extends JFrame {

    private JPanel contentPane;
    private JTextField txtFieldSelectMap;
    private JTextField txtFieldIpAdress;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ScenarioGUI frame = new ScenarioGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ScenarioGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmOpen = new JMenuItem("Open");
        mnFile.add(mntmOpen);

        JMenuItem mntmSave = new JMenuItem("Save");
        mnFile.add(mntmSave);

        JMenuItem mntmSaveAs = new JMenuItem("Save as...");
        mnFile.add(mntmSaveAs);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mnFile.add(mntmExit);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel_server = new JPanel();
        panel_server.setBorder(new TitledBorder(null, "Server Configurations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_server.setBounds(25, 25, 390, 200);
        contentPane.add(panel_server);
        panel_server.setLayout(new FormLayout(new ColumnSpec[]{
                ColumnSpec.decode("133px:grow"),
                ColumnSpec.decode("124px"),},
                new RowSpec[]{
                        FormFactory.LINE_GAP_ROWSPEC,
                        RowSpec.decode("16px"),
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,}
        ));

        JLabel lblIpAdress = new JLabel("IP adress:");
        panel_server.add(lblIpAdress, "1, 2");

        txtFieldIpAdress = new JTextField();
        panel_server.add(txtFieldIpAdress, "1, 4, fill, default");
        txtFieldIpAdress.setColumns(10);

        JPanel panel_agents = new JPanel();
        panel_agents.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Agents Configurations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_agents.setBounds(460, 25, 390, 443);
        contentPane.add(panel_agents);
        panel_agents.setLayout(new FormLayout(new ColumnSpec[]{
                ColumnSpec.decode("135px"),
                ColumnSpec.decode("119px"),
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,},
                new RowSpec[]{
                        FormFactory.LINE_GAP_ROWSPEC,
                        RowSpec.decode("16px"),
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,}
        ));

        JLabel lblDescriptionNumberOfBots = new JLabel("Number of Bots:");
        panel_agents.add(lblDescriptionNumberOfBots, "1, 2");

        JSlider sliderNumberOfBots = new JSlider();
        sliderNumberOfBots.setValue(0);
        sliderNumberOfBots.setMaximum(10);
        panel_agents.add(sliderNumberOfBots, "1, 4, 2, 1");

        JLabel lblValueNumberOfBots = new JLabel("0");
        panel_agents.add(lblValueNumberOfBots, "4, 4");

        JPanel panel_map = new JPanel();
        panel_map.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Map Configurations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_map.setBounds(25, 268, 390, 200);
        contentPane.add(panel_map);
        panel_map.setLayout(new FormLayout(new ColumnSpec[]{
                ColumnSpec.decode("143px:grow"),
                ColumnSpec.decode("104px"),},
                new RowSpec[]{
                        FormFactory.LINE_GAP_ROWSPEC,
                        RowSpec.decode("16px"),
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,}
        ));

        JLabel lblSelectMap = new JLabel("Select the map you would like to run:");
        panel_map.add(lblSelectMap, "1, 4");

        txtFieldSelectMap = new JTextField();
        panel_map.add(txtFieldSelectMap, "1, 6, fill, default");
        txtFieldSelectMap.setColumns(10);

        JButton btnBrowseMap = new JButton("Browse");
        panel_map.add(btnBrowseMap, "1, 8");

        JLabel lblOr = new JLabel("or");
        panel_map.add(lblOr, "1, 10");

        JButton btnCreateNewMap = new JButton("Create a new Map");
        panel_map.add(btnCreateNewMap, "1, 12");
    }
}

