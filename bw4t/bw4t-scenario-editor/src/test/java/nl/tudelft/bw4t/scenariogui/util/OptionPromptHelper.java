package nl.tudelft.bw4t.scenariogui.util;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * The OptionPrompt that always returns no, used when mocking the
 * objects during tests, to prevent the system from hanging due
 * to having to press a button on the prompt.
 */
public final class OptionPromptHelper {
    
    private OptionPromptHelper() {
    }

    public static OptionPrompt getYesOptionPrompt() {
        OptionPrompt yesPrompt = mock(OptionPrompt.class);
        when(yesPrompt.showConfirmDialog(any(Component.class), any(), anyString(), anyInt(), anyInt())).thenReturn(JOptionPane.YES_OPTION);
        return yesPrompt;
    }

    public static OptionPrompt getNoOptionPrompt() {
        OptionPrompt noPrompt = mock(OptionPrompt.class);
        when(noPrompt.showConfirmDialog(any(Component.class), any(), anyString(), anyInt(), anyInt())).thenReturn(JOptionPane.NO_OPTION);
        return noPrompt;
    }
}
