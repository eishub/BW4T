package nl.tudelft.bw4t.environmentstore.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;

import nl.tudelft.bw4t.environmentstore.editor.controller.ColorSequenceController;
import nl.tudelft.bw4t.environmentstore.editor.controller.ColorSequenceEditor;
import nl.tudelft.bw4t.map.BlockColor;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ColorSequenceEditorTest {

    private static final int DELAY = 500;
    private JFrame frame;
    private ColorSequenceController csc;
    private ColorSequenceEditor cse1;
    private ColorSequenceEditor cse2;
    private ColorSequenceEditor cse3;
    private JTextField jTextField;
    private static Robot robot;

    @BeforeClass
    public static void setupGeneral() throws Exception {
        robot = new Robot();
    }

    @Before
    public void setup() throws Exception {
        frame = new JFrame("ColorSequenceTest");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));

        jTextField = new JTextField();
        frame.add(jTextField);

        csc = new ColorSequenceController();
        cse1 = new ColorSequenceEditor();
        csc.addColorSequenceEditor(cse1);
        frame.add(cse1);
        cse2 = new ColorSequenceEditor(4);
        csc.addColorSequenceEditor(cse2);
        frame.add(cse2);
        cse3 = new ColorSequenceEditor();
        csc.addColorSequenceEditor(cse3);
        frame.add(cse3);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    @After
    public void reset() {
        frame.dispose();
    }
    @Test
    public void testTabOrder() throws Exception {
        robot.delay(DELAY);
        assertTrue(jTextField.isFocusOwner());

        tapKey(KeyEvent.VK_TAB);
        assertTrue(cse1.isFocusOwner());

        tapKey(KeyEvent.VK_TAB);
        assertTrue(cse2.isFocusOwner());

        tapKey(KeyEvent.VK_TAB);
        assertTrue(cse3.isFocusOwner());
    }

    @Test
    public void testColorNumbers() {
        robot.delay(DELAY);
        cse1.requestFocus();
        robot.delay(DELAY);

        assertEquals(0, cse1.getSequenceSize());

        for (int i = 0; i < BlockColor.getAvailableColors().size(); i++) {
            tapKey(KeyEvent.VK_1 + i);
            assertEquals(i+1, cse1.getSequenceSize());
        }
        
        List<BlockColor> expected = BlockColor.getAvailableColors();
        List<BlockColor> actual = cse1.getSequence();
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i),actual.get(i));
        }
    }

    @Test
    public void testColorLetters() {
        robot.delay(DELAY);
        cse1.requestFocus();
        robot.delay(DELAY);

        assertEquals(0, cse1.getSequenceSize());
        
        List<BlockColor> expected = BlockColor.getAvailableColors();
        for (int i = 0; i < expected.size(); i++) {
            tapKey(expected.get(i).getLetter());
            assertEquals(i+1, cse1.getSequenceSize());
        }
        
        List<BlockColor> actual = cse1.getSequence();
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i),actual.get(i));
        }
        
        cse1.setSequence(null);

        assertEquals(0, cse1.getSequenceSize());
    }

    private void tapKey(int key) {
        robot.keyPress(key);
        robot.delay(DELAY);
        robot.keyRelease(key);
        robot.delay(DELAY);
    }
    
    public static void main(String[]args) throws Exception{
        ColorSequenceEditorTest test = new ColorSequenceEditorTest();
      
        test.setup();
        test.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
