package nl.tudelft.bw4t.scenariogui.util;

import static org.junit.Assert.assertEquals;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import org.junit.Test;

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

    @Test
    public final void testFormatCorrect() {
        JTextField field = new JTextField();
        Format.addIntegerDocumentFilterForTextField(field);
        field.setText(numbersValid);
        assertEquals(numbersValid, field.getText());
    }

    @Test
    public final void testFormatIncorrect() {
        JTextField field = new JTextField();
        Format.addIntegerDocumentFilterForTextField(field);
        field.setText(numbersInvalid);
        assertEquals(numbersCorrected, field.getText());
    }

    @Test
    public final void testInsertStringFormatCorrect()
            throws BadLocationException {
        JTextField field = new JTextField();
        Format.addIntegerDocumentFilterForTextField(field);
        field.getDocument().insertString(0, numbersValid, null);
        assertEquals(numbersValid, field.getText());
    }

    @Test
    public final void testInsertStringFormatIncorrect()
            throws BadLocationException {
        JTextField field = new JTextField();
        Format.addIntegerDocumentFilterForTextField(field);
        field.getDocument().insertString(0, numbersInvalid, null);
        assertEquals(numbersCorrected, field.getText());
    }

    @Test
    public final void testGetNonNegativeIntValueNegative() {
        assertEquals(1, Format.getNonNegativeIntValue(negativeInteger));
    }

    @Test
    public final void testGetNonNegativeIntValuePositive() {
        assertEquals(1, Format.getNonNegativeIntValue(nonNegativeInteger));
    }

    public final void testGetNonNegativeIntValueNull() {
        assertEquals(0, Format.getNonNegativeIntValue(null));
    }

    @Test
    public final void testNegativeGetIntValueNoNegative() {
        assertEquals(1, Format.getIntValue(negativeInteger, false));
    }

    @Test
    public final void testNegativeGetIntValueNegative() {
        assertEquals(-1, Format.getIntValue(negativeInteger, true));
    }

    @Test
    public final void testPositiveGetIntValueNoNegative() {
        assertEquals(1, Format.getIntValue(nonNegativeInteger, false));
    }

    @Test
    public final void testPositiveGetIntValueNegative() {
        assertEquals(1, Format.getIntValue(nonNegativeInteger, true));
    }

    @Test
    public final void testGetIntValueNULLNegative() {
        assertEquals(0, Format.getIntValue(null, true));
    }

    @Test
    public final void testGetIntValueNULLNoNegative() {
        assertEquals(0, Format.getIntValue(null, false));
    }

    @Test
    public final void testGetIntValueToLongLong() {
        assertEquals(0, Format.getIntValue("1000000000000000000", false));
    }

    @Test
    public final void testGetIntValueToLongInt() {
        assertEquals(0, Format.getIntValue("10000000000", false));
    }

    @Test
    public final void testGetIntValueToSmallInt() {
        assertEquals(0, Format.getIntValue("-10000000000", false));
    }

    public final void testGetIntValueToSmallIntNegative() {
        assertEquals(0, Format.getIntValue("-10000000000", true));
    }

    @Test
    public final void testPadStringEmpty() {
        assertEquals("0000", Format.padString("", 4));
    }

    @Test
    public final void testPadStringShort() {
        assertEquals("3000", Format.padString("3", 4));
    }

    @Test
    public final void testPadStringLong() {
        assertEquals("3000000", Format.padString("3000000", 4));
    }

    @Test
    public final void testPadStringEqual() {
        assertEquals("3000", Format.padString("3000", 4));
    }

}
