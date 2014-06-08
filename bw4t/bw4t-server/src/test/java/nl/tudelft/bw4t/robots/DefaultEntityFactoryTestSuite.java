package nl.tudelft.bw4t.robots;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import nl.tudelft.bw4t.model.robots.DefaultEntityFactory;
import nl.tudelft.bw4t.model.robots.handicap.ColorBlindHandicap;
import nl.tudelft.bw4t.model.robots.handicap.IRobot;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Test the default entity factory.
 */
public class DefaultEntityFactoryTestSuite {
    /**
     * The bot config object required to make bots.
     */
    @Mock private BotConfig bc;
    /**
     * The e partner config object required to make e partners.
     */
    @Mock private EPartnerConfig epc;
    /**
     * The factory
     */
    private DefaultEntityFactory def;
    /**
     * Set up objects.
     */
    @Before
    public void setUp() {
        bc = spy(new BotConfig());
        epc = spy(new EPartnerConfig());
        def = new DefaultEntityFactory();
        when(bc.getBotName()).thenReturn("test");
    }
    /**
     * Make a color blind bot.
     */
    @Test
    public void makeColorBlindBot() {
        when(bc.getColorBlindHandicap()).thenReturn(true);
        IRobot r = def.makeRobot(bc);
        assertTrue(r instanceof ColorBlindHandicap);
    }
}
