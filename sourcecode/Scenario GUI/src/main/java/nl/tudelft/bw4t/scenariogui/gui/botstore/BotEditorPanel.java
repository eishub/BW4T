package nl.tudelft.bw4t.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


/**
 * BotEditorPanel which serves as the content pane for the BotEditor frame
 * @author Arun
 */
public class BotEditorPanel extends JPanel {
	
	private JPanel botCheckables = new JPanel();
	private JPanel botSliders = new JPanel();
	
	private JComboBox botTypeSelector = new JComboBox();
	
	private JButton applyButton = new JButton("Apply");
	private JButton resetButton = new JButton("Reset");
	private JButton cancelButton = new JButton("Cancel");
	
	private JCheckBox gripperCheckbox = new JCheckBox("Gripper enabled");
	private JCheckBox colorblindCheckbox = new JCheckBox("Color blindness enabled");
	private JCheckBox walkingCheckbox = new JCheckBox("Walking enabled");
	private JCheckBox jumpingCheckbox = new JCheckBox("Jumping enabled");
	
	private JSlider sizeSlider = new JSlider();
	private JSlider speedSlider = new JSlider();
	private JSlider batterySlider = new JSlider();

	
	public BotEditorPanel(){
		setLayout(new BorderLayout(20,20));		
		
		createBotCheckablesPanel();
		createBotSlidersPanel();
		
		add(botSliders, BorderLayout.WEST);
		add(botCheckables, BorderLayout.EAST);
		
	}
	
	public void createBotCheckablesPanel(){
		botCheckables.setLayout(new GridLayout(5,1));
		JLabel checkablesLabel = new JLabel("Checkables");
		JLabel batteryUseLabel = new JLabel("Average Battery use:");
		JLabel batteryUseValueLabel = new JLabel("0.9");
		JLabel perTickLabel = new JLabel("per tick");
		
		checkablesLabel.setFont(new Font("Tahoma",Font.PLAIN,24));
		batteryUseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel buttonPanel = new JPanel();
		JPanel batteryCapPanel = new JPanel();
		JPanel checkablesPanel = new JPanel();
		
		batteryCapPanel.add(batteryUseValueLabel);
		batteryCapPanel.add(perTickLabel);
		
		buttonPanel.add(applyButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(cancelButton);
		
		botCheckables.add(checkablesLabel);
		checkablesPanel.setLayout(new BoxLayout(checkablesPanel,BoxLayout.PAGE_AXIS));
		checkablesPanel.add(gripperCheckbox);
		checkablesPanel.add(colorblindCheckbox);
		checkablesPanel.add(walkingCheckbox);
		checkablesPanel.add(jumpingCheckbox);
		botCheckables.add(checkablesPanel);
		botCheckables.add(batteryUseLabel);
		botCheckables.add(batteryCapPanel);
		botCheckables.add(buttonPanel);
		
	}
	public void createBotSlidersPanel(){
		botSliders.setLayout(new GridLayout(8,1));
		JLabel botTitleLabel = new JLabel("Bot X");
		botTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		botTitleLabel.setFont(new java.awt.Font("Tahoma", 0, 36));
		
		botTypeSelector.setModel(new DefaultComboBoxModel(new String[]{"Agent", "Human"}));

		JLabel sizeLabel = new JLabel("Size (2 is default)");
		sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel speedLabel = new JLabel("Bot speed");
		speedLabel.setToolTipText("This speed is relatief to the bots. The default is 100%");
		speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel batteryCapacity = new JLabel("Battery Capacity(0 is infinite)");
		batteryCapacity.setHorizontalAlignment(SwingConstants.CENTER);
		
		createSliders();
		
		botSliders.add(botTitleLabel);
		botSliders.add(botTypeSelector);
		botSliders.add(sizeLabel);
		botSliders.add(sizeSlider);
		botSliders.add(speedLabel);
		botSliders.add(speedSlider);
		botSliders.add(batteryCapacity);
		botSliders.add(batterySlider);
	}
	
	public void createSliders(){
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setMaximum(5);
		sizeSlider.setMinimum(1);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		sizeSlider.setSnapToTicks(true);
		sizeSlider.setValue(2);
		sizeSlider.setValueIsAdjusting(true);
		
		speedSlider.setMajorTickSpacing(25);
		speedSlider.setMaximum(200);
		speedSlider.setMinimum(0);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setValue(100);
        speedSlider.setValueIsAdjusting(true);
        
        batterySlider = new JSlider();
        batterySlider.setValue(0);
        batterySlider.setSnapToTicks(true);
        batterySlider.setPaintTicks(true);
        batterySlider.setPaintLabels(true);
        batterySlider.setMajorTickSpacing(10);
        
	}

}
