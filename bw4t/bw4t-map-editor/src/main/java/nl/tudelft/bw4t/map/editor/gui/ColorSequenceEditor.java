package nl.tudelft.bw4t.map.editor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.powermock.tests.utils.Keys;

import nl.tudelft.bw4t.map.BlockColor;

public class ColorSequenceEditor extends JComponent {
    private static final int DEFAULT_LENGTH = 10;
    private static final long serialVersionUID = 2112401621332684899L;
    private static final int COLOR_SIZE = 15;
    private static final int BORDER = 2;

    private int maxLength = DEFAULT_LENGTH;

    private List<BlockColor> sequence = new ArrayList<>(DEFAULT_LENGTH);

    private Set<ChangeListener> onChange = new HashSet<>();

    public ColorSequenceEditor() {
        setupListeners();
        this.setupAperance();
    }

    public ColorSequenceEditor(int maxLength) {
        setupListeners();
        this.setMaxLength(maxLength);
    }

    private void setupListeners() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Character chr = Character.toUpperCase(e.getKeyChar());
                if (chr == 'D') {
                    return;
                }
                addColor(chr);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
                    removeColor();
                    return;
                }
            }
        });
    }

    private void setupAperance() {
        Dimension d = new Dimension(BORDER * 2 + COLOR_SIZE * maxLength, BORDER * 2 + COLOR_SIZE);
        setMinimumSize(d);
        setPreferredSize(d);
        this.setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public void addChangeListener(ChangeListener cl) {
        onChange.add(cl);
    }

    public void removeChangeListener(ChangeListener cl) {
        onChange.remove(cl);
    }

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

    public void setSequence(List<BlockColor> sequence) {
        if (sequence == null) {
            this.sequence.clear();
        }
        else {
            this.sequence = sequence;
        }
    }

    public void addColor(Character chr) {
        BlockColor color;
        try {
            color = BlockColor.toAvailableColor(chr);
        } catch (IllegalArgumentException iae) {
            return;
        }
        addColor(color);
    }

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
        super.paintComponent(g);
        Dimension s = this.getSize();
        int width = Math.max(1, (s.width - BORDER * 2) / maxLength);
        int height = s.height - BORDER * 2;

        int startX = BORDER;
        for (BlockColor c : getSequence()) {
            g.setColor(c.getColor());
            g.fillRect(startX, BORDER, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(startX, BORDER, width-1, height-1);
            startX += width;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TestFrame");
        frame.add(new ColorSequenceEditor());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
