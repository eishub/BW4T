package nl.tudelft.bw4t.server.environment;

import java.util.LinkedList;

import nl.tudelft.bw4t.BW4TBuilder;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.server.BW4TLogger;
import nl.tudelft.bw4t.server.RobotEntityInterface;
import eis.eis2java.environment.AbstractEnvironment;
import eis.exceptions.ActException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.Percept;

/**
 * The BW4TEnvironement, which includes and describes the whole
 * BW4TEnvironement.
 */
public class BW4TEnvironment extends AbstractEnvironment {

    /**
     * Serial, generated.
     */
    private static final long serialVersionUID = 7979540741572302941L;

    /**
     * Instance of the environment
     */
    private static BW4TEnvironment instance;
    
    /**
     * The map used in the environment
     */
    private static NewMap theMap;
    
    /**
     * Check if the map is fully loaded
     */
	private boolean mapFullyLoaded;
	/**
	 * Get the instance of this environment
	 * 
	 * @return the instance
	 */
	public static BW4TEnvironment getInstance() {
		if (instance == null) {
			throw new IllegalStateException(
					"BW4TEnvironment has not been initialized");
		}
		return instance;
	}
	
	/**
	 * get the current {@link NewMap}
	 * 
	 * @return the {@link NewMap}
	 */
	public static NewMap getMap() {
		return theMap;
	}
	
	/**
	 * Helper method to allow the server to call actions received from attached
	 * clients
	 * 
	 * @param entity
	 *            , the entity that should perform the action
	 * @param action
	 *            , the action that should be performed
	 * @return the percept received after performing the action
	 */
	public Percept performClientAction(String entity, Action action)
			throws ActException {
		BW4TLogger.getInstance().logAction(entity, action.toProlog());
		return performEntityAction(entity, action);
	}

	/**
	 * Helper method to allow the server to get all percepts for a connected
	 * client.
	 * <p>
	 * This function is synchronized to ensure that multiple calls are properly
	 * sequenced. This is important because getAllPercepts must 'lock' the
	 * environment and parallel calls would cause overlapping 'locks' taken at
	 * different moments in time.
	 * <p>
	 * Actually, locking the environment is done by copying the current location
	 * of the entity.
	 * <p>
	 * It seems that this new function is created because
	 * {@link AbstractEnvironment#getAllPerceptsFromEntity(String)} is final.
	 * 
	 * TODO: Cast to Robotinterface
	 * @param entity
	 *            , the entity for which all percepts should be gotten
	 * @return all percepts for the entity
	 */
	public synchronized LinkedList<Percept> getAllPerceptsFrom(String entity) {
		try {
			if (this.isMapFullyLoaded()) {
				((RobotEntityInterface) getEntity(entity))
						.initializePerceptionCycle();
				return getAllPerceptsFromEntity(entity);
			}
		} catch (PerceiveException e) {
			e.printStackTrace();
		} catch (NoEnvironmentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Check if the map was fully loaded. When this is true, all entities also
	 * have been processed and the environment is ready to run. Note that
	 * because of #2016 there may be an async between Repast and this
	 * BW4TEnvironment, causing this flag to remain true while repast has in
	 * fact stopped. We detect this only when the user turns 'on' the Repast
	 * environment again, in {@link BW4TBuilder#build()}.
	 * 
	 * @return
	 */
	public boolean isMapFullyLoaded() {
		return mapFullyLoaded;
	}
	
	
    /**
     * No idea... should still be implemented.
     * @param arg0
     *            No idea.
     * @param arg1
     *            No idea.
     * @return No Idea
     */
    @Override
    protected final boolean isSupportedByType(final Action arg0,
            final String arg1) {
        return false;
    }
    
    /**
	 * Check whether an action is supported by this environment.
	 * 
	 * @param arg0
	 *            , the action that should be checked
	 * @return true if there is an entity, a dropzone and sequence not yet
	 *         complete
	 */
	@Override
	public boolean isSupportedByEnvironment(Action arg0) {

		return !getEntities().isEmpty();
	}

}
