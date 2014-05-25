package nl.tudelft.bw4t.client.environment;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import eis.AgentListener;
import eis.EnvironmentListener;

public class RemoteEnvironmentData {
	private BW4TClient client;
	private List<EnvironmentListener> environmentListeners;
	private Map<String, BW4TClientGUI> entityToGUI;
	private boolean connectedToGoal;
	/**
	 * This is a list of locally registered agents.
	 * <p/>
	 * Only locally registered agents can act and be associated with entities.
	 */
	private List<String> localAgents;
	/**
	 * Stores for each agent (represented by a string) a set of listeners.
	 */
	private Map<String, HashSet<AgentListener>> agentsToAgentListeners;

	public RemoteEnvironmentData(BW4TClient client,
			List<EnvironmentListener> environmentListeners,
			Map<String, BW4TClientGUI> entityToGUI, boolean connectedToGoal,
			List<String> localAgents,
			Map<String, HashSet<AgentListener>> agentsToAgentListeners) {
		this.client = client;
		this.environmentListeners = environmentListeners;
		this.entityToGUI = entityToGUI;
		this.connectedToGoal = connectedToGoal;
		this.localAgents = localAgents;
		this.agentsToAgentListeners = agentsToAgentListeners;
	}

	public BW4TClient getClient() {
		return client;
	}

	public void setClient(BW4TClient client) {
		this.client = client;
	}

	public List<EnvironmentListener> getEnvironmentListeners() {
		return environmentListeners;
	}

	public void setEnvironmentListeners(
			List<EnvironmentListener> environmentListeners) {
		this.environmentListeners = environmentListeners;
	}

	public Map<String, BW4TClientGUI> getEntityToGUI() {
		return entityToGUI;
	}

	public void setEntityToGUI(Map<String, BW4TClientGUI> entityToGUI) {
		this.entityToGUI = entityToGUI;
	}

	public boolean isConnectedToGoal() {
		return connectedToGoal;
	}

	public void setConnectedToGoal(boolean connectedToGoal) {
		this.connectedToGoal = connectedToGoal;
	}

	public List<String> getLocalAgents() {
		return localAgents;
	}

	public void setLocalAgents(List<String> localAgents) {
		this.localAgents = localAgents;
	}

	public Map<String, HashSet<AgentListener>> getAgentsToAgentListeners() {
		return agentsToAgentListeners;
	}

	public void setAgentsToAgentListeners(
			Map<String, HashSet<AgentListener>> agentsToAgentListeners) {
		this.agentsToAgentListeners = agentsToAgentListeners;
	}
}