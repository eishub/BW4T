package nl.tudelft.bw4t.server.logging;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nl.tudelft.bw4t.eis.RobotEntity;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.ErrorCode;

public class BW4TFileAppender extends FileAppender {

	/**
     * The log4j logger, logs to the file
     */
    private static final Logger LOGGER = Logger.getLogger(BW4TFileAppender.class);
    
    public BW4TFileAppender() {
    }

    public BW4TFileAppender(Layout layout, String filename) throws IOException {
        super(layout, filename);
    }

    public BW4TFileAppender(Layout layout, String filename, boolean append) throws IOException {
        super(layout, filename, append);
    }

    public BW4TFileAppender(Layout layout, String filename, boolean append, boolean bufferedIO,
            int bufferSize) throws IOException {
        super(layout, filename, append, bufferedIO, bufferSize);
    }

    @Override
    public void activateOptions() {
        if (fileName != null) {
            try {
                fileName = getNewLogFileName();
                setFile(fileName, fileAppend, bufferedIO, bufferSize);
            } catch (Exception e) {
                errorHandler.error("Error while activating log options", e, ErrorCode.FILE_OPEN_FAILURE);
            }
        }
    }

    private String getNewLogFileName() {
        if (fileName != null) {
            final String DOT = ".";
            final String HIPHEN = "-";
            final File logFile = new File(fileName);
            final String fileName = logFile.getName();
            String newFileName = "";

            final int dotIndex = fileName.indexOf(DOT);
            if (dotIndex != -1) {
            	// the current date to be inserted in the file name.
            	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
            	//get current date time with Date()
         	   	Date date = new Date();
                // the file name has an extension. so, insert the time stamp
                // between the file name and the extension
                newFileName = fileName.substring(0, dotIndex) + HIPHEN + dateFormat.format(date) + DOT
                        + fileName.substring(dotIndex + 1);
            } else {
                // the file name has no extension. So, just append the timestamp
                // at the end.
                newFileName = fileName + HIPHEN + System.currentTimeMillis();
            }
            return logFile.getParent() + File.separator + newFileName;
        }
        return null;
    }
    
    public static void logFinish(long timeOfCall, String typeTime) {
    	logTime(timeOfCall, typeTime);
    	logBot();
    }
    
    /**
     * Writing total time needed into logfile.
     */
    private static void logTime(long timeOfCall, String typeTime) {

    	BW4TEnvironment env = BW4TEnvironment.getInstance();
    	
    	//totalTime is in miliseconds
        double totalTime = (timeOfCall - env.getStarttime());
        
        if (totalTime > 60000) {
        	int totalMin = (int) totalTime / 60000;
        	int totalSec = (int) totalTime / 1000 % 60;
        	LOGGER.log(BotLog.BOTLOG, typeTime + totalMin + " minutes and " + totalSec + " seconds");
        }
        else
        	LOGGER.log(BotLog.BOTLOG, typeTime + totalTime / 1000 + "seconds");
    }
    
    /**
     * Writing sumarry of all bots into logfile.
     */
     private static void logBot() {
    	BW4TEnvironment env = BW4TEnvironment.getInstance();
    	
        for (String entity : env.getEntities()) {
        	if (env.getEntity(entity) instanceof RobotEntity) {
            	RobotEntity rEntity = (RobotEntity) env.getEntity(entity);
            	if (!env.getFreeEntities().contains(entity)) {
            		LOGGER.log(BotLog.BOTLOG, "agentsummary " + stringHandicap(rEntity));
            		rEntity.getRobotObject().getAgentRecord().logSummary();
            	}
        	}
        }
    }
     
     /**
      * Gets all handicaps and make a String of it, which can be used for the logfile.
      * 
      * @param bot RobotEntity
      * @return String
      */
     private static String stringHandicap(RobotEntity bot) {
    	 List<String> handicap = bot.getRobotObject().getHandicapsList();
    	 String handicaps = bot.getRobotObject().getName() + " handicaps ";
    	 if (handicap.isEmpty())
    		 handicaps = handicaps + "none";
    	 else {
    		 for (int i = 0; i < handicap.size(); i++) {
    		 handicaps = handicaps + handicap.get(i);
    		 }
    	 }
    	 return handicaps;
     }
}
