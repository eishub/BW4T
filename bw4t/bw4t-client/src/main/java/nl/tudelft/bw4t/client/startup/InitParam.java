package nl.tudelft.bw4t.client.startup;

import eis.iilang.Identifier;
import eis.iilang.Parameter;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

/**
 * Available init parameters and default values for {@link BW4TEnvironment}.
 * 
 * @author W.Pasman 20mar13
 */
public enum InitParam {
    /**
     * our ip. Passed to server so server must be able to find us with this ip.
     */
    CLIENTIP("localhost"),
    /**
     * the port that we use.
     */
    CLIENTPORT("2000"),
    /**
     * The ip addres of server.
     */
    SERVERIP("localhost"),
    /**
     * port used by the server
     */
    SERVERPORT("8000"),
    /**
     * Number of agents that we have
     */

    AGENTCOUNT("0"),
    /**
     * Launch gui for entities? (check is this only for humans?)
     */
    LAUNCHGUI("true"),
    /**
     * Number of human bots (is this to connect GUIs?)
     */
    HUMANCOUNT("1"),
    /**
     * The java agent class to load when new entities appear.
     */
    AGENTCLASS("nl.tudelft.bw4t.agent.BW4TAgent"),
    /**
     * are we connected with GOAL?
     */
    GOAL("false"),
    /**
     * The key we should try to use to kill the remote server.
     */
    KILL(""),
    /**
     * The file from which the client reads the configuration.
     */
    CONFIGFILE("");
    
    private static final Logger LOGGER = Logger.getLogger(InitParam.class);
    
    private static boolean configFileLoaded = false;

    private String defaultvalue;

    /**
     * Store the program-wide parameters given to the {@link RemoteEnvironment}.
     */
    private static Map<String, Parameter> parameters = null;

    /**
     * @param def
     *            the default value of the init parameter
     */
    InitParam(String def) {
        defaultvalue = def;
    }

    public String getDefaultValue() {
        return defaultvalue;
    }

    /**
     * {@link #name()} but in lower case
     * 
     * @return name in lower case
     */
    public String nameLower() {
        return this.name().toLowerCase();
    }

    /**
     * Extract the value of a setting from the given Map.
     * 
     * @return the value of the parameter or the default value.
     */
    public String getValue() {
        Parameter param = getParameter(this.nameLower());
        if (param != null) {
            return ((Identifier) param).getValue();
        }
        return getDefaultValue();
    }

    /**
     * Set the program-wide parameters.
     * 
     * @param params
     *            the params given to the {@link RemoteEnvironment}
     */
    public static void setParameters(Map<String, Parameter> params) {
        parameters = params;
        
        final String cfile = CONFIGFILE.getValue();
        if (!cfile.isEmpty()) {
            LOGGER.info(String.format("Reading configuration file '%s'", cfile));
            try {
                ConfigFile.readConfigFile(cfile);
            } catch (JAXBException e) {
                LOGGER.error(String.format("Unable to load configuration file: '%s'", cfile), e);
            }
        }
    }

    /**
     * Get all program-wide parameters.
     * 
     * @return the params given to the {@link RemoteEnvironment}
     */
    public static Map<String, Parameter> getParameters() {
        return parameters;
    }

    /**
     * Filter out all the parameters for the server from the given set of
     * parameters. We do this by removing all parameters that we can handle as
     * client; the remaining ones must be server parameters.
     * 
     * @return parameters for the server
     */
    public static Map<String, Parameter> getServerParameters() {
        Map<String, Parameter> serverparams = new HashMap<String, Parameter>(
                getParameters());
        for (InitParam param : InitParam.values()) {
            serverparams.remove(param.nameLower());
        }
        return serverparams;
    }

    /**
     * Get the Parameter by name.
     * 
     * @param name
     *            the name of the parameter to be read
     * @return the parameter with the given name or null if not found
     */
    public static Parameter getParameter(String name) {
        if (parameters == null) {
            return null;
        }
        return parameters.get(name);
    }

    public void setDefaultValue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }
}
