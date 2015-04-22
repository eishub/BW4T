package nl.tudelft.bw4t.scenariogui.util;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * A class for the formatting of data.
 */
public final class Format {

    private static DocumentFilter intDocumentFilter = new IntegerDocumentFilter();

    /** Prevents this class from being instantiated. */
    private Format() {
    }

    /**
     * The class for filtering a document so it only has integers.
     */
    private static class IntegerDocumentFilter extends DocumentFilter {

        private static final String DIGIT_REGEX = "\\D++";
        
        @Override
        public void insertString(final FilterBypass fb, final int off,
                final String str,
                final AttributeSet attr) throws BadLocationException {
            // remove non-digits
            fb.insertString(off, str.replaceAll(DIGIT_REGEX, ""), attr);
        }

        @Override
        public void replace(final FilterBypass fb, final int off, final int len,
                final String str,
                final AttributeSet attr) throws BadLocationException {
            // remove non-digits
            fb.replace(off, len, str.replaceAll(DIGIT_REGEX, ""), attr);
        }
    }

    /**
     * Adds a filter to the text field that only allows numerical input.
     *
     * @param txt The text field.
     */
    public static void addIntegerDocumentFilterForTextField(
            final JTextField txt) {
        ((AbstractDocument) txt.getDocument())
                .setDocumentFilter(intDocumentFilter);
    }

    /**
     * Returns the non-negative integer value of a string.
     * @param intRepresentation The string.
     * @return The integer value of the string.
     */
    public static int getNonNegativeIntValue(String intRepresentation) {
        return getIntValue(intRepresentation, false);
    }
    
    /**
     * Gets the integer value of a string.
     * Returns zero if the string can not be converted to an Integer.
     * @param intRepresentation The string.
     * @param canBeNegative Decides whether the integer can be negative.
     * @return The integer value of the string.
     */
    public static int getIntValue(String intRepresentation, boolean canBeNegative) {
        int amount;
        try {
            amount = Integer.parseInt(intRepresentation);
        } catch (NumberFormatException ex) {
            amount = 0;
        }
        if(!canBeNegative) {
            amount = Math.abs(amount);
        }
        return amount;
    }
    
    /**
     * Pad the string with zeroes.
     * @param value The string to be padded.
     * @param amount The amount of zeroes to add.
     * @return The padded string.
     */
    public static String padString(String value, int amount) {
        StringBuffer sb = new StringBuffer(value);
        while (sb.length() < amount) {
            sb.append(0);
        }
        value = sb.toString();
        return value;
    }
    
}
