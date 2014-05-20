package nl.tudelft.bw4t.scenariogui;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created on 15-05-2014
 */
public class ScenarioEditorTest {

    ScenarioEditor editor;
    ScenarioEditor spyEditor;

    @Test
    public void checkActivePane() {
        editor = new ScenarioEditor();

        MainPanel panel = new MainPanel(new ConfigurationPanel(), new EntityPanel());

        editor.setActivePane(panel);
        assertEquals(panel, editor.getActivePane());
    }
    
   /* @Test
    public void testHandleException(){
    	editor = new ScenarioEditor();
    	spyEditor = spy(editor);
    	FileNotFoundException e = new FileNotFoundException();
    	String s = "test";
   	 	Mockito.doNothing().when(spyEditor).showDialog(any(FileNotFoundException.class), anyString());
    	
    	spyEditor.handleException(e, s);
    	

    	verify(spyEditor, times(1)).showDialog(e, s);
    	
    }*/


}
