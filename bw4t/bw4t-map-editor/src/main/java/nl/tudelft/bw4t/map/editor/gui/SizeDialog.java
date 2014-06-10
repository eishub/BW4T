package nl.tudelft.bw4t.map.editor.gui;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * The SizeDialog show the user a dialog where he can enter size of the map.
 */
public class SizeDialog extends JPanel {

	private static final long serialVersionUID = -5021732242993235726L;
	
	SpinnerModel rowmodel = new SpinnerNumberModel(3, // initial value
            1, // min
            24, // max
            1); // step
    JSpinner rows = new JSpinner(rowmodel);

    SpinnerModel colmodel = new SpinnerNumberModel(3, // initial value
            1, // min
            24, // max
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
        add(new JLabel("#Rows"));
        add(rows);
        add(new JLabel("#Columns"));
        add(cols);
        add(new JLabel("#Entities"));
        add(entities);
        add(new JLabel("Generate random sequence and blocks"));
        add(randomcheckbox);
        add(new JLabel("Show zone labels"));
        add(labelcheckbox);
        
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