package nl.tudelft.bw4t.scenariogui.botstore.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
/**
 * Handles actions of the battery checkbox.
 */
public class BatteryBox implements ActionListener {
    /**
     * The BotEditorPanel to get components from.
     */
    private BotEditorPanel view;
    /**
     * Constructor.
     * @param pview The BotEditorPanel containing the source
     * of the actions.
     */
    public BatteryBox(BotEditorPanel pview) {
        this.view = pview;
    }
    /**
     * Performs the necessary action of the battery-enabling checkbox.
     * @param e The action event caused by checking or unchecking
     * the checkbox.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    	view.setBatterySliderEnabled(view.isBatteryEnabled());
    	BotController currentController = view.getBotController();
    	if (view.isBatteryEnabled()) {
    		currentController.setNewBatteryValue(view);
    		System.out.println("" + currentController.getBotBatteryDischargeRate());
    	} 
    }
}
