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
	public static final int MIN_DELAY = 1; //10;
	public static final int MAX_DELAY = 50; //200;

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
	private static long loopDelay = 10; //20;
	/**
	 * Whether to throttle the stepping loop.
	 * If true then each iteration will be interjected with a timeout of {@link #loopDelay} ms.
	 */
	private static boolean throttle = true;
	private boolean running = true;
	
	private long stepTime = 0;
	
	private static long nrSteps = 0;

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
					long stepStart = System.nanoTime();

					/*
					 * note: busy-wait since we have to be prepared to be killed
					 * also if env is in pause mode. The sleep avoid CPU use.
					 */
					if (environment.getState() == EnvironmentState.RUNNING) {
						if (runner.getModelActionCount() == 0) {
							runner.setFinishing(true);
						}
						runner.step();
						nrSteps = nrSteps + 1;
					}
					try {
						if (throttle) {
							Thread.sleep(loopDelay);
						}
					} catch (InterruptedException e) {
						LOGGER.warn("The main loop was interrupted.", e);
					}
					long stepEnd = System.nanoTime();
					this.stepTime = stepEnd - stepStart;
					//this.nrSteps = nrStepEnd - nrStepStart;
				}
				nrSteps = 0;
				runner.stop();
				runner.cleanUpRun();
			}
		} catch (Exception e) {
			LOGGER.error("An internal error occurred while running the stepper: ", e);
		}
		running = false;
		runner = null;
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
	
	public boolean getIsThrottled() {
		return throttle;
	}

	public void setThrottled(final boolean isIt) {
		throttle = isIt;
	}

	public long getStepTime() {
		return this.stepTime;
	}
	
	public static long getNrSteps() {
		return nrSteps;
	}
	
	public static void setNrSteps(long steps) {
		nrSteps = steps;
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
				Thread.sleep(100);
			} catch (InterruptedException e) {
				LOGGER.warn("Ignoring interrupt from wait.", e);
			}
		}
	}

}
