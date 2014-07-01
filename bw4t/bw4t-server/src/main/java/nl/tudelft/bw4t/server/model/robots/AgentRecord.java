package nl.tudelft.bw4t.server.model.robots;

import nl.tudelft.bw4t.server.model.zone.Zone;

/**
 * This class stores performance information of an agent.
 */
public class AgentRecord {
    /**
     * name of agent
     */
    private String name;

    /**
     * number of good drops.
     */
    private Integer goodDrops = 0;

    /**
     * number of wrong drops
     */
    private Integer wrongDrops = 0;

    /**
     * number of messages
     */
    private Integer nMessages = 0;

    /**
     * number of rooms entered
     */
    private Integer nRoomsEntered = 0;

    /**
     * Sets last zone entered to null
     */
    private Zone lastZoneEntered = null;

    /** accumulated milliseconds of standing still */
    private Long totalStandingStillMillis = 0L;
    /**
     * current time stamp where agent stopped moving. If 0, the agent is moving now.
     */
    private Long currentStandingStillMillis = System.currentTimeMillis();

    /**
     * create new Agent record
     * 
     * @param agentName
     *           The name of the agent
     */
    public AgentRecord(String agentName) {
        name = agentName;
    }

    /**
     * Amount of good drops.
     */
    public void addGoodDrop() {
        goodDrops++;
    }

    /**
     * Amount of wrong drops
     */
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
     * should be called when agent enters room ensures that the same room does not get printed twice.
     * @param entered
     *          the zone that has been entered
     */
    public void addEnteredRoom(Zone entered) {
        if (lastZoneEntered == entered) {
            return;
        }
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

    public String getName() {
        return name;
    }

    public Integer getGoodDrops() {
        return goodDrops;
    }

    public Integer getWrongDrops() {
        return wrongDrops;
    }

    public Integer getNMessages() {
        return nMessages;
    }

    public Integer getNRoomsEntered() {
        return nRoomsEntered;
    }

    public long getTotalStandingStillMillis() {
        return totalStandingStillMillis;
    }
}
