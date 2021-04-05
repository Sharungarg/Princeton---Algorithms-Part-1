/* *****************************************************************************
 *  Name: Sharun Garg
 *  Date: March 30th, 2021
 *  Description: Algorithms Part 1 by Princeton University - Week 1 Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int trials;
    private final double[] thresholds;
    private final double CONFIDENCE_95 = 1.96;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) throw new IllegalArgumentException();

        this.trials = trials;
        this.thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                perc.open(row, col);
            }
            this.thresholds[i] = (double) perc.numberOfOpenSites() / Math.pow(n, 2);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return this.mean() - ((this.CONFIDENCE_95 * this.stddev()) / Math
                .sqrt(this.trials));
    }

    public double confidenceHi() {
        return this.mean() + ((this.CONFIDENCE_95 * this.stddev()) / Math
                .sqrt(this.trials));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[0]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println(
                "95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi()
                        + "]");
    }
}
