package nl.tudelft.bw4t.scenariogui.util;

import java.awt.event.WindowEvent;
import java.util.concurrent.Callable;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;

/**
 * A class with utilities to be used in the test suite.
 * 
 * @author Nick
 *
 */
public final class TestUtils {
    
    /**
     * Waits until a certain event is true before closing the GUI.
     * @param editor The GUI.
     * @param condition The event that has to be true.
     */
    public static void prepareGUIClose(final ScenarioEditor editor, final Callable<Boolean> condition) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (!condition.call()) {
                        //keep waiting
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                //Closes the GUI (also makes sure yes is pressed if asked if we're sure about closing):
                ScenarioEditor.setOptionPrompt(new YesMockOptionPrompt());
                editor.dispatchEvent(new WindowEvent(editor, WindowEvent.WINDOW_CLOSING));
                
            }
            
        }).start();
    }

}
