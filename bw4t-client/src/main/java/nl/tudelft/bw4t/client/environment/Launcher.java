package nl.tudelft.bw4t.client.environment;

import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

import java.util.HashMap;
import java.util.Map;

import nl.tudelft.bw4t.client.startup.InitParam;
import nl.tudelft.bw4t.util.LauncherException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * This class is used to startup the remote environment to interact with the
 * server
 */
public final class Launcher {

    /** The log4j Logger which displays logs on console. */
    private static final Logger LOGGER = Logger.getLogger(Launcher.class);

    /** This is a utility class, no instantiation! */
    private Launcher() {
    }

    /**
     * Convert the console parameters to EIS parameters.
     * 
     * @param args
     *            The console arguments.
     * @return the started environment
     */
    public static RemoteEnvironment launch(String[] args) {
         //Set up the logging environment to log on the console.
        if (!LOGGER.getAllAppenders().hasMoreElements()) {
            BasicConfigurator.configure();
        }
        LOGGER.info("Starting up BW4T Client.");
        LOGGER.info("Reading initialization parameters...");
        /**
         * Load all known parameters into the init array, convert it to EIS format.
         */
        Map<String, Parameter> init = new HashMap<String, Parameter>();
        for (InitParam param : InitParam.values()) {
            if (param == InitParam.GOAL) {
                LOGGER.info("Setting parameter 'GOAL' with 'false' because we started from commandline.");
                init.put(param.nameLower(), new Identifier("false"));
            } else {
                final Parameter value = findParameter(args, param);
                if (value != null) {
                    init.put(param.nameLower(), value);
                }
            }
        }
        return startupEnvironment(init);
    }

    /**
     * Start the client and connect to the server.
     * 
     * @param initParams
     *            - The parameters to be given to the environment
     *            {@link RemoteEnvironment#init(Map)}.
     * @return The created {@link RemoteEnvironment}.
     */
    public static synchronized RemoteEnvironment startupEnvironment(Map<String, Parameter> initParams) {
        RemoteEnvironment environment = new RemoteEnvironment();
        environment.attachEnvironmentListener(new BW4TEnvironmentListener(environment));
        try {
            LOGGER.info("Initializing environment...");
            environment.init(initParams);
        } catch (ManagementException | NoEnvironmentException e) {
            LOGGER.error("The Launcher encountered an error while trying to initialize the environment.");
            throw new LauncherException(e);
        }
        return environment;
    }

    /**
     * Find a certain argument in the string array and return its setting.
     * 
     * @param args
     *            - The string array containing all the arguments
     * @param param
     *            - The parameter to look for. The name of the parameter in lower
     *            case and prefixed with "-" should be in the list, and the next
     *            element in the array should then contain the value.
     * @return The value for the parameter. Returns default value if not in the array.
     */
    public static Identifier findParameter(String[] args, InitParam param) {
        for (int i = 0; i < (args.length - 1); i++) {
            if (args[i].equalsIgnoreCase("-" + param.nameLower())) {
                LOGGER.debug("Found parameter '" + param.nameLower() + "' with '" + args[i + 1] + "'");
                return new Identifier(args[i + 1]);
            }
        }
        LOGGER.debug("Defaulting parameter '" + param.nameLower() + "' with '" + param.getDefaultValue() + "'");
        return null;
    }
}
