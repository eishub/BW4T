package nl.tudelft.bw4t.map.renderer;

import java.lang.reflect.InvocationTargetException;

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

    /**
     * Setup a new Updater object for the given MapController.
     * 
     * @param c
     *            the map controller
     */
    public Updater(AbstractMapController c) {
        controller = c;
    }

    @Override
    public void run() {
        LOGGER.info("Initializing updater thread for: " + controller);
        if (controller.isRunning() || !controller.isStarting()) {
            controller = null;
            return;
        }
        controller.setForceRunning(true);

        LOGGER.info("Started updater thread for: " + controller);
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.warn("Interrupted the Updater initial wait period", e);
        }

        while (controller.isRunning() && !controller.isStarting()) {
            try {
                try {
                    SwingUtilities.invokeAndWait(controller);
                } catch (InvocationTargetException e) {
                    LOGGER.error("An Error occured while trying to update: " + controller, e);
                    controller.setRunning(false);
                }

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
