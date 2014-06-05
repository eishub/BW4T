package nl.tudelft.bw4t.robots;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.logging.BotLog;

import org.apache.log4j.Logger;

/**
 * This class stores performance information of an agent.
 */
public class AgentRecord {
    // name of agent
    private String name; 
     // number of good drops.
    private Integer goodDrops = 0;
    // number of wrong drops
    private Integer wrongDrops = 0;
    // number of messages
    private Integer nMessages = 0; 
    // number of rooms entered
    private Integer nRoomsEntered = 0; 

    /** accumulated milliseconds of standing still */
    private Long totalStandingStillMillis = 0L;
    /**
     * current time stamp where agent stopped moving. If 0, the agent is moving now.
     */
    private Long currentStandingStillMillis = System.currentTimeMillis();

    /**
     * The log4j logger, logs to the console and file
     */
    private static final Logger LOGGER = Logger.getLogger(BW4TEnvironment.class);
    
    /**
     * create new Agent record
     * 
     * @param agentName
     * @param tp
     *            is the type (human/bot).
     */
    public AgentRecord(String agentName) {
        name = agentName;
    }

    public void addGoodDrop() {
        goodDrops++;
    }

    public void addWrongDrop() {
        wrongDrops++;
    }

    /**
     * this informs the record that the agent is standing still now. If called multiple times, the first time is used.
     */
    public void setStoppedMoving() {
        if (currentStandingStillMillis == 0) {
            currentStandingStillMillis = System.currentTimeMillis();
        }
    }

    /**
     * should be called when agent enters room
     */
    public void addEnteredRoom() {
        nRoomsEntered++;
    }

    /**
     * This informs the record that the agent is moving now. If called multiple times, the first time is used.
     */
    public void setStartedMoving() {
        if (currentStandingStillMillis != 0L) {
            // we were really standing still.
            totalStandingStillMillis += (System.currentTimeMillis() - currentStandingStillMillis);
            currentStandingStillMillis = 0L;
        }
    }

    /**
     * should be called when agent sends message.
     */
    public void addSentMessage() {
        nMessages++;
    }
    
    /**
     * Write summary in logfile.
     */
    public void logSummary(){
    	 summary("gooddrops", "" + goodDrops);
         summary("wrongdrops", "" + wrongDrops);
         summary("nmessage", "" + nMessages);
         summary("idletime", "" + (float) totalStandingStillMillis / 1000.);
         summary("nroomsentered", "" + nRoomsEntered);
    }
    
    private void summary(String label, String value){
    	LOGGER.log(BotLog.BOTLOG, String.format("agentsummary %s %s %s", name, label, value));
    }
}
