package nl.tudelft.bw4t.server.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import nl.tudelft.bw4t.server.model.zone.Zone;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import repast.simphony.space.continuous.NdPoint;

/**
 * Path planner that uses the Zones.
 */
public class PathPlanner {
    private PathPlanner() {
    }

    /**
     * find a path from start to end point given a graph of all Zones.
     *
     * @param allZones set of all Zones in the map, or at least the ones involved in the search
     * @param start    is strat Zone
     * @param end      is target Zone
     * @return List of subsequent Zones from start to end, or null if no path exists.
     */
    public static List<Zone> findPath(List<Zone> allZones, Zone start, Zone end) {
        SimpleWeightedGraph<Zone, DefaultWeightedEdge> graph = generateZoneGraph(allZones);

        List<DefaultWeightedEdge> edgeList = DijkstraShortestPath.findPathBetween(graph, start, end);
        if (edgeList == null) {
            return new ArrayList<Zone>();
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
            }
            else if (graph.getEdgeTarget(edge).equals(current)) {
                current = graph.getEdgeSource(edge);
                path.add(current);
            }
        }

        return path;
    }


    /**
     * find a path from start to end point given a list of obstacles.
     *
     * @param obstacles set of all obstacles involved in the search.
     * @return List of subsequent NdPoints from start to finish.
     */
    public static List<NdPoint> findPath(List<Zone> allZones, List<BoundedMoveableObject> obstacles,
                                         NdPoint startPoint, NdPoint end, int botSize) {
        SimpleWeightedGraph<NdPoint, DefaultWeightedEdge> graph = generateNdPointGraph(startPoint, end, allZones,
                obstacles, botSize);


        List<DefaultWeightedEdge> edgeList = DijkstraShortestPath.findPathBetween(graph, startPoint, end);
        if (edgeList == null) {
            return new ArrayList<NdPoint>();
        }

        List<NdPoint> path = new ArrayList<NdPoint>();
        NdPoint current = startPoint;
        path.add(current);
        // Add each path node, but also check for the order of the edges so that
        // the correct point (source or target) is added.
        for (DefaultWeightedEdge edge : edgeList) {
            if (graph.getEdgeSource(edge).equals(current)) {
                current = graph.getEdgeTarget(edge);
                path.add(current);
            }
            else if (graph.getEdgeTarget(edge).equals(current)) {
                current = graph.getEdgeSource(edge);
                path.add(current);
            }
        }
        return path;
    }

    private static List<NdPoint> returnVacantPoints(List<Zone> zones, List<BoundedMoveableObject> obstacles,
                                                    int botSize) {
        // First generate the points occupied by the obstacles.
        List<NdPoint> obstaclePoints = new ArrayList<NdPoint>();

        for (BoundedMoveableObject obstacle : obstacles) {
            // Navigate around obstacles with a margin of 1 unit.
            obstaclePoints.addAll(obstacle.getPointsOccupiedByObject(0));
        }

        // Now for the zone points.
        List<NdPoint> zonePoints = new ArrayList<NdPoint>();
        for (Zone zone : zones) {
            // -botSize to allow the bot to move alone walls, etc.
            zonePoints.addAll(zone.getPointsOccupiedByObject(-botSize));
        }

        // Remove all obstacles points from the list. The remaining list if the one we'll use for pathfinding.
        zonePoints.removeAll(obstaclePoints);

        // Remove duplicates by copying the collection to a set, then recreating the list from
        // the duplicate-less set.

        zonePoints = new ArrayList<NdPoint>(new HashSet<NdPoint>(zonePoints));

        return zonePoints;
    }

    private static SimpleWeightedGraph<NdPoint, DefaultWeightedEdge> generateNdPointGraph(
            NdPoint start, NdPoint end, List<Zone> allZones, List<BoundedMoveableObject> obstacles, int botSize) {

        SimpleWeightedGraph<NdPoint, DefaultWeightedEdge> graph = new SimpleWeightedGraph<NdPoint, DefaultWeightedEdge>(
                DefaultWeightedEdge.class);


        List<NdPoint> vertices = returnVacantPoints(allZones, obstacles, botSize);

        // Add all the vertices.
        graph.addVertex(start);
        graph.addVertex(end);
        for (NdPoint p : vertices) {
            graph.addVertex(p);
        }

        /*
            Create the edges
            Each vertex can have 4 edges in GRID space. Since we won't be considering
            diagonal travel.
         */
        for (NdPoint vertex : vertices) {
            NdPoint[] neighbours = {
                    new NdPoint(vertex.getX(), vertex.getY() + 1),
                    new NdPoint(vertex.getX(), vertex.getY() - 1),
                    new NdPoint(vertex.getX() - 1, vertex.getY()),
                    new NdPoint(vertex.getX() + 1, vertex.getY())
            };

            for (NdPoint neighbour : neighbours) {
                if (vertices.contains(neighbour)) {
                    // Default edge distance is 1. So we're good.
                    graph.addEdge(vertex, neighbour);
                }
            }

        }

        return graph;
    }

    private static SimpleWeightedGraph<Zone, DefaultWeightedEdge> generateZoneGraph(List<Zone> allZones) {
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

        return graph;
    }

}
