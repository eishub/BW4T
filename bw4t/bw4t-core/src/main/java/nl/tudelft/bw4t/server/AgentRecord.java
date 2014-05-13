package nl.tudelft.bw4t.server;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores performance information of an agent.
 * 
 * @author W.Pasman 1dec2011
 * 
 */
public class AgentRecord {
	private String name; // name of agent
	private String type; // human or bot?
	private Integer goodDrops = 0; // number of good drops.
	private Integer wrongDrops = 0;// number of wrong drops
	private Integer nMessages = 0; // number of messages
	private Integer nRoomsEntered = 0; // number of rooms entered

	/** accumulated milliseconds of standing still */
	private Long totalStandingStillMillis = 0L;
	/**
	 * current time stamp where agent stopped moving. If 0, the agent is moving
	 * now.
	 */
	private Long currentStandingStillMillis = System.currentTimeMillis();

	/**
	 * create new Agent record
	 * 
	 * @param agentName
	 * @param tp
	 *            is the type (human/bot).
	 */
	public AgentRecord(String agentName, String tp) {
		name = agentName;
		type = tp;
	}

	public void addGoodDrop() {
		goodDrops++;
	}

	public void addWrongDrop() {
		wrongDrops++;
	}

	/**
	 * this informs the record that the agent is standing still now. If called
	 * multiple times, the first time is used.
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
	 * This informs the record that the agent is moving now. If called multiple
	 * times, the first time is used.
	 */
	public void setStartedMoving() {
		if (currentStandingStillMillis != 0L) {
			// we were really standing still.
			totalStandingStillMillis += (System.currentTimeMillis() - currentStandingStillMillis);
			currentStandingStillMillis = 0L;
		}
	}

	/**
	 * should be called when agent sends message
	 */
	public void addSentMessage() {
		nMessages++;
	}

	/**
	 * Convert this record to a summary list for in the log file. The list is a
	 * number of lines for in the log, each element of the top list having a
	 * list of items for one log line.
	 * 
	 * @return
	 */
	public List<List<String>> toSummaryArray() {
		List<List<String>> summaries = new ArrayList<List<String>>();

		summaries.add(summary("type", type));
		summaries.add(summary("gooddrops", "" + goodDrops));
		summaries.add(summary("wrongdrops", "" + wrongDrops));
		summaries.add(summary("nmessage", "" + nMessages));
		summaries.add(summary("idletime", "" + (float) totalStandingStillMillis
				/ 1000.));
		summaries.add(summary("nroomsentered", "" + nRoomsEntered));

		return summaries;
	}

	private List<String> summary(String label, String value) {
		List<String> summary = new ArrayList<String>();
		summary.add("agentsummary");
		summary.add(name);
		summary.add(label);
		summary.add(value);
		return summary;
	}
}
