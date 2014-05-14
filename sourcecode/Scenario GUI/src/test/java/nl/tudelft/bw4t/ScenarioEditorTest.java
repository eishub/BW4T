package nl.tudelft.bw4t;

import org.junit.Test;
import javax.swing.*;
import static org.junit.Assert.assertEquals;

/**
 * Created on 15-05-2014
 */
public class ScenarioEditorTest {

    ScenarioEditor editor;

    @Test
    public void checkActivePane() {
        editor = new ScenarioEditor();

        JPanel panel = new JPanel();

        editor.setActivePane(panel);
        assertEquals(panel, editor.getActivePane());
    }


}
