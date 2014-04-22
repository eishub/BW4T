package nl.tudelft.bw4t;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.server.BW4TEnvironment;
import nl.tudelft.bw4t.server.Stepper;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunListener;

/**
 * This builds the BW4T environment, by calling
 * {@link MapLoader#loadMap(String, Context)}. It also listens to runmode
 * changes in the Repast environment. Finally the created new {@link Context} is
 * passed to the {@link BW4TEnvironment}.
 * <p>
 * BW4TBuilder is called by repast, as it is referred to as the dataLoader in
 * the xml files. This class then loads the map and starts up the renderer.
 * 
 * @author trens
 * @modified W.Pasman 13mar13 removed repast {@link RunListener} since we have
 *           our own {@link Stepper} that controls the run mode. Removed the
 *           singleton stuff.
 */
public class BW4TBuilder implements ContextBuilder<Object> {

	/** Matches the ID in context.xml and scenario.xml */
	private static final String CONTEXT_ID = "BW4T";

	public BW4TBuilder() {
	}

	@Override
	public Context<Object> build(Context<Object> context) {
		// System.out.println("Repast now loading map");
		context.setId(CONTEXT_ID);
		try {
			MapLoader.loadMap(BW4TEnvironment.getInstance().getMapLocation(),
					context);
			/*
			 * we call setContext() only after context has been prepared,
			 * because we need complete context for the rendering that will be
			 * started immediately after publishing.
			 */
			BW4TEnvironment.getInstance().setContext(context);
		} catch (IOException e) {
			System.out.println("failure to load the map "
					+ BW4TEnvironment.getInstance().getMapLocation());
			e.printStackTrace();
		} catch (JAXBException e) {
			System.out.println("failure to load the map "
					+ BW4TEnvironment.getInstance().getMapLocation());
			e.printStackTrace();
		}
		// System.out.println("Repast loadmap complete");
		return context;
	}
}
