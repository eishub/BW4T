package nl.tudelft.bw4t.server.logging;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorCode;

public class BW4TFileAppender extends FileAppender {

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
				// the file name has an extension. so, insert the time stamp
				// between the file name and the extension
				newFileName = fileName.substring(0, dotIndex) + HIPHEN + System.currentTimeMillis() + DOT
						+ fileName.substring(dotIndex + 1);
			}
			else {
				// the file name has no extension. So, just append the timestamp
				// at the end.
				newFileName = fileName + HIPHEN + System.currentTimeMillis();
			}
			return logFile.getParent() + File.separator + newFileName;
		}
		return null;
	}
}
