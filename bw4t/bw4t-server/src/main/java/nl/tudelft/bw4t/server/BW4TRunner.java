package nl.tudelft.bw4t.server;

import java.io.File;

import org.apache.log4j.Logger;

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
import repast.simphony.parameter.Parameters;
import repast.simphony.parameter.Schema;
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
	
	/**
	 * The log4j logger, logs to the console.
	 */
	private static Logger logger = Logger.getLogger(Launcher.class);
	
	private RunEnvironmentBuilder runEnvironmentBuilder;
	protected Controller bw4tController;
	protected boolean pauseRunner = false;
	protected Object monitor = new Object();
	protected SweeperProducer producer;
	private ISchedule schedule;

	public BW4TRunner() {
		runEnvironmentBuilder = new DefaultRunEnvironmentBuilder(this, true);
		bw4tController = new DefaultController(runEnvironmentBuilder);
		bw4tController.setScheduleRunner(this);
	}

	public void load(File scenarioDir) throws ScenarioLoadException {
		if (scenarioDir.exists()) {
			BatchScenarioLoader loader = new BatchScenarioLoader(scenarioDir);
			ControllerRegistry registry = loader.load(runEnvironmentBuilder);
			bw4tController.setControllerRegistry(registry);
		} else {
			logger.error("Scenario was not found.");
			return;
		}

		bw4tController.batchInitialize();
		bw4tController.runParameterSetters(null);
	}

	public void runInitialize() {
		Parameters params = new Parameters() {
			/**
			 * This should be changed. Temporary fix in order to be able to run BW4T.
			 */
			@Override
			public void setValue(String paramName, Object val) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isReadOnly(String paramName) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public String getValueAsString(String paramName) {
				// TODO Auto-generated method stub
				return "asd";
			}
			
			@Override
			public Object getValue(String paramName) {
				// TODO Auto-generated method stub
				return 223;
			}
			
			@Override
			public Schema getSchema() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getDisplayName(String paramName) {
				// TODO Auto-generated method stub
				return "asd";
			}
			@Override
			public Parameters clone() {
				// TODO Auto-generated method stub
				return this;
			}
		};
		bw4tController.runInitialize(params);
		schedule = RunState.getInstance().getScheduleRegistry()
				.getModelSchedule();
		fireStartedMessage();
	}

	public void cleanUpRun() {
		bw4tController.runCleanup();
	}

	public void cleanUpBatch() {
		bw4tController.batchCleanup();
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
