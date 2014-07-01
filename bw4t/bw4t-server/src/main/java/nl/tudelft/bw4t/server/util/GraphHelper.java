package nl.tudelft.bw4t.server.util;

import java.util.Collection;

import nl.tudelft.bw4t.server.model.zone.Zone;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import repast.simphony.space.continuous.NdPoint;

/**
 * A Helper class for various graph related tasks.
 */
public final class GraphHelper {
    private GraphHelper() {
    }

    /**
     * Get the opposite node across a edge in a {@link WeightedGraph}.
     * 
     * @param graph
     *            the weighted graph containing the edge and node
     * @param edge
     *            the edge along which to find the opposite
     * @param current
     *            the node to which to find the opposite along the edge
     * @param <TNode>
     *            the type of the nodes in this graph
     * @param <TEdge>
     *            the type of the edges in this graph
     * @return the node opposite to the current one, null if the current is not on the given edge
     */
    public static <TNode, TEdge> TNode getOpposite(WeightedGraph<TNode, TEdge> graph, TEdge edge, TNode current) {
        TNode opposite = null;
        final TNode source = graph.getEdgeSource(edge);
        final TNode target = graph.getEdgeTarget(edge);
        if (source != null && source.equals(current)) {
            opposite = target;
        } else if (target != null && target.equals(current)) {
            opposite = source;
        }
        return opposite;
    }

    public static WeightedGraph<NdPoint, DefaultWeightedEdge> generateZoneGraph(Collection<Zone> allZones) {
        WeightedGraph<NdPoint, DefaultWeightedEdge> graph = new SimpleWeightedGraph<NdPoint, DefaultWeightedEdge>(
                DefaultWeightedEdge.class);
        for (Zone p : allZones) {
            graph.addVertex(p.getLocation());
        }
        for (Zone p1 : allZones) {
            for (Zone p2 : p1.getNeighbours()) {
                DefaultWeightedEdge edge = graph.addEdge(p1.getLocation(), p2.getLocation());
                /**
                 * If we get edge==null, the edge already has been added.
                 */
                if (edge != null) {
                    graph.setEdgeWeight(edge, p1.distanceTo(p2));
                }
            }
        }
    
        return graph;
    }
}
