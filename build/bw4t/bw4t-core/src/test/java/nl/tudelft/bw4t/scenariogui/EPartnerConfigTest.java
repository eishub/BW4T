package nl.tudelft.bw4t.scenariogui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Class used for testing the EPartnerConfig class except the getters and setters.
 */
public class EPartnerConfigTest {

    private String name;
    private int amount;
    private String epartnerReferenceName;
    private String epartnerGoalFileName;
    private boolean gps;
    private boolean forgetmenot;

    /**
     * Tests whether the toString method displays the desired result.
     */
    @Test
    public void bcToStringTest() {
        setDefaultValues();
        EPartnerConfig epartner = createTestEPartner();
        assertNotEquals("Wrong string test", epartner.ecToString());
        assertEquals((name + amount + gps + forgetmenot +
                        epartnerGoalFileName + epartnerReferenceName),
                epartner.ecToString()
        );
    }

    private void setDefaultValues() {
        name = "testEpartner";
        amount = 10;
        epartnerReferenceName = "testRef";
        epartnerGoalFileName = "testEpartner.goal";
        gps = true;
        forgetmenot = false;
    }

    private EPartnerConfig createTestEPartner() {
        EPartnerConfig epartner = new EPartnerConfig();
        epartner.setEpartnerName(name);
        epartner.setEpartnerAmount(amount);
        epartner.setReferenceName(epartnerReferenceName);
        epartner.setFileName(epartnerGoalFileName);
        epartner.setGps(gps);
        epartner.setForgetMeNot(forgetmenot);

        return epartner;
    }

}
