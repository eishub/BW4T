package nl.tudelft.bw4t.map.editor;

import javax.swing.JTextField;

import nl.tudelft.bw4t.map.BlockColor;

/**
 * This class provides user a GUI where he can type letters. Each letter stands
 * for a block of some color. See also {@link BlockColor}
 * 
 * @author W.Pasman 12sep12
 * 
 */
@SuppressWarnings("serial")
public class ColorSequenceEditor extends JTextField {

    /**
     * The colors are stored in the text of the text field.
     * 
     * @param cols
     *            is initial sequence.
     */
    public ColorSequenceEditor(ColorSequence cols) {
        if (cols == null) {
            throw new NullPointerException("given color list is null");
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
            AlertBox.alert("more than 10 letters, ignoring them");
            letters = letters.substring(0, 9);
        }

        ColorSequence colors = new ColorSequence();
        for (Character letter : letters.toCharArray()) {
            try {
                colors.add(BlockColor.toAvailableColor(letter));
            } catch (IllegalArgumentException e) {
                AlertBox.alert(e.getMessage()); // and continue the loop.
            }
        }
        return colors;
    }

}