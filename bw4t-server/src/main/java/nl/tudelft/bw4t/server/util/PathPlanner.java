package nl.tudelft.bw4t.server.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import nl.tudelft.bw4t.server.model.zone.DropZone;
import nl.tudelft.bw4t.server.model.zone.Room;
import nl.tudelft.bw4t.server.model.zone.Zone;
import nl.tudelft.bw4t.server.repast.MapLoader;

import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import repast.simphony.context.Context;
import repast.simphony.space.SpatialException;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

/**
 * Path planner that uses the Zones.
 */
public final class PathPlanner {
    
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
    public static List<NdPoint> findPath(Collection<Zone> allZones, Zone start, Zone end) {
        WeightedGraph<NdPoint, DefaultWeightedEdge> graph = GraphHelper.generateZoneGraph(allZones);

        return findPath(graph, start.getLocation(), end.getLocation());
    }


    /**
     * find a path from start to end point given a list of obstacles.
     *
     * @param obstacles set of all obstacles involved in the search.
     * @return List of subsequent NdPoints from start to finish.
     */
    public static List<NdPoint> findPath(List<Zone> allZones, List<BoundedMoveableObject> obstacles,
                                         NdPoint startPoint, NdPoint endPoint, int botSize) {
        SimpleWeightedGraph<NdPoint, DefaultWeightedEdge> graph = generateNdPointGraph(startPoint, allZones,
                obstacles, botSize);

        try {
            NdPoint start = findNearestRoundedPoint(startPoint, graph);
            NdPoint end  =  findNearestRoundedPoint(endPoint, graph);

            return findPath(graph, start, end);
        } catch (SpatialException ex) {
            return new ArrayList<NdPoint>();
        }

    }

    private static NdPoint findNearestRoundedPoint(NdPoint point, SimpleWeightedGraph<NdPoint, DefaultWeightedEdge> graph) throws SpatialException {
        NdPoint roundedPoint;
        Set<NdPoint> vertices = graph.vertexSet();

        // Find the floored point first.
        double x = Math.floor(point.getX());
        double y = Math.floor(point.getY());
        roundedPoint = new NdPoint(x, y);

        if(vertices.contains(roundedPoint)) {
            return roundedPoint;
        }

        // floor x, ceil y.
        y = Math.ceil(point.getY());
        roundedPoint = new NdPoint(x, y);

        if(vertices.contains(roundedPoint)) {
            return roundedPoint;
        }

        // ceil x, ceil y
        x = Math.ceil(point.getX());
        roundedPoint = new NdPoint(x, y);

        if(vertices.contains(roundedPoint)) {
            return roundedPoint;
        }

        // ceil x, floor y
        y = Math.floor(point.getY());
        roundedPoint = new NdPoint(x, y);

        if(vertices.contains(roundedPoint)) {
            return roundedPoint;
        }


        throw new SpatialException("Start vertex not on pathfinding graph.");
    }

    /**
     * Find the list of point to be traversed from the start to the end using the DijkstraShortestPath algorithm.
     * 
     * @param graph
     *            the graph upon which we need to find the path
     * @param start
     *            the start node from which to navigate
     * @param end
     *            the end node to which to navigate to
     * @return the succession of node to traverse from start to end, empty if no path was found
     */
    public static List<NdPoint> findPath(WeightedGraph<NdPoint, DefaultWeightedEdge> graph, NdPoint start,
            NdPoint end) {
        List<DefaultWeightedEdge> edgeList = DijkstraShortestPath.findPathBetween(graph, start, end);
        if (edgeList == null) {
            return new ArrayList<NdPoint>();
        }

        List<NdPoint> path = new ArrayList<NdPoint>();
        NdPoint current = start;
        path.add(current);
        // Add each path node, but also check for the order of the edges so that
        // the correct point (source or target) is added.
        for (DefaultWeightedEdge edge : edgeList) {
            current = GraphHelper.getOpposite(graph, edge, current);
            if (current != null) {
                path.add(current);
            }
        }
        return path;
    }

    private static List<NdPoint> returnVacantPoints(Collection<Zone> zones, Collection<BoundedMoveableObject> obstacles,
                                                    int botSize) {
        // First generate the points occupied by the obstacles.
        List<NdPoint> obstaclePoints = new ArrayList<NdPoint>();

        for (BoundedMoveableObject obstacle : obstacles) {
            // Navigate around obstacles with a margin of 1 unit.
            obstaclePoints.addAll(obstacle.getPointsOccupiedByObject(2));
        }

        // Now for the zone points.
        List<NdPoint> zonePoints = new ArrayList<NdPoint>();
        for (Zone zone : zones) {
            // -botSize to allow the bot to move alone walls, etc.
            zonePoints.addAll(zone.getPointsOccupiedByObject(0));
        }

        // Remove all obstacles points from the list. The remaining list if the one we'll use for pathfinding.
        zonePoints.removeAll(obstaclePoints);

        // Remove duplicates by copying the collection to a set, then recreating the list from
        // the duplicate-less set.

        zonePoints = new ArrayList<NdPoint>(new HashSet<NdPoint>(zonePoints));

        return zonePoints;
    }

    private static SimpleWeightedGraph<NdPoint, DefaultWeightedEdge> generateNdPointGraph(NdPoint start,
            Collection<Zone> allZones, Collection<BoundedMoveableObject> obstacles, int botSize) {

        SimpleWeightedGraph<NdPoint, DefaultWeightedEdge> graph = new SimpleWeightedGraph<NdPoint, DefaultWeightedEdge>(
                DefaultWeightedEdge.class);


        List<NdPoint> vertices = returnVacantPoints(allZones, obstacles, botSize / 2);
        vertices.add(start);
        // Margin is botsize / 2, since the points move under the center of the bot.
        sanitizeVertices(allZones, vertices, obstacles, botSize / 2);


        // Add all the vertices.
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

    private static void sanitizeVertices(Collection<Zone> zones, Collection<NdPoint> vertices,
                                         Collection<BoundedMoveableObject> obstacles, int margin) {
        Context<Object> context = BW4TEnvironment.getInstance().getContext();

        ContinuousSpace<Object> space = (ContinuousSpace<Object>) context.getProjection(MapLoader.PROJECTION_ID);
        double width = space.getDimensions().getWidth();
        double height = space.getDimensions().getHeight();

        Set<NdPoint> invalidPoints = new HashSet<NdPoint>();

        for(Zone zone : zones) {
            if(zone instanceof Room || zone instanceof DropZone) {
                // Disallow a band 'margin' thick around rooms.
                double zx = zone.getBoundingBox().getX();
                double zy = zone.getBoundingBox().getY();

                double zwidth = zone.getBoundingBox().getWidth();
                double zheight = zone.getBoundingBox().getHeight();

                // Top bar
                invalidPoints.addAll(
                        generateBlock(zx - margin, zy + margin, zx + zwidth + margin, zy)
                );
                // Lower bar
                invalidPoints.addAll(
                        generateBlock(zx - margin, zy - zheight, zx + zwidth + margin, zy - zheight - margin)
                );
                // Left bar
                invalidPoints.addAll(
                        generateBlock(zx - margin, zy, zx, zy - zheight)
                );
                // Right bar
                invalidPoints.addAll(
                        generateBlock(zx + zwidth, zy, zx + zwidth + margin, zy - zheight)
                );
            }
        }

        // And now for the points around the map border.

        // Top bar.
        invalidPoints.addAll(
                generateBlock(0, margin, width, 0)
        );
        // Bottom bar
        invalidPoints.addAll(
                generateBlock(0, height, width, height - margin)
        );
        // Left bar
        invalidPoints.addAll(
                generateBlock(0, height - margin, margin, margin)
        );
        // Right bar
        invalidPoints.addAll(
                generateBlock(width - margin, height - margin, width, margin)
        );

        vertices.removeAll(invalidPoints);
    }

    private static Set<NdPoint> generateBlock(double x1, double y1, double x2, double y2) {
        Set<NdPoint> points = new HashSet<NdPoint>();
        for(double i = x1; i <= x2; i++) {
            for(double j = y1; j >= y2; j--) {
                points.add(new NdPoint(i, j));
            }
        }
        return points;
    }

}
