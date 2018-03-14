package onethreeseven.trajsuitePlugin.util;

import onethreeseven.geo.projection.AbstractGeographicProjection;
import onethreeseven.geo.model.LatLonBounds;
import onethreeseven.trajsuitePlugin.model.BoundingCoordinates;
import java.util.Collection;
import java.util.Iterator;

/**
 * A utility for dealing with objects that have bounds.
 * @author Luke Bermingham
 */
public final class BoundsUtil {

    private BoundsUtil() {
    }

    /**
     * Gets the bounds given a collection of bounding coordinates.
     * @param bounds The ultimate bounds of the collection.
     * @return The bounds of the collection.
     */
    public static double[][] calculateFromBoundingCoordinates(Collection<? extends BoundingCoordinates> bounds){

        if(bounds == null || bounds.isEmpty()){
            throw new IllegalArgumentException("Collection of bounds must be non-null and non-empty.");
        }

        int nDims = bounds.iterator().next().getBounds().length;
        double[][] curBounds = BoundsUtil.boundToOverride(nDims);

        for (BoundingCoordinates boundingCoords : bounds) {
            expandBounds(curBounds, boundingCoords.getBounds());
        }

        return curBounds;
    }

    /**
     * Get the corners of the bounds
     * @param bounds the bounds [nDim][{min, max}]
     * @return The corner points from the given bounds
     */
    public static double[][] getCorners(double[][] bounds) {
        int nDimensions = bounds.length;
        //rectangle 4, cube 8, tesseract 16... etc
        int nCorners = (nDimensions > 1) ? (int) Math.pow(2, nDimensions) : 0;
        double[][] corners = new double[nCorners][];
        //get each corner we just permute the bounds

        //go through each corner
        for (int i = 0; i < nCorners; i++) {
            double[] corner = new double[nDimensions];
            for (int j = 0; j < nDimensions; j++) {
                //we get the binary representation, i.e binary array of size nDimensions
                int idx = ((i & (nCorners >> j + 1)) == 0) ? 0 : 1;
                corner[j] = bounds[j][idx];
            }
            corners[i] = corner;
        }
        return corners;
    }

    /**
     * Multiplies the length of each dimension together to produce a single unit.
     * In 2d this is area, in 3d this volume, in 4d this is hyper-volume?
     * @param bounds The bounds
     * @return The area, or volume, or hyper-volume of the bounds (depends on dimensionality of the bounds).
     */
    public static double getVolume(double[][] bounds){
        double[] lengths = new double[bounds.length];
        for (int i = 0; i < bounds.length; i++) {
            lengths[i] = bounds[i][1] - bounds[i][0];
        }
        double vol = 1;
        for (double length : lengths) {
            vol *= length;
        }
        return vol;
    }

    public static double[] getCenter(double[][] bounds){
        int nDimensions = bounds.length;
        double[] center = new double[nDimensions];
        for (int n = 0; n < nDimensions; n++) {
            center[n] = bounds[n][0] + (bounds[n][1] - bounds[n][0]) / 2;
        }
        return center;
    }

    public static double[][] fromLatLonBounds(LatLonBounds geoBounds, AbstractGeographicProjection projection){

        double[] minLatLon = projection.geographicToCartesian(geoBounds.getMinLat(), geoBounds.getMinLon());
        double[] maxLatLon = projection.geographicToCartesian(geoBounds.getMaxLat(), geoBounds.getMaxLon());

        double[][] bounds = new double[minLatLon.length][2];
        for (int n = 0; n < minLatLon.length; n++) {
            bounds[n][0] = minLatLon[n];
            bounds[n][1] = maxLatLon[n];
        }
        return bounds;
    }

    /**
     * Calculate the bounds of a set of n-d integer points
     *
     * @param values n-d integer points
     * @return the bounds like { {min,max}, {min,max} }
     */
    public static int[][] calculateBounds(int[][] values) {
        int nDimensions = values[0].length;
        int[][] bounds = new int[nDimensions][2];
        //make bounds to override
        for (int i = 0; i < nDimensions; i++) {
            bounds[i][0] = Integer.MAX_VALUE;
            bounds[i][1] = -Integer.MAX_VALUE;
        }

        //find min/max
        for (int[] ndIndices : values) {
            for (int n = 0; n < nDimensions; n++) {
                int val = ndIndices[n];
                if (val < bounds[n][0]) {
                    bounds[n][0] = val;
                }
                if (val > bounds[n][1]) {
                    bounds[n][1] = val;
                }
            }
        }
        return bounds;
    }

    /**
     * @param entries     the entries to find the bounds of
     * @return The bounds of all the n-dimensional vectors in the format { {0,100} , {-50, 50} }.
     * Where [n][0] is min and [n][1] is max.
     */
    public static double[][] calculateBounds(Iterator<double[]> entries) {

        //[0] is min for that dim and [1] is max for that dim
        double[][] currentBounds = null;

        if(!entries.hasNext()){
            return currentBounds;
        }

        {
            double[] firstCoord = entries.next();
            int nDimensions = firstCoord.length;
            currentBounds = new double[nDimensions][2];
            //fill it with values that will be overridden
            for (int n = 0; n < nDimensions; n++) {
                //so, [0] is min for that dim and [1] is max for that dim
                currentBounds[n] = new double[]{firstCoord[n], firstCoord[n]};
            }
        }

        while(entries.hasNext()){
            double[] entry = entries.next();
            for (int i = 0; i < entry.length; i++) {
                double entryValN = entry[i];
                //current val smaller than nMin
                if (entryValN < currentBounds[i][0]) {
                    currentBounds[i][0] = entryValN;
                }
                //current val bigger than nMax
                if (entryValN > currentBounds[i][1]) {
                    currentBounds[i][1] = entryValN;
                }
            }
        }
        return currentBounds;
    }

    /**
     * Pad the bounds by some amount
     * @param bounds the bounds (min,max) in each dimension
     * @param padding the padding to apply
     * @return the padded bounds
     */
    public static double[][] padBounds(double[][] bounds, int padding) {
        for (int i = 0; i < bounds.length; i++) {
            bounds[i][0] -= padding;
            bounds[i][1] += padding;
        }
        return bounds;
    }

    /**
     * Given a bunch of n-dimensional points normalise them into a new bounds
     *
     * @param ndPoints      the nd-points
     * @param desiredBounds the new n-d bounds
     */
    public static void normaliseIntoBounds(double[][] ndPoints, double[][] desiredBounds) {

        int nDimensions = desiredBounds.length;
        double[][] curBounds = boundToOverride(nDimensions);
        //expand to include
        for (double[] ndPoint : ndPoints) {
            expandBounds(curBounds, ndPoint);
        }
        //now normalise
        for (int i = 0; i < ndPoints.length; i++) {
            for (int n = 0; n < nDimensions; n++) {
                double nd = ndPoints[i][n];
                //normalise in the range 0..1
                nd = (nd - curBounds[n][0]) / (curBounds[n][1] - curBounds[n][0]);
                //project into the new bounds
                nd = nd * (desiredBounds[n][1] - desiredBounds[n][0]) + desiredBounds[n][0];
                //accumulate the normalised value back into the the nd points
                ndPoints[i][n] = nd;
            }
        }
    }

    /**
     * Get the all the mins/maxs of a given nD bounds.
     * @param bounds The bounds, i.e [{min, max},{min,max},{min,max}]
     * @param min True: get all the mins. False: get all the maxs.
     * @return An nd-point containing all the mins (or maxs).
     */
    public static double[] getMinOrMaxOfBounds(double[][] bounds, boolean min){
        double[] ndPt = new double[bounds.length];
        int idx = min ? 0 : 1;
        for (int i = 0; i < bounds.length; i++) {
            ndPt[i] = bounds[i][idx];
        }
        return ndPt;
    }

    /**
     * Expands one bounds by another by taking on its minimum and maximum points (if they are smaller/larger).
     * @param boundsToExpand The bounds to expand.
     * @param otherBounds The bounds to use to expand the first parameter.
     */
    public static void expandBounds(double[][] boundsToExpand, double[][] otherBounds){
        if(boundsToExpand.length != otherBounds.length){
            throw new IllegalArgumentException("If bounds are to be expanded they must have the same dimensionality.");
        }
        //extract min/max points from other bounds
        double[] minPt = getMinOrMaxOfBounds(otherBounds, true);
        double[] maxPt = getMinOrMaxOfBounds(otherBounds, false);
        expandBounds(boundsToExpand, minPt);
        expandBounds(boundsToExpand, maxPt);
    }

    /**
     * Expand a given point to fit a new point in (if necessary)
     *
     * @param bounds  The n-dimensional bounds, specified as { {min, max}, {min, max}, ..., {nMin, nMax} }
     * @param ndPoint The n-dimensional point {n1, n2, ..., nN}
     */
    public static void expandBounds(double[][] bounds, double[] ndPoint) {
        for (int i = 0; i < bounds.length; i++) {
            double nD = ndPoint[i];
            double minNd = bounds[i][0];
            double maxNd = bounds[i][1];
            if (nD < minNd) {
                bounds[i][0] = nD;
            }
            if (nD > maxNd) {
                bounds[i][1] = nD;
            }
        }
    }

    /**
     * @param nDimensions the number of dimensions to make for this bounds
     * @return Creates a bounds that has Max as min and Min as Max, so any number will override it
     */
    public static double[][] boundToOverride(int nDimensions) {
        double[][] bounds = new double[nDimensions][2];
        //fill it with values that will be overridden
        for (int n = 0; n < nDimensions; n++) {
            //so, Max for min and Min for max, will definitely be overridden
            bounds[n] = new double[]{Double.MAX_VALUE, -Double.MAX_VALUE};
        }
        return bounds;
    }



}
