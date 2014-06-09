package nl.tudelft.bw4t.map.editor.gui;

import javax.swing.JTextField;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.editor.MapEditor;
import nl.tudelft.bw4t.map.editor.model.ColorSequence;

/**
 * This class provides user a GUI where he can type letters. Each letter stands
 * for a block of some color. See also {@link BlockColor}
 */
public class ColorSequenceEditor extends JTextField {

	private static final long serialVersionUID = -6725233034128456897L;

	/**
     * The colors are stored in the text of the text field.
     * 
     * @param cols
     *            is initial sequence.
     */
    public ColorSequenceEditor(ColorSequence cols) {
        if (cols == null) {
            throw new NullPointerException("Given color list is null");
        }
        setText(cols.getLetters());
    }

    /**
     * Convert the typed characters to a {@link ColorSequence}. shows warning if
     * too many or unknown characters in the textbox
     * 
     * @return {@link ColorSequence}
     */
    public ColorSequence getColors() {
        String letters = getText();
        if (letters.length() > 10) {
            MapEditor.showDialog("More than 10 letters, ignoring them");
            letters = letters.substring(0, 9);
        }

        ColorSequence colors = new ColorSequence();
        for (Character letter : letters.toCharArray()) {
            try {
                colors.add(BlockColor.toAvailableColor(letter));
            } catch (IllegalArgumentException e) {
                MapEditor.showDialog(e, e.getMessage()); // and continue the loop.
            }
        }
        return colors;
    }
    
    

}