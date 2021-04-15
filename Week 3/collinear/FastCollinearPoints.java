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
        Arrays.sort(this.inputPoints);
    }

    public int numberOfSegments() {
        return this.noOfSegments == 0 ? this.segments().length : this.noOfSegments;
    }

    public LineSegment[] segments() {
        if (this.inputPoints.length < 4) return new LineSegment[0];
        int index = 0;
        LineSegment[] segments = new LineSegment[1];

        for (int i = 0; i < this.inputPoints.length; i++) {
            Point referencePoint = this.inputPoints[i];
            Comparator<Point> referencePointComparator = referencePoint.slopeOrder();
            Point[] sortedPointsWithReferencePoint = this.inputPoints.clone();
            Arrays.sort(sortedPointsWithReferencePoint, referencePointComparator);


            double ignoreSlope = Double.NEGATIVE_INFINITY;
            Point nextPoint = sortedPointsWithReferencePoint[1];
            Point lastPointOnSegment = nextPoint;
            double commonSlope = referencePoint.slopeTo(nextPoint);
            int collinearPoints = 2;
            if (referencePoint.compareTo(nextPoint) > 0) {
                ignoreSlope = commonSlope;
            }


            for (int j = 0; j < sortedPointsWithReferencePoint.length; j++) {
                nextPoint = sortedPointsWithReferencePoint[j];
                double nextPointSlope = referencePoint.slopeTo(nextPoint);
                if (nextPointSlope == ignoreSlope) {
                    continue;
                }
                if (referencePoint.compareTo(nextPoint) > 0) {
                    ignoreSlope = nextPointSlope;
                }
                if (nextPointSlope == commonSlope) {
                    collinearPoints++;
                    lastPointOnSegment = nextPoint;
                }
                else {
                    if (collinearPoints >= 4) {
                        if (index == segments.length) {
                            segments = this.increaseArrayLength(segments);
                        }
                        segments[index++] = new LineSegment(referencePoint, lastPointOnSegment);
                    }
                    commonSlope = nextPointSlope;
                    collinearPoints = 2;
                }
            }
            if (collinearPoints >= 4) {
                if (index == segments.length) {
                    segments = this.increaseArrayLength(segments);
                }
                segments[index++] = new LineSegment(referencePoint, lastPointOnSegment);
            }
        }
        segments = Arrays.copyOfRange(segments, 0, index);
        return segments;
    }


    private LineSegment[] increaseArrayLength(LineSegment[] inputArray) {
        LineSegment[] newArray = new LineSegment[inputArray.length * 2];
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


