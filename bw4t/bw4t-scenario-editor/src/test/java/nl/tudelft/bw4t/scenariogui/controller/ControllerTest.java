package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.spy;

/**
 * Tests the controller class. It's near impossible to get the save button.
 *
 * @author Nick
 *
 */
public class ControllerTest {

    /** The file directory used for testing. */
    private static final String FILE_DIR = System.getProperty("user.dir")
            + "/src/test/resources/";
    /** The file path used for testing. */
    private static final String FILE_PATH = FILE_DIR + "SaveAsTest.xml";
    {
        File f = new File(FILE_PATH);
        if (f.exists()) {
            f.delete();
        }
    }

    /** The main GUI. */
    private ScenarioEditor mainGUI = new ScenarioEditor();

    /**
     * Tests the quick save after a file was saved before (and therefor a quick
     * save location is known).
     */
    @Test
    public final void testSaveAfterHavingSavedBefore() {
        // set the last file location, so the quick save is possible
        getMenu().setLastFileLocation(FILE_PATH); 
        // press save (open dialogue)
        getMenu().getMenuItemFileSave().doClick();
        // verify(getSave(), times(1)).saveFile(false);
        // check if method with proper parameters was called check if the file to be saved was saved
        checkFileCreated();
    }

    /**
     * Tests whether pressing save the first time is seen as a save as action,
     * and the second time as save. Also checks if the files are really created.
     */
    @Ignore
    public final void testSave() {
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                // confirm save as dialogue when it opens
                awaitAndPressConfirmOnSaveAsDialogue();
            }
            // press save (open dialogue)
            getMenu().getMenuItemFileSave().doClick();
            // verify(getSave(), times(1)).saveFile(i == 0);
            //check if method with proper parameters was called check if the file to be saved was saved
            checkFileCreated();
        }
    }

    /**
     * Tests whether pressing the save as menu item has the same behavior if its
     * pressed multiple times in a row.
     */
    @Ignore
    public final void testSaveAs() {
        for (int i = 0; i < 2; i++) {
            // confirm save as dialogue when it opens
            awaitAndPressConfirmOnSaveAsDialogue();
            // press save as (open dialogue)
            getMenu().getMenuItemFileSaveAs().doClick();
            // verify(getSaveAs(), times(1)).saveFile(true);
            //check if method with proper parameters was called checks if the file to be saved was saved
            checkFileCreated();
        }
    }

    // private JDialog getSaveAsDialog() {
    // for (ActionListener listener :
    // saveAs.currentFileChooser.getActionListeners()) {
    // if (listener instanceof JDialog) {
    // return (JDialog) listener;
    // }
    // }
    // return null;
    // }

    /**
     * Gets the listener that is listening to this menu item which is of the
     * specified type.
     *
     * @param menuItem
     *            The menu item.
     * @param listenerClass
     *            The specified type.
     * @return The listener of this type found.
     */
    private ActionListener getListener(final JMenuItem menuItem,
            final Class<?> listenerClass) {
        for (ActionListener listener : menuItem.getActionListeners()) {
            if (listenerClass.isInstance(listener)) {
                return (ActionListener) listenerClass.cast(listener);
            }
        }
        return null;
    }

    /**
     * Checks if the file was created and then deletes it. Fails otherwise.
     */
    private void checkFileCreated() {
        File f = new File(FILE_PATH);
        if (f.exists()) {
            f.delete();
        }
        else {
            fail("File was not created as it should have.");
        }
    }

    /**
     * Finds the opened JFileChooser and presses accept in order to store the
     * file in the resources folder.
     */
    private void awaitAndPressConfirmOnSaveAsDialogue() {
        new Thread(new Runnable() {

            public void run() {
                while (true) {
                    JFileChooser fc = ((MenuOptionSaveAs) getListener(getMenu()
                            .getMenuItemFileSaveAs(),
                            MenuOptionSaveAs.class)).getCurrentFileChooser();
                    if (fc != null) {
                        System.out.println("Not null! Executing..");
                        fc.setSelectedFile(new File(FILE_PATH));
                        fc.approveSelection();
                        System.out.println("..done!!");
                        break;
                    }
                    else {
                        System.out.println("Is still null.");
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
            }

        }).start();

        // FileChooserUI fUI = saveAs.currentFileChooser.getUI();
        // fUI.getDefaultButton(saveAs.currentFileChooser).doClick();

        // saveAs.currentFileChooser.setCurrentDirectory(new File(FILE_DIR));
        // saveAs.currentFileChooser.setSelectedFile(new File(FILE_PATH));
        // saveAs.currentFileChooser.setSelectedFiles(new File[] { new
        // File(FILE_PATH) });
        // saveAs.currentFileChooser.approveSelection();
        // saveAs.currentFileChooser.setApproveButtonMnemonic(MNEMONIC);
        // try {
        // new Robot().keyPress(MNEMONIC);
        // } catch (AWTException e) {
        // e.printStackTrace();
        // }

        // System.out.println("Got here.....");
        // JFileChooser fileChooser = (JFileChooser) component.get;
        // if (fileChooser == null)
        // System.out.println("File chooser == null");
        // else
        // System.out.println("Found the file chooser!");
        // fileChooser.accept(new File(FILE_PATH));

        // for (Window window : component.getWindows()) {
        // if (window instanceof JDialog) {
        // for (Component c : ((JDialog) window).getComponents()) {
        // System.out.println(c.toString());
        // }
        // }
        // }

        // for (Component c : component.getComponents()) {
        // if (c instanceof JFileChooser) {
        // System.out.println("Found JFileChooser component!");
        // JFileChooser fileChooser = (JFileChooser) c;
        // fileChooser.accept(new File(FILE_PATH));
        // break;
        // } else {
        // if (pressConfirmOnSaveAsDialogue(component))
        // return true;
        // }
        // }
        // System.out.println("Did NOT find JFileChooser component!");
        // return false;
    }

    /**
     * Gets the menu.
     * @return The menu.
     */
    private MenuBar getMenu() {
        return mainGUI.getTopMenuBar();
    }

    /**
     * Gets the save button listener in the controller class.
     * @return The save button listener in the controller class.
     */
    private MenuOptionSave getSave() {
        return spy((MenuOptionSave) getListener(
                getMenu().getMenuItemFileSave(), MenuOptionSave.class));
    }

    /**
     * Gets the save as button listener in the controller class.
     * @return The save as button listener in the controller class.
     */
    private MenuOptionSaveAs getSaveAs() {
        return spy((MenuOptionSaveAs) getListener(getMenu()
                .getMenuItemFileSaveAs(), MenuOptionSaveAs.class));
    }

}
