package nl.tudelft.bw4t.environmentstore.sizedialog.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import nl.tudelft.bw4t.environmentstore.sizedialog.controller.SizeDialogController;

/**
 * The SizeDialog shows the user a dialog where he can enter the size of the
 * map.
 */
public class SizeDialog extends JFrame {

    /** Random generated serial version UID */
    private static final long serialVersionUID = -3691483561210215655L;

    /**
     * The contentPane.
     */
    private JPanel contentPane = new JPanel();

    /**
     * With this button the user can choose to start building a map from
     * scratch.
     */
    private JButton startButton = new JButton("Start");

    /**
     * Label with the text #rows.
     */
    private JLabel rowsLabel = new JLabel("Number of Rows: ");

    /**
     * Label with the text #columns.
     */
    private JLabel columnsLabel = new JLabel("Number of Columns: ");

    /**
     * Here can the user set the wanted numbers of rows.
     */
    private JSpinner rows = new JSpinner();

    /**
     * Here can the user set the wanted number of columns.
     */
    private JSpinner cols = new JSpinner();

    /** The controller for the size dialog. */
    private SizeDialogController sdc;

    /** Creates the frame. */
    public SizeDialog() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentPane.setLayout(new GridBagLayout());
        setContentPane(contentPane);

        sdc = new SizeDialogController(this);

        // (initial value, min, max, step)
        SpinnerModel rowmodel = new SpinnerNumberModel(5, 5, 100, 1); 
        rows = new JSpinner(rowmodel);
        rows.setPreferredSize(new Dimension(100, 20));
        contentPane.add(rowsLabel,
                setUpContraints(GridBagConstraints.HORIZONTAL, 0, 0, 0));
        contentPane.add(rows,
                setUpContraints(GridBagConstraints.HORIZONTAL, 1, 0, 0));

        // (initial value, min, max, step)
        SpinnerModel colmodel = new SpinnerNumberModel(5, 3, 100, 1); 
        cols = new JSpinner(colmodel);
        cols.setPreferredSize(new Dimension(100, 20));
        contentPane.add(columnsLabel,
                setUpContraints(GridBagConstraints.HORIZONTAL, 0, 1, 0));
        contentPane.add(cols,
                setUpContraints(GridBagConstraints.HORIZONTAL, 1, 1, 0));

        contentPane.add(startButton,
                setUpContraints(GridBagConstraints.BASELINE, 0, 2, 2));

        setTitle("New Environment");
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        setResizable(false);

    }

    /**
     * This method set ups the certain constraints for the Grid Bag Layout.
     * @param constraint
     *                   positions
     * @param x
     *         the concerned column
     * @param y
     *         the concerned row
     * @param width
     *             for components that need to be bigger.
     * @return the constraints set up
     */
    private GridBagConstraints setUpContraints(int constraint, int x, int y,
            int width) {
        GridBagConstraints c = new GridBagConstraints();

        c.fill = constraint;
        c.gridx = x;
        c.gridy = y;
        c.insets = new Insets(3, 3, 3, 3);

        if (width != 0) {
            c.gridwidth = width;
        }

        return c;
    }

    public int getRows() {
        return (Integer) (rows.getValue());
    }

    public int getColumns() {
        return (Integer) (cols.getValue());
    }

    public JButton getStartButton() {
        return startButton;
    }

    public SizeDialogController getSizeDialogController() {
        return sdc;
    }

    public JSpinner getRowSpinner() {
        return rows;
    }

    public JSpinner getColumnsSpinner() {
        return cols;
    }
}
