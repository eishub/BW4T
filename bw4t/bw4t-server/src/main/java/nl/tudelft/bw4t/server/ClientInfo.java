package nl.tudelft.bw4t.server;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;

class ClientInfo {
    private int numberOfHumans = 0;
    private int numberOfAgents = 0;
    private List<BotConfig> requestedBots = new ArrayList<>();
    private List<EPartnerConfig> requestedEPartners = new ArrayList<>();

    public ClientInfo(int reqHuman, int reqAgent) {
        assert reqHuman >= 0;
        assert reqAgent >= 0;
        this.numberOfAgents = reqAgent;
        this.numberOfHumans = reqHuman;
    }

    public ClientInfo(List<BotConfig> reqBots, List<EPartnerConfig> reqEP) {
        if (reqBots != null){
            this.requestedBots = reqBots;
        }
        if (reqEP != null) {
            this.requestedEPartners = reqEP;
        }
    }

    public int getNumberOfHumans() {
        return numberOfHumans;
    }

    public boolean decreaseNumberOfHumans() {
        if (numberOfHumans > 0) {
            this.numberOfHumans--;
            return true;
        }
        return false;
    }

    public int getNumberOfAgents() {
        return numberOfAgents;
    }

    public boolean decreaseNumberOfAgents() {
        if (this.numberOfAgents > 0) {
            this.numberOfAgents--;
            return true;
        }
        return false;
    }

    public List<BotConfig> getRequestedBots() {
        return requestedBots;
    }

    public List<EPartnerConfig> getRequestedEPartners() {
        return requestedEPartners;
    }

    /**
     * Count the total number of bots requested.
     * 
     * @return the number of bots
     */
    public int countNumberOfBotsRequested() {
        int count = 0;
        for (BotConfig bc : requestedBots) {
            count += bc.getBotAmount();
        }
        return count;
    }
}
