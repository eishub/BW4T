package nl.tudelft.bw4t.server;

class ClientInfo {
	private int numberOfHumans = 0;
	private int numberOfAgents = 0;

	public ClientInfo(int reqHuman, int reqAgent) {
		assert reqHuman >= 0;
		assert reqAgent >= 0;
		this.numberOfAgents = reqAgent;
		this.numberOfHumans = reqHuman;
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
}
