package nl.tudelft.bw4t.environmentstore.editor.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.tudelft.bw4t.map.BlockColor;

/**
 * The ColorSequenceEditor class contains the sequence that should be completed by the agents.
 *
 */
public class ColorSequenceEditor extends JComponent {
    
    /** Random generated serial version UID. */
    private static final long serialVersionUID = 2112401621332684899L;
    
    /** length of the sequence */
    private static final int DEFAULT_LENGTH = 10;
  
    private static final int COLOR_SIZE = 15;
    
    private static final int BORDER = 2;

    private int maxLength = DEFAULT_LENGTH;

    private List<BlockColor> sequence = new ArrayList<>(DEFAULT_LENGTH);

    private Set<ChangeListener> onChange = new HashSet<>();

    /**
     * Constructor sets up listeners and aperance.
     * calls setupListeners method and setupAperance method.
     */
    public ColorSequenceEditor() {
        setupListeners();
        this.setupAperance();
    }

    /**
     * Constructor sets a sequence with a maximum length.
     * @param maxLength is the maximum length of the sequence.
     */
    public ColorSequenceEditor(int maxLength) {
        setupListeners();
        this.setMaxLength(maxLength);
    }

    /**
     * Setup all the listeners for the ColorSequenceEditor.
     */
    private void setupListeners() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Character chr = Character.toUpperCase(e.getKeyChar());
                if (chr == 'D') {
                    return;
                }
                try {
                    int n = Integer.parseInt(chr.toString());
                    if (n > 0 && n <= BlockColor.getAvailableColors().size()) {
                        addColor(BlockColor.getAvailableColors().get(n - 1));
                    }
                } catch (NumberFormatException exc) {
                    addColor(chr);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
                    removeColor();
                    return;
                }
                if (e.getKeyCode() == KeyEvent.VK_TAB && e.getSource() instanceof Component) {
                    ((Component) e.getSource()).transferFocus();
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocus();
            }
        });
    }

    /**
     * Set the dimensions and border of the ColorSequenceEditor.
     */
    private void setupAperance() {
        this.setBackground(Color.WHITE);
        Dimension d = new Dimension(BORDER * 2 + COLOR_SIZE * maxLength, BORDER * 2 + COLOR_SIZE);
        setMinimumSize(d);
        setPreferredSize(d);
        this.setBorder(BorderFactory.createLoweredBevelBorder());
    }

    /**
     * 
     * @param cl
     */
    public void addChangeListener(ChangeListener cl) {
        onChange.add(cl);
    }

    /**
     * 
     * @param cl
     */
    public void removeChangeListener(ChangeListener cl) {
        onChange.remove(cl);
    }

    /**
     * Revalidate and Repaint on change.
     */
    private void notifyValueChange() {
        ChangeEvent evt = new ChangeEvent(this);
        for (ChangeListener l : onChange) {
            l.stateChanged(evt);
        }
        this.revalidate();
        this.repaint();
    }

    public int getMaxLength() {
        return maxLength;
    }

    public boolean isFull() {
        return getSequenceSize() >= getMaxLength();
    }

    /**
     * changes the current length to maxLength
     * @param maxLength the length of the sequence and updates the sequence
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        this.setupAperance();
    }

    public List<BlockColor> getSequence() {
        return sequence;
    }

    public int getSequenceSize() {
        return getSequence().size();
    }

    /**
     * If a sequence given as parameter, set the sequence to this given sequence.
     * Else set it to clear.
     * @param sequence is the sequence the current sequence is set to.
     */
    public void setSequence(List<BlockColor> sequence) {
        if (sequence == null) {
            this.sequence.clear();
        } else {
            this.sequence = new ArrayList<BlockColor>(sequence);
        }
        notifyValueChange();
    }

    /**
     * add the color to the sequence list
     * @param chr the color to be added as character
     */
    public void addColor(Character chr) {
        BlockColor color;
        try {
            color = BlockColor.toAvailableColor(chr);
        } catch (IllegalArgumentException iae) {
            return;
        }
        addColor(color);
    }

    /**
     * add the color to the sequence list
     * @param c the BlockColor to be added
     */
    public void addColor(BlockColor c) {
        if (isFull() || c == null) {
            return;
        }
        sequence.add(c);
        notifyValueChange();
    }

    public void removeColor() {
        if (getSequenceSize() <= 0) {
            return;
        }
        sequence.remove(sequence.size() - 1);
        notifyValueChange();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.clearRect(BORDER, BORDER, getWidth() - BORDER * 2, getHeight() - BORDER * 2);
        Dimension s = this.getSize();
        int width = Math.max(1, (s.width - BORDER * 2) / maxLength);
        int height = s.height - BORDER * 2;

        int startX = BORDER;
        for (BlockColor c : getSequence()) {
            g.setColor(c.getColor());
            g.fillRect(startX, BORDER, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(startX, BORDER, width - 1, height - 1);
            startX += width;
        }
    }
}
