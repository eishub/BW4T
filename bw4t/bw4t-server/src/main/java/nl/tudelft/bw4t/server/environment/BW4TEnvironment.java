package nl.tudelft.bw4t.server.environment;

import nl.tudelft.bw4t.map.NewMap;
import eis.eis2java.environment.AbstractEnvironment;
import eis.iilang.Action;

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
     * No idea... should still be implemented.
     * @param arg0
     *            No Idea.
     * @return No Idea.
     */
    @Override
    protected final boolean isSupportedByEnvironment(final Action arg0) {
        return false;
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

}
