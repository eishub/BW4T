package nl.tudelft.bw4t.scenariogui;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class BotConfigTest {

    private static final String BASE = System.getProperty("user.dir") + "/src/test/resources/";

    private static final String FILE_TEST_ROBOT_GOAL = BASE + "test_robot.goal";
 
    @Test
    public void fileNameExistsTest(){
        File f = new File(FILE_TEST_ROBOT_GOAL);
        File f2 = new File(BASE + "test_robot2.goal");
        assertTrue(f.exists());
        assertFalse(f2.exists());
    }
    

}
