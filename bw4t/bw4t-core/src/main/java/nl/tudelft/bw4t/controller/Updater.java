package nl.tudelft.bw4t.controller;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

/**
 * A simple thread class that calls the given {@link AbstractMapController} periodically.
 */
class Updater implements Runnable {
	/**
	 * The log4j logger which writes logs.
	 */
	private static final Logger LOGGER = Logger.getLogger(Updater.class);

	/**
	 * the {@link AbstractMapController} associated with this update thread.
	 */
	private AbstractMapController controller;

	public Updater(AbstractMapController c) {
		controller = c;
	}

	@Override
	public void run() {
		if (controller.isRunning()) {
			return;
		}
		controller.setForceRunning(true);

		LOGGER.info("Started updater thread for: " + controller);

		while (controller.isRunning()) {

			SwingUtilities.invokeLater(controller);

			try {
				// Sleep for a short while
				Thread.sleep(controller.getRenderSettings().getUpdateDelay());
			} catch (InterruptedException e) {
				LOGGER.warn("The update thread was interrupted", e);
			}
		}
		LOGGER.info("Stopped updater thread for: " + controller);
		controller = null;
	}
}
