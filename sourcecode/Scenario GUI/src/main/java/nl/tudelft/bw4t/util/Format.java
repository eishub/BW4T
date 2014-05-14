package nl.tudelft.bw4t.util;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * A class for the formatting of data.
 * 
 * @author Nick
 *
 */
public class Format {
	
	private static DocumentFilter intDocumentFilter = new IntegerDocumentFilter();
	
	private static class IntegerDocumentFilter extends DocumentFilter {
		
		@Override
		public void insertString(FilterBypass fb, int off, String str, AttributeSet attr) throws BadLocationException {
		    // remove non-digits
		    fb.insertString(off, str.replaceAll("\\D++", ""), attr);
		}
		
		@Override
		public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr) throws BadLocationException {
		    // remove non-digits
		    fb.replace(off, len, str.replaceAll("\\D++", ""), attr);
		}
		
	}
	
	/**
	 * Adds a filter to the text field that only allows numerical input.
	 * @param txt The text field.
	 */
	public static void addIntegerDocumentFilterForTextField(JTextField txt) {
		((AbstractDocument) txt.getDocument()).setDocumentFilter(intDocumentFilter);
	}

}
