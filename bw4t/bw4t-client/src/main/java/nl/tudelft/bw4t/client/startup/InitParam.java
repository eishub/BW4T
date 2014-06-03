package nl.tudelft.bw4t.client.startup;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * Available init parameters and default values for {@link BW4TEnvironment}.
 * 
 * @author W.Pasman 20mar13
 */
public enum InitParam {
    /** Our IP. Passed to server so server is able to find us with this IP. */
    CLIENTIP("localhost"),
    /** The port that we use. */
    CLIENTPORT("2000"),
    /** The IP address of the server. */
    SERVERIP("localhost"),
    /** The Port used by the server. */
    SERVERPORT("8000"),
    /** Number of agents that we have. */
    AGENTCOUNT("0"),
    /** Launch GUI for entities? (check is this only for humans?) */
    LAUNCHGUI("true"),
    /** Number of human bots (is this to connect GUIs?) */
    HUMANCOUNT("1"),
    /** The java agent class to load when new entities appear. */
    AGENTCLASS("nl.tudelft.bw4t.agent.BW4TAgent"),
    /** Are we connected with GOAL? */
    GOAL("false"),
    /** The key we should try to use to kill the remote server. */
    KILL("");
    /** Default value of the this InitParam */
    private String defaultvalue;

    /** Store the program-wide parameters given to the {@link RemoteEnvironment}. */
    private static Map<String, Parameter> parameters = null;

    /**
     * @param def
     *            - The default value of the init parameter.
     */
    InitParam(String def) {
        defaultvalue = def;
    }

    public String getDefaultValue() {
        return defaultvalue;
    }

    /**
     * {@link #name()} but in lower case.
     * 
     * @return Name in lower case.
     */
    public String nameLower() {
        return this.name().toLowerCase();
    }

    /**
     * Retrieve the value of this InitParam  EnumType.
     * 
     * @return The String value for this InitParam EnumType.
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
     *            - The Map of parameters given to the {@link RemoteEnvironment}
     */
    public static void setParameters(Map<String, Parameter> params) {
        parameters = params;
    }

    /**
     * Get all program-wide parameters.
     * 
     * @return The Map of parameters given to the {@link RemoteEnvironment}
     */
    public static Map<String, Parameter> getParameters() {
        return parameters;
    }

    /**
     * Filter out all the parameters for the server from the given set of
     * parameters. We do this by removing all parameters that we can handle as
     * client; the remaining ones must be server parameters.
     * 
     * @return A Map containing the Parameters used by for the server.
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
     *            - The name of the parameter to be read.
     * @return The parameter with the given name or {@code null} if not found.
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
