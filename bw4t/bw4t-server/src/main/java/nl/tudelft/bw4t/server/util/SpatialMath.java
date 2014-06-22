package nl.tudelft.bw4t.server.util;

import repast.simphony.space.continuous.NdPoint;

/**
 * A Utility class to do spatial calculations.
 */
public final class SpatialMath {

    /**
     * Utility Class, no instanciation.
     */
    private SpatialMath() {
    }

    /**
     * calculate the distance between two n-dimensional points, if the points are in different dimensions the result is
     * -1.
     * 
     * @param p1
     *            the first point
     * @param p2
     *            the second point
     * @return the distance greater than zero or -1 if the points are on different dimensions.
     */
    public static double distance(NdPoint p1, NdPoint p2) {
        final int dims = p1.dimensionCount();
        if (dims != p2.dimensionCount()) {
            return -1;
        }
        double root = 0;
        double[] v1 = p1.toDoubleArray(null);
        double[] v2 = p2.toDoubleArray(null);

        for (int i = 0; i < dims; i++) {
            double a = v1[i];
            double b = v2[i];
            root += Math.pow(a - b, 2);
        }

        return Math.sqrt(root);
    }

    /**
     * Calculate the angle when trying to go from origin to dest.
     * 
     * @param origin
     *            the originating point
     * @param dest
     *            the destination
     * @return the angle in radians
     */
    public static double angle(NdPoint origin, NdPoint dest) {
        if (origin.dimensionCount() != 2 || dest.dimensionCount() != 2) {
            throw new IllegalArgumentException("We need 2d points.");
        }
        double dX = dest.getX() - origin.getX();
        double dY = dest.getY() - origin.getY();
        return repast.simphony.space.SpatialMath.angleFromDisplacement(dX, dY);
    }

    /**
     * Calculates the angle in the corner A of the triangle ABC.
     * @param pA the point A
     * @param pB the point B
     * @param pC the point C
     * @return the angle in A
     */
    public static double angle(NdPoint pA, NdPoint pB, NdPoint pC) {
        double a = distance(pB, pC);
        double b = distance(pA, pC);
        double c = distance(pA, pB);
        
        double result = Math.pow(b, 2) + Math.pow(c, 2) - Math.pow(a, 2);
        result /= 2. * b * c;
        return Math.acos(result);
    }
}
