package server;

import java.util.Collection;
import java.util.Map;

import eis.AgentListener;
import eis.EnvironmentInterfaceStandard;
import eis.EnvironmentListener;
import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.QueryException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eis.iilang.Percept;

/**
 * This is an example of an external library being implemented locally.
 * 
 */
public class EISEnvironment implements EnvironmentInterfaceStandard {

	@Override
	public void attachEnvironmentListener(EnvironmentListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void detachEnvironmentListener(EnvironmentListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attachAgentListener(String agent, AgentListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void detachAgentListener(String agent, AgentListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerAgent(String agent) throws AgentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterAgent(String agent) throws AgentException {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<String> getAgents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateEntity(String agent, String entity)
			throws RelationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void freeEntity(String entity) throws RelationException,
			EntityException {
		// TODO Auto-generated method stub

	}

	@Override
	public void freeAgent(String agent) throws RelationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void freePair(String agent, String entity) throws RelationException {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<String> getAssociatedEntities(String agent)
			throws AgentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getAssociatedAgents(String entity)
			throws EntityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getFreeEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType(String entity) throws EntityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Percept> performAction(String agent, Action action,
			String... entities) throws ActException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Collection<Percept>> getAllPercepts(String agent,
			String... entities) throws PerceiveException,
			NoEnvironmentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isStateTransitionValid(EnvironmentState oldState,
			EnvironmentState newState) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init(Map<String, Parameter> parameters)
			throws ManagementException {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() throws ManagementException {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() throws ManagementException {
		// TODO Auto-generated method stub

	}

	@Override
	public void kill() throws ManagementException {
		// TODO Auto-generated method stub

	}

	@Override
	public EnvironmentState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInitSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStartSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPauseSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isKillSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String requiredVersion() {
		/**
		 * Return the required EIS version to run the environment.
		 */
		return "0.3";
	}

	@Override
	public String queryProperty(String property) throws QueryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryEntityProperty(String entity, String property)
			throws QueryException {
		// TODO Auto-generated method stub
		return null;
	}

}
