package nl.tudelft.bw4t.scenariogui;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import nl.tudelft.bw4t.util.XMLManager;

/**
 * This class holds the possible options that can be specified in the Scenario
 * GUI and is meant to be directly convertible to XML and constructible from
 * XML.
 *
 * @version 0.1
 * @since 12-05-2014
 */
@XmlRootElement
public class BW4TClientConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private String outputFile;
    private String clientIp = DefaultConfigurationValues.DEFAULT_CLIENT_IP.getValue();
    private int clientPort = DefaultConfigurationValues.DEFAULT_CLIENT_PORT.getIntValue();
    private String serverIp = DefaultConfigurationValues.DEFAULT_SERVER_IP.getValue();
    private int serverPort = DefaultConfigurationValues.DEFAULT_SERVER_PORT.getIntValue();
    private boolean launchGui = DefaultConfigurationValues.USE_GUI.getBooleanValue();
    private boolean visualizePaths = DefaultConfigurationValues.VISUALIZE_PATHS.getBooleanValue();
    private boolean useGoal = DefaultConfigurationValues.USE_GOAL.getBooleanValue();
    private boolean collisionEnabled = DefaultConfigurationValues.COLLISIONS_ENABLED.getBooleanValue();
    private String mapFile = "";

    /**
     * The XML element wrapper for the list of bots.
     */
    @XmlElementWrapper(name = "bots")
    @XmlElement(name = "bot")
    private List<BotConfig> bots = new ArrayList<BotConfig>();

    private transient List<BotConfig> oldBots = new ArrayList<BotConfig>();

    /**
     * The XML element wrapper for the list of epartners.
     */
    @XmlElementWrapper(name = "epartners")
    @XmlElement(name = "epartner")
    private List<EPartnerConfig> epartners = new ArrayList<EPartnerConfig>();

    private transient List<EPartnerConfig> oldEpartners = new ArrayList<EPartnerConfig>();

    /**
     * An empty <code>BW4TClientConfig</code> object.
     */
    public BW4TClientConfig() {

    }

    /**
     * Construct Java Object from XML file.
     *
     * @param inputFile The file location of the XML-file
     * @return The BW4TClientConfig object
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a
     *                               specified pathname has failed.
     * @throws JAXBException         Root exception class for all JAXB exceptions.
     */
    public static BW4TClientConfig fromXML(final String inputFile)
            throws FileNotFoundException, JAXBException {
        return (BW4TClientConfig) XMLManager.fromXML(inputFile,
                BW4TClientConfig.class);
    }

    /**
     * Converts Java Object into XML file.
     *
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a
     *                               specified pathname has failed.
     * @throws JAXBException         Root exception class for all JAXB exceptions.
     */
    public final void toXML() throws FileNotFoundException, JAXBException {
        XMLManager.toXML(outputFile, this);
    }

    @XmlTransient
    public final String getFileLocation() {
        return outputFile;
    }

    public final void setFileLocation(final String newFileLocation) {
        this.outputFile = newFileLocation;
    }

    public final String getClientIp() {
        return clientIp;
    }

    @XmlElement
    public final void setClientIp(final String newClientIp) {
        this.clientIp = newClientIp;
    }

    public final int getClientPort() {
        return clientPort;
    }

    @XmlElement
    public final void setClientPort(final int newClientPort) {
        this.clientPort = newClientPort;
    }

    public final String getServerIp() {
        return serverIp;
    }

    @XmlElement
    public final void setServerIp(final String newServerIp) {
        this.serverIp = newServerIp;
    }

    public final int getServerPort() {
        return serverPort;
    }

    @XmlElement
    public final void setServerPort(final int newServerPort) {
        this.serverPort = newServerPort;
    }

    public final boolean isLaunchGui() {
        return launchGui;
    }

    @XmlElement
    public final void setLaunchGui(final boolean newLaunchGui) {
        this.launchGui = newLaunchGui;
    }

    public final boolean isVisualizePaths() {
        return visualizePaths;
    }

    @XmlElement
    public final void setVisualizePaths(final boolean newVisualizePaths) {
        this.visualizePaths = newVisualizePaths;
    }

    public final boolean isUseGoal() {
        return useGoal;
    }

    @XmlElement
    public final void setUseGoal(final boolean newUseGoal) {
        this.useGoal = newUseGoal;
    }

    public final String getMapFile() {
        return mapFile;
    }

    @XmlElement
    public final void setMapFile(final String newMapFile) {
        this.mapFile = newMapFile;
    }

    /**
     * Add a bot to the configuration file.
     *
     * @param theBot The bot that is to be added.
     */
    public void addBot(BotConfig theBot) {
        bots.add(theBot);
    }

    /**
     * Remove a bot from the configuration file.
     *
     * @param theBot The bot that is to be removed.
     */
    public void removeBot(BotConfig theBot) {
        bots.remove(theBot);
    }

    public List<BotConfig> getBots() {
        return bots;
    }

    public List<BotConfig> getOldBots() {
        return oldBots;
    }

    public BotConfig getBot(int index) {
        return bots.get(index);
    }

    /**
     * Overwrites the bot config at this index in the bot list
     * with the new bot config.
     *
     * @param index        The index of the bot config to overwrite.
     * @param newBotConfig The new bot config.
     */
    public void setBot(int index, BotConfig newBotConfig) {
        bots.set(index, newBotConfig);
    }

    /**
     * Updates the bot list with the new bots.
     */
    public void updateOldBotConfigs() {
        oldBots = new ArrayList<BotConfig>(bots);
    }

    /**
     * Returns the amount of bots in the bot list.
     *
     * @return The amount of bots in the bot list.
     */
    public int getAmountBot() {
        int botCount = 0;
        for (int i = 0; i < bots.size(); i++) {
            botCount = botCount + bots.get(i).getBotAmount();
        }
        return botCount;
    }

    /**
     * Compares the BotConfig lists.
     *
     * @param config The BotConfig list to be compared.
     * @return If the BotConfigs lists are equal.
     */
    public boolean compareBotConfigs(List<BotConfig> config) {
        if (bots.size() != config.size()) {
            return false;
        }

        for (int i = 0; i < bots.size(); i++) {
            if (!bots.get(i).bcToString()
                    .equals(config.get(i).bcToString())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Add an epartner to the configuration file.
     *
     * @param theEpartner The epartner that is to be added.
     */
    public void addEpartner(EPartnerConfig theEpartner) {
        epartners.add(theEpartner);
    }

    /**
     * Remove an epartner from the configuration file.
     *
     * @param theEpartner The epartner that is to be removed.
     */
    public void removeEpartner(EPartnerConfig theEpartner) {
        epartners.remove(theEpartner);
    }


    public List<EPartnerConfig> getEpartners() {
        return epartners;
    }

    public List<EPartnerConfig> getOldEpartners() {
        return oldEpartners;
    }

    public EPartnerConfig getEpartner(int index) {
        return epartners.get(index);
    }

    /**
     * Updates the EpartnerConfig list.
     */
    public void updateOldEpartnerConfigs() {
        oldEpartners = new ArrayList<EPartnerConfig>(epartners);
    }

    /**
     * Returns the amount of epartners in the epartner list.
     *
     * @return The amount of epartners in the epartner list.
     */
    public int getAmountEPartner() {
        int epartnerCount = 0;
        for (int i = 0; i < epartners.size(); i++) {
            epartnerCount = epartnerCount + epartners.get(i).getEpartnerAmount();
        }
        return epartnerCount;
    }

    /**
     * Compares the EpartnerConfig lists.
     *
     * @param config The EpartnerConfig list to be compared.
     * @return If the EpartnerConfigs lists are equal.
     */
    public boolean compareEpartnerConfigs(List<EPartnerConfig> config) {
        if (epartners.size() != config.size()) {
            return false;
        }

        for (int i = 0; i < epartners.size(); i++) {
            if (!epartners.get(i).ecToString()
                    .equals(config.get(i).ecToString())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Clears the bot and e-partner list
     * and the history that came with it.
     */
    public void clearBotsAndEpartners() {
        getBots().clear();
        getEpartners().clear();

        // Delete the history as well.
        updateOldBotConfigs();
        updateOldEpartnerConfigs();
    }

    public boolean isCollisionEnabled() {
        return collisionEnabled;
    }

    @XmlElement
    public void setCollisionEnabled(boolean state) {
        collisionEnabled = state;
    }

    @Override
    public BW4TClientConfig clone() {
        try {
            return (BW4TClientConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
