package nl.tudelft.bw4t.scenariogui.util;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by on 15-5-2014.
 */
public class FormatTest {

    /** A valid number for an integer only text field. */
    private String numbersValid = "1234567";
    /** An invalid 'number' for an integer only text field. */
    private String numbersInvalid = "123caramba456";
    /** The correction of the 'number' above after going through the filter. */
    private String numbersCorrected = "123456";

    private String negativeInteger = "-1";
    private String nonNegativeInteger = "1";

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

    /**
     * Tests getNonNegativeIntValue() method by feeding it a negative
     * string
     */
    @Test
    public final void testGetNonNegativeIntValue() {
    	assertEquals(0, Format.getNonNegativeIntValue(negativeInteger));
    }

    /**
     * Tests getIntValue() by feeding it NULL as string
     */
    @Test
    public final void testGetIntValueNULL() {
    	assertEquals(0, Format.getIntValue(null, true));
    }

    /**
     * Tests getIntValue() by feeding it a to long Long
     */
    @Test
    public final void testGetIntValueToLongLong() {
    	assertEquals(0, Format.getIntValue("1000000000000000000", false));
    }

    /**
     * Tests getIntValue() by feeding it a to long Integer
     * Returns zero since the long cant be converted to an int.
     */
    @Test
    public final void testGetIntValueToLongInt() {
    	assertEquals(0, Format.getIntValue("10000000000", false));
    }

    /**
     * Tests getIntValue() by feeding it NULL to long negative Integer
     * without allowing negative Integers
     */
    @Test
    public final void testGetIntValueToSmallInt() {
    	assertEquals(0, Format.getIntValue("-10000000000", false));
    }

    /**
     * Tests getIntValue() by feeding it NULL to long negative Integer
     * and allowing negative Integers
     */
    @Test
    public final void testGetIntValueToSmallIntNegative() {
    	assertEquals(0, Format.getIntValue("-10000000000", true));
    }

    /**
     * Tests getIntValue() by feeding it a ligit Integer
     * without allowing negative Integers
     */
    @Test
    public final void testGetIntValueNormal() {
    	assertEquals(1, Format.getIntValue(nonNegativeInteger, false));
    }

    /**
     * Tests getIntValue() by feeding it a ligit Integer
     * and allowing negative Integers
     */
    @Test
    public final void testGetIntValueNormalNegative() {
    	assertEquals(1, Format.getIntValue(nonNegativeInteger, true));
    }

}
