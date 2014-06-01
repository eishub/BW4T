package nl.tudelft.bw4t.server;

import java.util.List;

import nl.tudelft.bw4t.scenariogui.BotConfig;

class ClientInfo {
    private int numberOfHumans = 0;
    private int numberOfAgents = 0;
    private List<BotConfig> requestedBots;

    public ClientInfo(int reqHuman, int reqAgent) {
        assert reqHuman >= 0;
        assert reqAgent >= 0;
        this.numberOfAgents = reqAgent;
        this.numberOfHumans = reqHuman;
    }

    public ClientInfo(List<BotConfig> reqBots) {
        assert reqBots != null;
        this.requestedBots = reqBots;
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
	
	/**
	 * Count the total number of bots requested.
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
