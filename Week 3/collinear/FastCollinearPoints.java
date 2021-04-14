/* *****************************************************************************
 *  Name: Sharun Garg
 *  Date: April 14th, 2021
 *  Description: Algorithms Part 1 by Princeton University - Week 3 Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private final Point[] inputPoints;
    private int noOfSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        this.inputPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            Point inputPoint = points[i];
            if (inputPoint == null) throw new IllegalArgumentException();
            for (int j = i - 1; j >= 0; j--) {
                if (inputPoint.compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
            this.inputPoints[i] = points[i];
        }
        this.noOfSegments = 0;
    }

    public int numberOfSegments() {
        return this.noOfSegments == 0 ? this.segments().length : this.noOfSegments;
    }

    public LineSegment[] segments() {
        if (this.inputPoints.length < 4) return new LineSegment[0];
        Point[] segmentPairs = new Point[2];
        int index = 0;

        for (int i = 0; i < this.inputPoints.length; i++) {
            Point referencePoint = this.inputPoints[i];
            Comparator<Point> referencePointComparator = referencePoint.slopeOrder();
            Point[] sortedPointsWithReferencePoint = this.inputPoints.clone();
            Arrays.sort(sortedPointsWithReferencePoint, referencePointComparator);

            // Check for slope with each point
            Point nextPoint = sortedPointsWithReferencePoint[1];
            double referenceSlope = referencePoint.slopeTo(nextPoint);
            int collinearPoints = 2;
            // Create points that would be ends of line segment
            Point nearPoint;
            Point farPoint;
            if (referencePoint.compareTo(nextPoint) < 0) {
                nearPoint = referencePoint;
                farPoint = nextPoint;
            }
            else {
                nearPoint = nextPoint;
                farPoint = referencePoint;
            }

            // Iterate over points to check for collinearity
            for (int j = 2; j < sortedPointsWithReferencePoint.length; j++) {
                nextPoint = sortedPointsWithReferencePoint[j];
                double slopeWithIteratedPoint = referencePoint.slopeTo(nextPoint);
                if (slopeWithIteratedPoint == referenceSlope) {
                    collinearPoints++;
                    if (nextPoint.compareTo(farPoint) > 0) {
                        farPoint = nextPoint;
                    }
                    if (nextPoint.compareTo(nearPoint) < 0) {
                        nearPoint = nextPoint;
                    }
                }
                else {
                    // Add point pair to the segmentPair array if it doesn't exist already.
                    if (collinearPoints >= 4 && !this
                            .checkPairExists(segmentPairs, nearPoint, farPoint)) {
                        if (index >= segmentPairs.length) {
                            segmentPairs = this.increaseArrayLength(segmentPairs);
                        }
                        segmentPairs[index++] = nearPoint;
                        segmentPairs[index++] = farPoint;

                    }

                    // Reset nearPoint, farPoint, collinearPairs and referenceSlope wrt to the new point.
                    collinearPoints = 2;
                    referenceSlope = slopeWithIteratedPoint;
                    if (referencePoint.compareTo(nextPoint) < 0) {
                        nearPoint = referencePoint;
                        farPoint = nextPoint;
                    }
                    else {
                        nearPoint = nextPoint;
                        farPoint = referencePoint;
                    }
                }
            }

            // For Vertical Points (array iteration over and there must be a pair forming which is not added)
            if (collinearPoints >= 4 && !this.checkPairExists(segmentPairs, nearPoint, farPoint)) {
                if (index >= segmentPairs.length) {
                    segmentPairs = this.increaseArrayLength(segmentPairs);
                }
                segmentPairs[index++] = nearPoint;
                segmentPairs[index++] = farPoint;

            }
        }
        segmentPairs = Arrays.copyOfRange(segmentPairs, 0, index);
        LineSegment[] segments = this.generateLineSegments(segmentPairs);
        this.noOfSegments = segments.length;
        return segments;
    }

    private LineSegment[] generateLineSegments(Point[] segmentPairs) {
        LineSegment[] segments = new LineSegment[segmentPairs.length / 2];
        int index = 0;
        for (int i = 0; i < segmentPairs.length - 1; i += 2) {
            Point first = segmentPairs[i];
            Point second = segmentPairs[i + 1];
            if (first != null && second != null) {
                segments[index++] = new LineSegment(first, second);
            }
        }
        return segments;
    }

    private boolean checkPairExists(Point[] segmentPairs, Point nearPoint, Point farPoint) {
        for (int i = 0; i < segmentPairs.length - 1; i += 2) {
            Point first = segmentPairs[i];
            Point second = segmentPairs[i + 1];
            if (first != null && second != null) {
                if (first.compareTo(nearPoint) == 0 && second.compareTo(farPoint) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private Point[] increaseArrayLength(Point[] inputArray) {
        Point[] newArray = new Point[inputArray.length * 2];
        for (int i = 0; i < inputArray.length; i++) {
            newArray[i] = inputArray[i];
        }
        return newArray;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        LineSegment[] segments = collinear.segments();
        System.out.println("Number of Segments: " + segments.length);
        for (LineSegment segment : segments) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}


