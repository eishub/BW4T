package nl.tudelft.bw4t.server.model;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.server.model.zone.Zone;
import nl.tudelft.bw4t.server.repast.MapLoader;
import nl.tudelft.bw4t.server.util.GraphHelper;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

/**
 * A data structure containing both the map and the repast context that is active within the current server.
 */
public class BW4TServerMap {
    // [start] Constructors

    /**
     * Create a new server map with the given {@link NewMap}.
     * 
     * @param map
     *            the map
     */
    public BW4TServerMap(NewMap map) {
        this.setMap(map);
    }

    /**
     * Create a new server map with the given {@link NewMap} and repast {@link Context}.
     * 
     * @param map
     *            the map
     * @param context
     *            the repast context
     */
    public BW4TServerMap(NewMap map, Context<Object> context) {
        this(map);
        this.setContext(context);
    }

    // [end]

    // [start] Map and Context
    private Context<Object> context;
    private NewMap map;

    public NewMap getMap() {
        return map;
    }

    /**
     * @return is there a map currently present
     */
    public boolean hasMap() {
        return map != null;
    }

    /**
     * Set the new {@link NewMap} for this server map, will notify attached listeners.
     * 
     * @param map
     *            the new {@link NewMap}
     */
    public void setMap(NewMap map) {
        if (this.map == map) {
            return;
        }
        this.map = map;
        notifyMapChange();
    }

    public Context<Object> getContext() {
        return context;
    }

    /**
     * @return is there a repast context currently present
     */
    public boolean hasContext() {
        return context != null;
    }

    /**
     * Set a new repast {@link Context} for this server map, will notify attached listeners.
     * 
     * @param context
     *            the new repast {@link Context}
     */
    public void setContext(Context<Object> context) {
        if (this.context == context) {
            return;
        }
        this.context = context;
        this.continuousSpace = null;
        this.gridSpace = null;
        this.graph = null;
        notifyContextChange();
    }

    // [end]

    // [start] Repast Context derived functions
    private ContinuousSpace<Object> continuousSpace;
    private Grid<Object> gridSpace;
    private WeightedGraph<NdPoint, DefaultWeightedEdge> graph;

    /**
     * Get the contiuous space for the current repast {@link Context}.
     * 
     * @return the {@link ContinuousSpace}
     */
    public ContinuousSpace<Object> getContinuousSpace() {
        if (continuousSpace == null && hasContext()) {
            continuousSpace = (ContinuousSpace<Object>) getContext().getProjection(MapLoader.PROJECTION_ID);
        }
        return continuousSpace;
    }

    /**
     * Get the grid space for the current repast {@link Context}.
     * 
     * @return the {@link Grid}
     */
    public Grid<Object> getGridSpace() {
        if (gridSpace == null && hasContext()) {
            gridSpace = (Grid<Object>) getContext().getProjection(MapLoader.GRID_PROJECTION_ID);
        }
        return gridSpace;
    }

    /**
     * Get the graph of all connected zones in the map.
     * 
     * @return the graph
     */
    public WeightedGraph<NdPoint, DefaultWeightedEdge> getZoneGraph() {
        if (graph == null && hasContext()) {
            graph = GraphHelper.generateZoneGraph(getObjectsFromContext(Zone.class));
        }
        return graph;
    }

    /**
     * Get a set of objects of type clazz from the repast {@link Context}.
     * 
     * @see Context#getObjects(Class)
     * @param clazz
     *            the class of the objects to be found
     * @param <T>
     *            the type of object to be found
     * @return a {@link Set} of the found objects
     */
    @SuppressWarnings("unchecked")
    public <T> Set<T> getObjectsFromContext(Class<T> clazz) {
        Set<T> elements = new HashSet<>();
        if (!hasContext()) {
            return elements;
        }
        for (Object t : getContext().getObjects(clazz)) {
            elements.add((T) t);
        }
        return elements;
    }

    // [end]

    // [start] Change Listeners

    private Set<BW4TServerMapListerner> listeners = new HashSet<>();

    /**
     * Attach the given object so it get notified whenever there are changes to the {@link NewMap} or repast
     * {@link Context}.
     * 
     * @param e
     *            the object to be notified
     */
    public void attachChangeListener(BW4TServerMapListerner e) {
        listeners.add(e);
    }

    /**
     * Remove the given object so it does not get notified of changes any more.
     * 
     * @param o
     *            the object to remove
     */
    public void removeChangeListener(Object o) {
        listeners.remove(o);
    }

    /**
     * Notify all attached listeners that the {@link NewMap} changed.
     */
    private void notifyMapChange() {
        for (BW4TServerMapListerner listener : listeners) {
            listener.mapChange(this);
        }
    }

    /**
     * Notify all attached listeners that the repast {@link Context} changed.
     */
    private void notifyContextChange() {
        for (BW4TServerMapListerner listener : listeners) {
            listener.contextChange(this);
        }
    }

    // [end]
}
