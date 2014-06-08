package nl.tudelft.bw4t.map.editor.gui;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * shows the user a dialog where he can enter size of the map
 */
@SuppressWarnings("serial")
public class SizeDialog extends JPanel {
    SpinnerModel rowmodel = new SpinnerNumberModel(3, // initial value
            1, // min
            10, // max
            1); // step
    JSpinner rows = new JSpinner(rowmodel);

    SpinnerModel colmodel = new SpinnerNumberModel(3, // initial value
            1, // min
            10, // max
            1); // step
    JSpinner cols = new JSpinner(colmodel);

    SpinnerModel entitymodel = new SpinnerNumberModel(2, // initial value
            1, // min
            11, // max
            1); // step
    JSpinner entities = new JSpinner(entitymodel);

    JCheckBox randomcheckbox = new JCheckBox();
    JCheckBox labelcheckbox = new JCheckBox("", true);

    public SizeDialog() {
        setLayout(new GridLayout(0, 2));
        add(new JLabel("#rows"));
        add(rows);
        add(new JLabel("#columns"));
        add(cols);
        add(new JLabel("#entities"));
        add(entities);
        add(new JLabel("generate random sequence and blocks"));
        add(randomcheckbox);
        add(new JLabel("show zone labels"));
        add(labelcheckbox);

        // pack();
        // setVisible(true);
    }

    public boolean isRandomMap() {
        return randomcheckbox.isSelected();
    }

    public boolean isLabelsVisible() {
        return labelcheckbox.isSelected();
    }

    /**
     * get {@link #rows} as set by user
     * 
     * @return {@link #rows}
     */
    public Integer getRows() {
        return (Integer) (rows.getValue());
    }

    /**
     * get {@link #cols} as set by user
     * 
     * @return {@link #cols}
     */
    public Integer getColumns() {
        return (Integer) (cols.getValue());
    }

    /**
     * get {@link #entities} as set by user
     * 
     * @return {@link #entities}
     */
    public Integer getEntities() {
        return (Integer) (entities.getValue());
    }
}