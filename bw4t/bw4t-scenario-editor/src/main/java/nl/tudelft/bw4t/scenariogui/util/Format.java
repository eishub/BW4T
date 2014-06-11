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
    
    private static DocumentFilter intDocumentFilter =
            new IntegerDocumentFilter();

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

        /**
         * Deletes the region of text from <code>off</code> to
         * <code>off + len</code>, and replaces it with <code>str</code>.
         *
         * @param fb
         *            FilterBypass to circumvent calling back into the Document
         *            to change it.
         * @param off
         *            Offset, location in document.
         * @param len
         *            Length of text to delete.
         * @param str
         *            Text to insert, null indicates no text to insert.
         * @param attr
         *            AttributeSet indicating attributes of inserted text, null
         *            is legal.
         * @throws BadLocationException
         *             Reports bad locations within a document model.
         */
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
     * @param txt
     *            The text field.
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
     * @param canBeNegative TODO
     * @return The integer value of the string.
     */
    public static int getIntValue(String intRepresentation, boolean canBeNegative) {
        int amount;
        try {
            amount = Integer.parseInt(intRepresentation);
        } catch (NumberFormatException ex) {
            amount = 0;
        }
        if(amount < 0 && !canBeNegative) {
            amount = Math.abs(amount);
        }
        return amount;
    }
    
    /**
     * Pad the string with zeros.
     * @param value The string to be padded.
     * @param amount The amount of zeroes to add.
     * @return The padded string.
     */
    public static String padString(String value, int amount) {
        while (value.length() < amount) {
            value += 0;
        }
        return value;
    }
    
}
