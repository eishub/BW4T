package nl.tudelft.bw4t.scenariogui.botstore.gui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import nl.tudelft.bw4t.scenariogui.botstore.controller.BotController;

/**
 * This class serves as frame for the BotEditorPanel
 */
public class BotEditor extends JFrame {
    
    /**
     * Random generated serial version UID.
     */
    private static final long serialVersionUID = 8114982191029560097L;
    /** the name of the window */
    private String windowName = "Bot Editor";
    
    /**
     * creates the BotEditor frame
     * @param bc the BotController in control of this frame
     */
  
    public BotEditor(BotController bc) {
        setLookAndFeel();
        setTitle(windowName);
        setResizable(false);
        setLayout(null);
      
        BotEditorPanel bPanel = new BotEditorPanel(bc);
        bPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(bPanel);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
    }

     /**
     *  Function to set the look and feel of the frame to the default look and feel of the system.
     *  Throws exceptions which are passed over since the failure to set the look and feel is not
     *  considered harmful.
     */
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            // pass
        } catch (InstantiationException e) {
            // pass
        } catch (IllegalAccessException e) {
            // pass
        } catch (UnsupportedLookAndFeelException e) {
            // pass
        }
    }
}

