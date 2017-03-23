package nl.tudelft.bw4t.server.environment;

import java.io.File;

import org.apache.log4j.Logger;

import eis.eis2java.environment.AbstractEnvironment;
import eis.iilang.EnvironmentState;
import repast.simphony.scenario.ScenarioLoadException;

/**
 * Stepper is the thread that schedules the bot stepping according to the
 * environment run mode and the loopDelay setting.
 */
public class Stepper implements Runnable {

	/**
	 * The log4j logger, logs to the console.
	 */
	public static final int MIN_DELAY = 10;
	public static final int MAX_DELAY = 200;
	public static final int DEFAULT_DELAY = 10;

	public static final int MIN_STEPS_AT_ONCE = 1;
	public static final int MAX_STEPS_AT_ONCE = 500;
	public static final int DEFAULT_STEPS_AT_ONCE = 100;

	private static final Logger LOGGER = Logger.getLogger(Stepper.class);
	/**
	 * HACK should be private.
	 */
	BW4TRunner runner;
	private final String scenarioLocation;
	private final AbstractEnvironment environment;
	/**
	 * default 10ms between steps
	 */
	private long loopDelay = DEFAULT_DELAY;


	/**
	 * Steps executed each loop
	 */
	private int stepsAtOnce = DEFAULT_STEPS_AT_ONCE;


	private boolean running = true;

	public Stepper(String scenario, AbstractEnvironment envi) throws ScenarioLoadException {
		scenarioLocation = scenario;
		environment = envi;
		runner = new BW4TRunner();
		runner.load(new File(scenarioLocation));
		runner.runInitialize();
	}


	@Override
	public void run() {
		try {
			while (running) {
				while (runner.getActionCount() > 0 && running) {
					/*
					 * note: busy-wait since we have to be prepared to be killed
					 * also if env is in pause mode. The sleep avoid sucking
					 * CPU.
					 */
					if (environment.getState() == EnvironmentState.RUNNING) {
						if (runner.getModelActionCount() == 0) {
							runner.setFinishing(true);
						}

						//Run multiple steps per frame, if the user wants to.
						for(int i = 0; i < stepsAtOnce; i++) {
							runner.step();
						}
					}
					try {
						Thread.sleep(loopDelay);
					} catch (InterruptedException e) {
						LOGGER.warn("The main loop was interrupted.", e);
					}
				}
				runner.stop();
				runner.cleanUpRun();
			}
		} catch (Exception e) {
			LOGGER.error("An internal error occurred while running the stepper: ", e);
		}
		running = false;
		runner = null;
	}

	public long getStepsAtOnce() {
		return stepsAtOnce;
	}

	/**
	 * set new delay value. Lower is faster animation speed.
	 *
	 * @param value
	 *            the value for the delay. Should be at least {@link #MIN_DELAY}
	 *            .
	 */
	public void setStepsAtOnce(int value) {
		if (value < MIN_STEPS_AT_ONCE || value > MAX_STEPS_AT_ONCE) {
			throw new IllegalArgumentException(
					"speed should be >=" + MIN_DELAY + " and <= " + MAX_DELAY + " but got " + value);
		}
		stepsAtOnce = value;

	}


	public long getDelay() {
		return loopDelay;
	}

	/**
	 * set new delay value. Lower is faster animation speed.
	 * 
	 * @param value
	 *            the value for the delay. Should be at least {@link #MIN_DELAY}
	 *            .
	 */
	public void setDelay(int value) {
		if (value < MIN_DELAY || value > MAX_DELAY) {
			throw new IllegalArgumentException(
					"speed should be >=" + MIN_DELAY + " and <= " + MAX_DELAY + " but got " + value);
		}
		loopDelay = value;

	}

	/**
	 * Call this only after EnvironmentState=KILLED. This function returns only
	 * after the stepper thread stopped.
	 */
	public void terminate() {
		running = false;
		while (runner != null) {
			LOGGER.info("Stepper is running... waiting for requests.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOGGER.warn("Ignoring interrupt from wait.", e);
			}
		}
	}

}
