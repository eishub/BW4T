package nl.tudelft.bw4t.client.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.iilang.Parameter;
import eis.iilang.Percept;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * The class <code>TestAgentTest</code> contains tests for the class {@link <code>TestAgent</code>}
 */

@RunWith(MockitoJUnitRunner.class)
public class TestAgentTest {

    @Mock
    private RemoteEnvironment remoteEnvironment;
    private TestAgent testAgent;
    List<Percept> percepts;
    Parameter[] idParam;

    @Before
    public void setUp() throws Exception {
        testAgent = new TestAgent("test", remoteEnvironment);
        percepts = new LinkedList();
    }

    @Test
    public void constructorTest() {
        assertNotNull(testAgent);
        assertEquals("test", testAgent.getAgentId());
    }

    @Test
    public void testProcessPerceptsPlace() throws TranslationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        idParam = Translator.getInstance().translate2Parameter("placeTest");
        Percept percept = new Percept("place", idParam);
        percepts.add(percept);
        testAgent.processPercepts(percepts);

        Method method = TestAgent.class.getDeclaredMethod("action", null);
        method.setAccessible(true);
        Object[] argObjects = new Object[0];
        method.invoke(testAgent, argObjects);
        
        List<String> testPlaces = new LinkedList();
        Field field = TestAgent.class.getDeclaredField("places");
        field.setAccessible(true);
        testPlaces = (List<String>) field.get(testAgent);
        assertEquals("placeTest",testPlaces.get(0));
    }
    
    @Test
    public void testProcessPerceptsState() throws TranslationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        idParam = Translator.getInstance().translate2Parameter("stateTest");
        Percept percept = new Percept("state", idParam);
        percepts.add(percept);
        testAgent.processPercepts(percepts);
        
        Method method = TestAgent.class.getDeclaredMethod("action");
        method.setAccessible(true);
        Object[] argObjects = new Object[0];
        method.invoke(testAgent, argObjects);
        
        String testState;
        Field field = TestAgent.class.getDeclaredField("state");
        field.setAccessible(true);
        testState = (String) field.get(testAgent);
        assertEquals("stateTest",testState);
    }
    
    @Test
    public void testProcessPerceptsPlayer(){
        Percept percept = new Percept("player");
        percepts.add(percept);
        testAgent.processPercepts(percepts);
    }
    
    @Test
    public void testRunEnvKilled(){
        testAgent.setKilled();
        testAgent.run();
    }
}
