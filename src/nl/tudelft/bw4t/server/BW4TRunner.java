package nl.tudelft.bw4t.server;

import java.io.File;

import repast.simphony.batch.BatchScenarioLoader;
import repast.simphony.engine.controller.Controller;
import repast.simphony.engine.controller.DefaultController;
import repast.simphony.engine.environment.AbstractRunner;
import repast.simphony.engine.environment.ControllerRegistry;
import repast.simphony.engine.environment.DefaultRunEnvironmentBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.environment.RunEnvironmentBuilder;
import repast.simphony.engine.environment.RunState;
import repast.simphony.engine.environment.Runner;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.parameter.SweeperProducer;
import repast.simphony.scenario.ScenarioLoadException;

/**
 * This class implements the repast {@link Runner}. This handles the calls to
 * the repast stepping - when do bots move, how often, and pausing etc. This is
 * modified copy of TestRunner_2 (see #2009 and #2236). I did not give this much
 * thought, I just plugged it in and it did what I hoped for - being able to
 * control Repast. Otherwise, scheduling/running is not done here at all, but
 * from the {@link BW4TEnvironment} directly by calling {@link #step()}.
 * 
 * @author W.Pasman 11mar13
 * 
 */
public class BW4TRunner extends AbstractRunner {
	private RunEnvironmentBuilder runEnvironmentBuilder;
	protected Controller controller;
	protected boolean pause = false;
	protected Object monitor = new Object();
	protected SweeperProducer producer;
	private ISchedule schedule;

	public BW4TRunner() {
		runEnvironmentBuilder = new DefaultRunEnvironmentBuilder(this, true);
		controller = new DefaultController(runEnvironmentBuilder);
		controller.setScheduleRunner(this);
	}

	public void load(File scenarioDir) throws ScenarioLoadException {
		if (scenarioDir.exists()) {
			BatchScenarioLoader loader = new BatchScenarioLoader(scenarioDir);
			ControllerRegistry registry = loader.load(runEnvironmentBuilder);
			controller.setControllerRegistry(registry);
		} else {
			System.out.println("Scenario not found");
			return;
		}

		controller.batchInitialize();
		controller.runParameterSetters(null);
	}

	public void runInitialize() {
		controller.runInitialize(null);
		schedule = RunState.getInstance().getScheduleRegistry()
				.getModelSchedule();
		fireStartedMessage();
	}

	public void cleanUpRun() {
		controller.runCleanup();
	}

	public void cleanUpBatch() {
		controller.batchCleanup();
	}

	// returns the tick count of the next scheduled item
	public double getNextScheduledTime() {
		return ((Schedule) RunEnvironment.getInstance().getCurrentSchedule())
				.peekNextAction().getNextTime();
	}

	/**
	 * see {@link ISchedule#getModelActionCount()}.
	 */
	public int getModelActionCount() {
		return schedule.getModelActionCount();
	}

	/**
	 * see {@link ISchedule#getActionCount()}.
	 */
	public int getActionCount() {
		return schedule.getActionCount();
	}

	// Step the schedule
	public void step() {
		schedule.execute();
	}

	// stop the schedule
	public void stop() {
		if (schedule != null)
			schedule.executeEndActions();
		fireStoppedMessage();
	}

	public void setFinishing(boolean fin) {
		schedule.setFinishing(fin);
	}

	public void execute(RunState toExecuteOn) {
		// required AbstractRunner stub. We will control the
		// schedule directly.
	}

}
