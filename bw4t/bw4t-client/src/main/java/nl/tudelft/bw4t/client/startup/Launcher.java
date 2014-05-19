package nl.tudelft.bw4t.client.startup;

import java.util.HashMap;
import java.util.Map;

import nl.tudelft.bw4t.BW4TEnvironmentListener;
import nl.tudelft.bw4t.client.BW4TRemoteEnvironment;
import nl.tudelft.bw4t.startup.LauncherException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

public class Launcher {

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
		BW4TRemoteEnvironment env = new BW4TRemoteEnvironment();
		env.attachEnvironmentListener(new BW4TEnvironmentListener(env));
		try {
			env.init(init);
		} catch (ManagementException | NoEnvironmentException e) {
			throw new LauncherException(e);
		}

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
