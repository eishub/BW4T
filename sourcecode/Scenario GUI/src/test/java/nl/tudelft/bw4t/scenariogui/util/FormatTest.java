package nl.tudelft.bw4t.scenariogui.util;

import static org.junit.Assert.assertEquals;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import org.junit.Test;

/**
 * Created by on 15-5-2014.
 */
public class FormatTest {

    String numbersValid = "1234567";
    String numbersInvalid = "123caramba456";
    String numbersCorrected = "123456";

    @Test
    public void testFormatCorrect() {
        JTextField field = new JTextField();
        Format.addIntegerDocumentFilterForTextField(field);
        field.setText(numbersValid);
        assertEquals(numbersValid, field.getText());
    }

    @Test
    public void testFormatIncorrect() {
        JTextField field = new JTextField();
        Format.addIntegerDocumentFilterForTextField(field);
        field.setText(numbersInvalid);
        assertEquals(numbersCorrected, field.getText());
    }

    @Test
    public void testInsertStringFormatCorrect()throws BadLocationException {
        JTextField field = new JTextField();
        Format.addIntegerDocumentFilterForTextField(field);
        field.getDocument().insertString(0, numbersValid, null);
        assertEquals(numbersValid, field.getText());
    }

    @Test
    public void testInsertStringFormatIncorrect()throws BadLocationException {
        JTextField field = new JTextField();
        Format.addIntegerDocumentFilterForTextField(field);
        field.getDocument().insertString(0, numbersInvalid, null);
        assertEquals(numbersCorrected, field.getText());
    }


}
