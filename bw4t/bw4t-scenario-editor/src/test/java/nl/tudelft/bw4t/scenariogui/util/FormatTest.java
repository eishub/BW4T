package nl.tudelft.bw4t.scenariogui.util;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * @author        
 * @version     0.1                
 * @since       15-05-2014        
 */
public class FormatTest {

    /** A valid number for an integer only text field. */
    private String numbersValid = "1234567";
    /** An invalid 'number' for an integer only text field. */
    private String numbersInvalid = "123caramba456";
    /** The correction of the 'number' above after going through the filter. */
    private String numbersCorrected = "123456";

    /**
     * Tests whether a correct format is left unchanged.
     */
    @Test
    public final void testFormatCorrect() {
        JTextField field = new JTextField();
        Format.addIntegerDocumentFilterForTextField(field);
        field.setText(numbersValid);
        assertEquals(numbersValid, field.getText());
    }

    /**
     * Tests whether the filter corrects the incorrect number.
     */
    @Test
    public final void testFormatIncorrect() {
        JTextField field = new JTextField();
        Format.addIntegerDocumentFilterForTextField(field);
        field.setText(numbersInvalid);
        assertEquals(numbersCorrected, field.getText());
    }

    /**
     * Tests whether the filter can be used with the getDocument() method.
     * @throws BadLocationException When the text for the field could not
     * be inserted.
     */
    @Test
    public final void testInsertStringFormatCorrect()
            throws BadLocationException {
        JTextField field = new JTextField();
        Format.addIntegerDocumentFilterForTextField(field);
        field.getDocument().insertString(0, numbersValid, null);
        assertEquals(numbersValid, field.getText());
    }

    /**
     * Tests the filter with the getDocument() method for an incorrect
     * number.
     * @throws BadLocationException When the number inserted cannot
     * be inserted.
     */
    @Test
    public final void testInsertStringFormatIncorrect()
            throws BadLocationException {
        JTextField field = new JTextField();
        Format.addIntegerDocumentFilterForTextField(field);
        field.getDocument().insertString(0, numbersInvalid, null);
        assertEquals(numbersCorrected, field.getText());
    }

}
