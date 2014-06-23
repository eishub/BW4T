package nl.tudelft.bw4t.server.environment;

import eis.exceptions.ManagementException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.server.BW4TServer;
import nl.tudelft.bw4t.server.model.robots.DefaultEntityFactory;
import nl.tudelft.bw4t.server.model.robots.EntityFactory;
import nl.tudelft.bw4t.util.FileUtils;
import nl.tudelft.bw4t.util.LauncherException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import repast.simphony.scenario.ScenarioLoadException;


/**
 * This class handles the startup of the server interpreting the configuration variables and invoking the proper
 * classes. First the parameters are parsed, next the envirnment is started and lastly the server is setup to accept
 * client connections.
 */
public class Launcher {
    /**
     * The instance of the Launcher used to start this server.
     */
    private static Launcher instance;
    /**
     * The Bw4tEnvironment at the core of this server.
     */
    private static BW4TEnvironment environment;

    /**
     * The log4j logger, logs to the console.
     */
    private static final Logger LOGGER = Logger.getLogger(Launcher.class);

    // Parameters from the operatingsystem

    /**
     * The path to the scenario folder required by repast.
     */
    private String paramScenario;
    /**
     * the name of the map file in the /maps/ folder.
     */
    private String paramMap;
    /**
     * the ip to bind this server to.
     */
    private String paramServerIp;
    /**
     * the port to bind this server to.
     */
    private int paramServerPort;
    /**
     * the message to be made available to the clients.
     */
    private String paramServerMsg;
    /**
     * true if GUI should be enabled, false if server should run without GUI.
     */
    private boolean paramGUI;
    /**
     * The key necessary to remotely kill the server.
     */
    private String paramKey;
    /**
     * stores a reference to the robot factory being used to create new robots.
     */
    private EntityFactory entityFactory;
    /**
     * true if collisions are enabled.
     */
    private boolean paramCollision;
    /**
     * True if draw paths is enabled
     */
    private boolean paramDrawPaths;
    /**
     * This class cannot be externally instantiated, it is a utility startup class.
     * 
     * @param args
     *            The arguments received from the commandline
     */
    protected Launcher(final String[] args) {
        setInstance(this);
        /**
         * Set up the logging environment to log on the console.
         */
        PropertyConfigurator.configure(Launcher.class.getResource("/log4j.xml"));
        LOGGER.info("Starting up BW4T Server.");
        LOGGER.info("Reading console arguments...");
        readParameters(args);
        LOGGER.info("Setting up correct directory structure.");
        setupDirectoryStructure();
        LOGGER.info("Setting up BW4T Environment.");
        setupEnvironment();
        setupFactories();
        LOGGER.info("Starting the BW4T Environment.");
        startEnvironment();
    }
    

    /**
     * Interpret the parameter sent from the operating system.
     * 
     * @param args
     *            the commandline arguments
     */
    private void readParameters(final String[] args) {
        paramScenario = findArgument(args, "-scenario", "./BW4T.rs");
        paramMap = findArgument(args, "-map", "Random");
        paramServerIp = findArgument(args, "-serverip", "localhost");
        paramServerPort = Integer.parseInt(findArgument(args, "-serverport", "8000"));
        paramServerMsg = findArgument(args, "-msg", "Hello I am an BW4T Server version " + BW4TEnvironment.VERSION
                + ".");
        paramGUI = Boolean.parseBoolean(findArgument(args, "-gui", "true"));
        paramKey = findArgument(args, "-key", "GuVC7TZ38NN49X8utMspV3Z5");
        paramCollision = Boolean.parseBoolean(findArgument(args, "-collision", "true"));
        paramDrawPaths = Boolean.parseBoolean(findArgument(args, "-paths", "false"));
    }

    /**
     * Find a certain argument in the string array and return its setting.
     * 
     * @param args
     *            the string array containing all the arguments
     * @param def
     *            The default value for this argument
     * @param name
     *            what to check for
     * @return the result
     */
    private String findArgument(String[] args, String name, String def) {
        String result = def;
        LOGGER.debug("Default for parameter '" + name + "' is '" + def + "'");
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equalsIgnoreCase(name)) {
                LOGGER.debug("Found parameter '" + name + "' with '" + args[i + 1] + "'");
                result = args[i + 1];
                break;
            }
        }
        return result;
    }

    /**
     * Check the directory structure to ensure that repast runs properly
     */
    private void setupDirectoryStructure() {
        boolean success = true;

        File userDir = new File(System.getProperty("user.dir"));

        File mapsFolder = new File(userDir.getAbsolutePath() + "/maps");
        if (!mapsFolder.exists()) {
            LOGGER.debug("exporting maps to: " + mapsFolder.getPath());
            success &= mapsFolder.mkdir();
            success &= FileUtils.copyResourcesRecursively(this.getClass().getResource("/setup/maps"), userDir);
        }
        File scenarioFolder = new File(userDir.getAbsolutePath() + "/BW4T.rs");
        if (!scenarioFolder.exists()) {
            LOGGER.debug("exporting scenario to: " + scenarioFolder.getPath());
            success &= scenarioFolder.mkdir();
            success &= FileUtils.copyResourcesRecursively(this.getClass().getResource("/setup/BW4T.rs"), userDir);
        }
        File logFolder = new File(userDir.getAbsolutePath() + "/log");
        if (!logFolder.exists()) {
            LOGGER.debug("creating log folder at: " + logFolder.getPath());
            success &= logFolder.mkdir();
        }
        File binFolder = new File(userDir.getAbsolutePath() + "/bin/nl/tudelft");
        if (!binFolder.exists()) {
            LOGGER.debug("exporting server classes to: " + binFolder.getPath());
            success &= binFolder.mkdirs();
            success &= FileUtils.copyResourcesRecursively(this.getClass().getResource("/nl/tudelft/bw4t"), binFolder);
        }
        if (!success) {
            throw new LauncherException("Could not generate neccessary directories and files,"
                    + " please copy the following folders from the jar file "
                    + "(some are in the setup and bin folder): /logs , /BW4T.rs , /maps and /nl");
        }
    }

    /**
     * setup the environment.
     */
    private void setupEnvironment() {
            try {
                environment = new BW4TEnvironment(setupRemoteServer(), paramScenario, paramMap, paramGUI, paramKey, paramCollision, paramDrawPaths);
            } catch (ManagementException | IOException | ScenarioLoadException | JAXBException e) {
                LOGGER.fatal("Failed to setup the BW4T Environment.");
                throw new LauncherException("failed to setup the bw4t environment", e);
            }
        
    }

    /**
     * Setup the factories used by the system.
     */
    private void setupFactories() {
        entityFactory = new DefaultEntityFactory();
    }

    /**
     * start the environment, repast and all.
     */
    private void startEnvironment() {
        try {
        environment.launchAll();
        } catch (ManagementException | IOException | ScenarioLoadException | JAXBException e) {
            LOGGER.fatal("Failed to start the BW4T Environment.");
            throw new LauncherException("failed to start the bw4t environment", e);
        }
    }

    /**
     * Setup the rpc server so clients can connect to this environment.
     * 
     * @return a server bound to the given ip and port
     */
    public final BW4TServer setupRemoteServer() {
        try {
            return new BW4TServer(paramServerIp, paramServerPort, paramServerMsg);
        } catch (RemoteException | MalformedURLException e) {
            LOGGER.fatal("Failed to start the RPC Server.");
            throw new LauncherException("failed to start the rpc server", e);
        }
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    /**
     * The main method to start up the bw4t server.
     * 
     * @param args
     *            the command line arguments transmitted by the operating systems
     */
    public static void main(final String[] args) {
        new Launcher(args);
    }

    /**
     * Get the environment that is at the core of this bw4t server.
     * 
     * @return the environment
     */
    public BW4TEnvironment getEnvironment() {
        return environment;
    }

    /**
     * get the instance of the launcher that started the bw4t server.
     * 
     * @return the instance
     */
    public static Launcher getInstance() {
        return instance;
    }

    /**
     * set the launcher instance to the global field
     * 
     * @param inst
     *            the instance to be used to startup the environment
     */
    private static void setInstance(Launcher inst) {
        Launcher.instance = inst;
    }

}
