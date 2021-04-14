/* *****************************************************************************
 *  Name: Sharun Garg
 *  Date: April 14th, 2021
 *  Description: Algorithms Part 1 by Princeton University - Week 3 Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private final Point[] inputPoints;
    private int noOfSegments;

    public BruteCollinearPoints(Point[] points) {
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
        Arrays.sort(this.inputPoints);
        this.noOfSegments = -1;
    }

    public int numberOfSegments() {
        return this.noOfSegments == -1 ? this.segments().length : this.noOfSegments;
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[1];
        int index = 0;
        for (int i = 0; i < inputPoints.length - 3; i++)
            for (int j = i + 1; j < inputPoints.length - 2; j++)
                for (int k = j + 1; k < inputPoints.length - 1; k++)
                    for (int m = k + 1; m < inputPoints.length; m++) {
                        Point first = this.inputPoints[i];
                        Point second = this.inputPoints[j];
                        Point third = this.inputPoints[k];
                        Point fourth = this.inputPoints[m];
                        if (first.slopeTo(second) == second.slopeTo(third)) {
                            if (third.slopeTo(fourth) == first.slopeTo(fourth)) {
                                if (index == segments.length) {
                                    segments = this.increaseArrayLength(segments);
                                }
                                segments[index++] = new LineSegment(first, fourth);
                            }
                        }
                    }

        segments = Arrays.copyOfRange(segments, 0, index);
        this.noOfSegments = segments.length;
        return segments;
    }

    private LineSegment[] increaseArrayLength(LineSegment[] inputArray) {
        LineSegment[] newArray = new LineSegment[inputArray.length * 2];
        for (int i = 0; i < inputArray.length; i++)
            newArray[i] = inputArray[i];
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        LineSegment[] segments = collinear.segments();
        System.out.println("Number of Segments: " + segments.length);
        for (LineSegment segment : segments) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
