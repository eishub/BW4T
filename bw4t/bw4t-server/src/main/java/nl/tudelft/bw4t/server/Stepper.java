package nl.tudelft.bw4t.server;

import java.io.File;

import org.apache.log4j.Logger;

import repast.simphony.scenario.ScenarioLoadException;
import eis.eis2java.environment.AbstractEnvironment;
import eis.iilang.EnvironmentState;

/**
 * Stepper is the thread that schedules the bot stepping according to the environment run mode and the loopDelay
 * setting.
 * 
 * @author W.Pasman 12mar13
 * 
 */
public class Stepper implements Runnable {

	/**
	 * The log4j logger, logs to the console.
	 */
	private static Logger logger = Logger.getLogger(Launcher.class);

	BW4TRunner runner; // HACK should be private.
	private String scenarioLocation;
	private AbstractEnvironment environment;
	private long loopDelay = 10; // default 10ms between steps
	private boolean running = true;

	public final static int MIN_DELAY = 10;
	public final static int MAX_DELAY = 200;

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
					 * note: busy-wait since we have to be prepared to be killed also if env is in pause mode. The sleep
					 * avoid sucking CPU.
					 */
					if (environment.getState() == EnvironmentState.RUNNING) {
						if (runner.getModelActionCount() == 0) {
							runner.setFinishing(true);
						}
						runner.step();
					}
					Thread.sleep(loopDelay);
				}
				runner.stop();
				runner.cleanUpRun();
			}
		} catch (Exception e) {
			logger.error("An internal error occurred while running the stepper: ", e);
		}
		running = false;
		runner = null;
	}

	/**
	 * wait till run mode allows us to move the entities.
	 */
	private void waitTillWeRun() {
		while (environment.getState() != EnvironmentState.RUNNING) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.warn("Wait for run state was unexpectedly interrupted, returning to wait state.");
			}
		}
	}

	/**
	 * set new delay value. Lower is faster animation speed.
	 * 
	 * @param value
	 *            the value for the delay. Should be at least {@link #MIN_DELAY} .
	 */
	public void setDelay(int value) {
		if (value < MIN_DELAY) {
			throw new IllegalArgumentException("speed should be >=10 but got " + value);
		}
		loopDelay = value;

	}

	/**
	 * Call this only after EnvironmentState=KILLED. This function returns only after the stepper thread stopped.
	 */
	public void terminate() {
		running = false;
		while (runner != null) {
			logger.info("Stepper is running... waiting for requests.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("Ignoring interrupt from wait.");
			}
		}
	}

}
