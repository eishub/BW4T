package nl.tudelft.bw4t.client.startup;

import java.util.HashMap;
import java.util.Map;

import nl.tudelft.bw4t.BW4TEnvironmentListener;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.startup.LauncherException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

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
    private static RemoteEnvironment environment;

    /**
     * The log4j Logger which displays logs on console
     */
    private static Logger logger = Logger.getLogger(Launcher.class);

    /**
     * This is a utility class, no instantiation!
     */
    private Launcher() {
    }

    /**
     * convert the console parameters to EIS parameters
     * 
     * @param args
     *            the console arguments
     */
    public static void launch(String[] args) {
        /**
         * Set up the logging environment to log on the console.
         */
        BasicConfigurator.configure();
        logger.info("Starting up BW4T Client.");
        logger.info("Reading initialization parameters...");
        /**
         * Load all known parameters into the init array, convert it to EIS
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
    public static RemoteEnvironment getEnvironment() {
        return environment;
    }

    /**
     * Start the remote environment, connect to the server
     * 
     * @param initParams
     *            the parameters to be given to the environment
     *            {@link RemoteEnvironment#init(Map)}
     * @return the created environment
     */
    public static RemoteEnvironment startupEnvironment(
            Map<String, Parameter> initParams) {
        if (environment != null) {
            return environment;
        }
        environment = new RemoteEnvironment();
        environment.attachEnvironmentListener(new BW4TEnvironmentListener(
                environment));
        try {
            logger.info("Initializing environment...");
            environment.init(initParams);
        } catch (ManagementException | NoEnvironmentException e) {
            logger.error("The Launcher encountered an error while trying to initialize the environment.");
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
