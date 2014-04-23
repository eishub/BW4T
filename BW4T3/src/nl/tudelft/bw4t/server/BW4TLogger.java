package nl.tudelft.bw4t.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import nl.tudelft.bw4t.map.BlockColor;

/**
 * This logger logs all actions to a file:
 * <ol>
 * <li>sequence: goal sequence (which block colors are to be dropped)
 * <li>room: initial blocks per room
 * <li>action: log of each action of a bot, with timestamp in ms since january
 * 1, 1970
 * <li>sequencecomplete: total time to complete task. Begin time is determined
 * by first incoming action. End time is determined by the last block of the
 * sequence dropped.
 * <li>agentsummary: for each agent:
 * <ul>
 * <li>the bot type
 * <li># correct drops in dropzone
 * <li># incorrect drops in dropzone
 * <li>total time of standing still
 * <li>#messages to other agents
 * <li>#rooms entered
 * </ul>
 * </ol>
 * We will write info as soon as possible to the log file, so that you at least
 * have some log info even when the system is killed before the end is reached
 * (sequence completed).
 * <p>
 * The logger is a singleton object because it needs to be called from various
 * repast objects and also from the server.
 * <p>
 * This class should be thread safe.
 * 
 * @author W.Pasman 1dec2011
 * 
 */
public class BW4TLogger {
	// set to true if logging fails and
	// warning was printed.

	static boolean alreadywarned;
	private static File logFile;
	private static FileWriter writer;
	private static BW4TLogger theLogger = null; // the singleton

	private static Long starttime = null;

	/**
	 * Here we store per-agent performance records.
	 */
	private static HashMap<String, AgentRecord> agentRecords;

	/**
	 * Create a new Logger.
	 * 
	 * @throws IOException
	 *             if log file can not be created.
	 */
	private BW4TLogger() {
		reset();
		try {
			System.out.println("creating log file");
			logFile = File.createTempFile("BW4T", ".txt", new File("."));
			writer = new FileWriter(logFile);
			ArrayList<String> info = new ArrayList<String>();
			info.add("logcreationtime " + System.currentTimeMillis());
			log(info);
		} catch (IOException e) {
			System.out
					.println("WARNING. opening log file failed. Proceeding as if file opened OK.");
			e.printStackTrace();
		}
	}

	/**
	 * reset all internal values to null/default.
	 */
	private void reset() {
		alreadywarned = false;
		logFile = null;
		writer = null;
		theLogger = null;
		starttime = null;
		agentRecords = new HashMap<String, AgentRecord>();

	}

	/**
	 * get the logger instance
	 * 
	 * @return the BW4TLogger
	 * @throws IOException
	 */
	public synchronized static BW4TLogger getInstance() {
		if (theLogger == null) {
			theLogger = new BW4TLogger();
		}
		return theLogger;
	}

	/**
	 * get the agent's record. Creates new one if it does not yet exist.
	 * 
	 * @param agent
	 *            is the name of the agent
	 * @return AgentRecord.
	 */
	private AgentRecord getAgentRecord(String agent) {
		if (agentRecords.get(agent) == null) {
			agentRecords.put(agent, new AgentRecord(agent, "unknown"));
		}
		return agentRecords.get(agent);
	}

	/**
	 * Log to file. The items are written to a single row, with tabs separating
	 * them and a newline at the end. If there are 0 items in the list, nothing
	 * is written to the log file.
	 * 
	 * @param text
	 *            is text to be appended to log file.
	 * @throws IOException
	 */
	public void log(String[] items) {
		log(Arrays.asList(items));
	}

	/**
	 * Log to file. The items are written to a single row, with tabs separating
	 * them and a newline at the end. If there are 0 items in the list, nothing
	 * is written to the log file.
	 * 
	 * @param text
	 *            is text to be appended to log file.
	 */
	public synchronized void log(List<String> items) {

		try {
			if (items.size() == 0) {
				return;
			}
			writer.write(items.get(0));

			for (int i = 1; i < items.size(); i++) {
				writer.write("\t" + items.get(i));
			}

			writer.write("\n");
			writer.flush();
		} catch (IOException e) {
			if (alreadywarned)
				return;
			System.out.println("WARNING. Writing to log file is failing."
					+ e.getMessage());
			e.printStackTrace();
			alreadywarned = true;
		}
	}

	/**
	 * write the team info to log file.
	 * 
	 * @param nAgent
	 *            is number of agents in team
	 * @param nHumans
	 *            is number of humans in the team
	 */
	public void logTeam(Integer nAgents, Integer nHumans) throws IOException {
		log(new String[] { "team", nAgents.toString(), nHumans.toString() });
	}

	/**
	 * log the goal sequence
	 * 
	 * @param sequence
	 *            is a list of Strings, each item being a color.
	 * @throws IOException
	 */
	public void logSequence(List<BlockColor> sequence) throws IOException {
		ArrayList<String> logString = new ArrayList<String>();
		logString.add("sequence");
		for (BlockColor col : sequence) {
			logString.add(" " + col.getLetter());
		}
		log(logString);
	}

	/**
	 * called when a robot makes a good drop in the drop zone. Nothing is logged
	 * at this point
	 */
	public void logGoodDrop(String entity) {
		getAgentRecord(entity).addGoodDrop();
	}

	/**
	 * called when a robot makes a wrong drop in the drop zone. Nothign is
	 * logged at this point
	 */
	public void logWrongDrop(String entity) {
		getAgentRecord(entity).addWrongDrop();
	}

	/**
	 * log the action. If this is the first action, it is also the start time.
	 * 
	 * @param entity
	 * @param action
	 */
	public void logAction(String entity, String action) {
		Long time = System.currentTimeMillis();
		log(new String[] { "action ", "" + time, entity, action });
		if (starttime == null) {
			starttime = time;
		}
	}

	/**
	 * record a sent message
	 * 
	 * @param entity
	 */
	public void logSentMessageAction(String entity) {
		getAgentRecord(entity).addSentMessage();
	}

	public void logEnteredRoom(String entity) {
		getAgentRecord(entity).addEnteredRoom();
	}

	/**
	 * log the start of a motion
	 * 
	 * @param entity
	 */
	public void logMoving(String entity) {
		getAgentRecord(entity).setStartedMoving();
	}

	/**
	 * log the stop of a motion
	 * 
	 * @param entity
	 */
	public void logStopMoving(String entity) {
		getAgentRecord(entity).setStoppedMoving();
	}

	/**
	 * This adds the agent summary to the log file and closes the log file. Any
	 * attempt to log after this will create a new log file.
	 */
	public synchronized void closeLog() {
		System.out.println("closing the log");
		logAgentSummary();
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("WARNING. log file did not close:"
					+ e.getMessage());
		}
		reset();
	}

	private void logAgentSummary() {
		for (AgentRecord agentrecord : agentRecords.values()) {
			for (List<String> summary : agentrecord.toSummaryArray()) {
				log(summary);
			}
		}

	}

	/**
	 * Log a room and the blocks in there.
	 * 
	 * @param room
	 *            is name of room
	 * @param blocksInRoom
	 *            is list of block colors
	 */
	public void logRoomBlocks(String room, List<BlockColor> blocksInRoom) {

		ArrayList<String> logString = new ArrayList<String>();
		logString.add("room");
		logString.add(room);
		for (BlockColor c : blocksInRoom) {
			logString.add(c.getLetter().toString());
		}
		log(logString);
	}

	/**
	 * This is called when the sequence has been completed.
	 */
	public void logCompletedSequence() {
		Long complete = System.currentTimeMillis();
		log(new String[] { "sequencecomplete", "" + complete });
		log(new String[] { "totaltime",
				"" + (float) (complete - starttime) / 1000. });
		closeLog(); // no more actions after this point, close right away.
	}
}