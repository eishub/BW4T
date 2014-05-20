package nl.tudelft.bw4t.client.startup;

import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

import java.util.HashMap;
import java.util.Map;

import nl.tudelft.bw4t.BW4TEnvironmentListener;
import nl.tudelft.bw4t.client.BW4TRemoteEnvironment;
import nl.tudelft.bw4t.startup.LauncherException;

/**
 * This class is used to startup the remote environment to interact with the
 * server
 * 
 * @author Jan Giesenberg
 */
public final class Launcher {

	/**
	 * store the remoteenvironment
	 */
	private static BW4TRemoteEnvironment environment;

	/**
	 * This is a utility class, no instantiation!
	 */
	private Launcher() {
	}

	/**
	 * convert the commandline params to eis params
	 * 
	 * @param args
	 *            the commandline arguments
	 */
	public static void main(String[] args) {
		/**
		 * load all known parameters into the init array, convert it to EIS
		 * format.
		 */
		Map<String, Parameter> init = new HashMap<String, Parameter>();
		for (InitParam param : InitParam.values()) {
			init.put(param.nameLower(), new Identifier(
					findArgument(args, param)));
		}

		startupEnvironment(init);
	}

	/**
	 * get the environment for the client
	 * 
	 * @return the remote environment
	 */
	public static BW4TRemoteEnvironment getEnvironment() {
		return environment;
	}

	/**
	 * Start the remote environment, connect to the server
	 * 
	 * @param initParams
	 *            the parameters to be given to the environment
	 *            {@link BW4TRemoteEnvironment#init(Map)}
	 * @return the created environment
	 */
	public static BW4TRemoteEnvironment startupEnvironment(
			Map<String, Parameter> initParams) {
		if (environment != null) {
			return environment;
		}
		environment = new BW4TRemoteEnvironment();
		environment.attachEnvironmentListener(new BW4TEnvironmentListener(
				environment));
		try {
			environment.init(initParams);
		} catch (ManagementException | NoEnvironmentException e) {
			throw new LauncherException(e);
		}
		return environment;
	}

	/**
	 * Find a certain argument in the string array and return its setting
	 * 
	 * @param args
	 *            , the string array containing all the arguments
	 * @param param
	 *            the parameter to look for. The name of the parameter in lower
	 *            case and prefixed with "-" should be in the list, and the next
	 *            element in the array then should contain the value.
	 * @return the value for the parameter. Returns default value if not in the
	 *         array.
	 */
	private static String findArgument(String[] args, InitParam param) {
		for (int i = 0; i < args.length - 1; i++) {
			if (args[i].equalsIgnoreCase("-" + param.nameLower())) {
				return args[i + 1];
			}
		}
		return param.getDefaultValue();
	}

}
