package nl.tudelft.bw4t.util;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.zone.Zone;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 * Path planner that uses the Zones.
 * 
 * @author W.Pasman 22aug2011
 * 
 */
public class PathPlanner {
	private PathPlanner() {
	}

	/**
	 * find a path from start to end point given a graph of all Zones.
	 * 
	 * @param allZones
	 *            set of all Zones in the map, or at least the ones involved in
	 *            the search
	 * @param start
	 *            is strat Zone
	 * @param end
	 *            is target Zone
	 * @return List of subsequent Zones from start to end, or null if no path
	 *         exists.
	 */
	public static List<Zone> findPath(List<Zone> allZones, Zone start, Zone end) {
		SimpleWeightedGraph<Zone, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Zone, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);
		for (Zone p : allZones) {
			graph.addVertex(p);
		}

		for (Zone p1 : allZones) {
			for (Zone p2 : p1.getNeighbours()) {
				DefaultWeightedEdge edge = graph.addEdge(p1, p2);
				/**
				 * If we get edge==null, the edge already has been added.
				 */
				if (edge != null) {
					graph.setEdgeWeight(edge, p1.distanceTo(p2));
				}
			}
		}

		List<DefaultWeightedEdge> edgeList = DijkstraShortestPath
				.findPathBetween(graph, start, end);
		if (edgeList == null) {
			return null;
		}

		List<Zone> path = new ArrayList<Zone>();
		Zone current = start;
		path.add(current);
		// Add each path node, but also check for the order of the edges so that
		// the correct point (source or target) is added.
		for (DefaultWeightedEdge edge : edgeList) {
			if (graph.getEdgeSource(edge).equals(current)) {
				current = graph.getEdgeTarget(edge);
				path.add(current);
			} else if (graph.getEdgeTarget(edge).equals(current)) {
				current = graph.getEdgeSource(edge);
				path.add(current);
			}
		}

		return path;
	}

}
