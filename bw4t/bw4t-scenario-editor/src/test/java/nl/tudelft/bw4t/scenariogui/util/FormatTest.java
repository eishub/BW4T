package nl.tudelft.bw4t.scenariogui.util;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by on 15-5-2014.
 */
public class FormatTest {

    private String numbersValid = "1234567";

    private String numbersInvalid = "123caramba456";
    
    //The correction of the 'number' above after going through the filter.
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
    public final void testGetNonNegativeIntValueNegative() {
    	assertEquals(1, Format.getNonNegativeIntValue(negativeInteger));
    }
    
    /**
     * Tests getNonNegativeIntValue() method by feeding it a positive
     * string
     */
    @Test
    public final void testGetNonNegativeIntValuePositive() {
    	assertEquals(1, Format.getNonNegativeIntValue(nonNegativeInteger));
    }
    
    /**
     * Tests getNonNegativeIntValue() method by feeding it NULL
     */
    @Test
    public final void testGetNonNegativeIntValueNull() {
    	assertEquals(0, Format.getNonNegativeIntValue(null));
    }

    /**
     * Tests getIntValue() by feeding it a negative String
     * without allowing negative Integers
     */
    @Test
    public final void testNegativeGetIntValueNoNegative() {
    	assertEquals(1, Format.getIntValue(negativeInteger, false));
    }

    /**
     * Tests getIntValue() by feeding it a negative String
     * and allowing negative Integers
     */
    @Test
    public final void testNegativeGetIntValueNegative() {
    	assertEquals(-1, Format.getIntValue(negativeInteger, true));
    }

    /**
     * Tests getIntValue() by feeding it a positive String
     * without allowing negative Integers
     */
    @Test
    public final void testPositiveGetIntValueNoNegative() {
    	assertEquals(1, Format.getIntValue(nonNegativeInteger, false));
    }

    /**
     * Tests getIntValue() by feeding it a positive String
     * and allowing negative Integers
     */
    @Test
    public final void testPositiveGetIntValueNegative() {
    	assertEquals(1, Format.getIntValue(nonNegativeInteger, true));
    }
    
    /**
     * Tests getIntValue() by feeding it NULL as string
     * and allowing negative Integers
     */
    @Test
    public final void testGetIntValueNULLNegative() {
    	assertEquals(0, Format.getIntValue(null, true));
    }
    
    /**
     * Tests getIntValue() by feeding it NULL as string
     * and not allowing negative Integers
     */
    @Test
    public final void testGetIntValueNULLNoNegative() {
    	assertEquals(0, Format.getIntValue(null, false));
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
     * Tests padString() by feeding it an empty String.
     */
    @Test
    public final void testPadStringEmpty() {
    	assertEquals("0000", Format.padString("", 4));
    }
    
    /**
     * Tests padString() by feeding it a short String.
     */
    @Test
    public final void testPadStringShort() {
    	assertEquals("3000", Format.padString("3", 4));
    }
    
    /**
     * Tests padString() by feeding it a long String.
     */
    @Test
    public final void testPadStringLong() {
    	assertEquals("3000000", Format.padString("3000000", 4));
    }
    
    /**
     * Tests padString() by feeding it a String with
     * its length equal to the amount.
     */
    @Test
    public final void testPadStringEqual() {
    	assertEquals("3000", Format.padString("3000", 4));
    }

}
