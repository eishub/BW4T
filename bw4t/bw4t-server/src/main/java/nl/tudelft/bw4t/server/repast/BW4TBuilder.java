package nl.tudelft.bw4t.server.repast;

import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.environment.Launcher;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;

/**
 * This builds the BW4T environment, by calling {@link MapLoader#loadMap(String, Context)}. It also listens to runmode
 * changes in the Repast environment. Finally the created new {@link Context} is passed to the {@link BW4TEnvironment}.
 * BW4TBuilder is called by repast, as it is referred to as the dataLoader in the xml files. This class then loads the
 * map and starts up the renderer.
 */
public class BW4TBuilder implements ContextBuilder<Object> {

    /**
     * Matches the ID in context.xml and scenario.xml
     */
    private static final String CONTEXT_ID = "BW4T";

    /**
     * Build the Context in which to run the repast simulation.
     */
    public BW4TBuilder() {
    }

    @Override
    public Context<Object> build(Context<Object> context) {
        context.setId(CONTEXT_ID);
        Launcher.getInstance().getEnvironment().getServerMap().setContext(context);
        return context;
    }
}
