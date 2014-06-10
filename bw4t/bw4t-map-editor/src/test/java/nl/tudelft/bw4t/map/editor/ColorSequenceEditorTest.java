package nl.tudelft.bw4t.map.editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JTextField;

import nl.tudelft.bw4t.map.editor.controller.ColorSequenceController;
import nl.tudelft.bw4t.map.editor.gui.ColorSequenceEditor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ColorSequenceEditorTest {
    
    private JFrame frame;
    private ColorSequenceController csc;
    private ColorSequenceEditor cse1;
    private ColorSequenceEditor cse2;
    private ColorSequenceEditor cse3;
    
    @Before
    public void setup() {
        frame = new JFrame("ColorSequenceTest");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(4,1));
        
        frame.add(new JTextField());
        
        csc = new ColorSequenceController();
        cse1 = new ColorSequenceEditor();
        csc.addColorSequenceEditor(cse1);
        frame.add(cse1);
        cse2 = new ColorSequenceEditor();
        csc.addColorSequenceEditor(cse2);
        frame.add(cse2);
        cse3 = new ColorSequenceEditor();
        csc.addColorSequenceEditor(cse3);
        frame.add(cse3);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    @Test
    public void testRun() throws Exception {
        Thread.sleep(100000);
    }
}
