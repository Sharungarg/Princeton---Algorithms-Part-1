/* *****************************************************************************
 *  Name: Sharun Garg
 *  Date: March 30th, 2021
 *  Description: Algorithms Part 1 by Princeton University - Week 1 Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF weightedUF;
    private int[] grid;
    private int openSites = 0;
    private final int size;
    private int upperRowRoot = -1;
    private int lowerRowRoot = -1;

    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();

        this.size = n;
        int totalElements = (int) Math.pow(n, 2);
        this.weightedUF = new WeightedQuickUnionUF(totalElements);
        this.grid = new int[totalElements];
        for (int i = 0; i < totalElements; i++) {
            this.grid[i] = 0;
        }
    }

    private int map2Dto1D(int row, int col) {
        return ((row - 1) * this.size) + col - 1;
    }

    private void validate(int row, int col) {
        if ((row > this.size) || (row < 1) || (col > this.size) || (col < 1))
            throw new IllegalArgumentException();
    }

    public void open(int row, int col) {
        this.validate(row, col);

        int element = this.map2Dto1D(row, col);

        if (this.isOpen(row, col)) {
            return;
        }

        this.grid[element] = 1;
        this.openSites++;

        if (row == 1) {
            if (this.upperRowRoot == -1) {
                this.upperRowRoot = element;
            }
            this.weightedUF.union(element, this.upperRowRoot);
        }

        if (row == this.size) {
            if (this.lowerRowRoot == -1) {
                this.lowerRowRoot = element;
            }
            this.weightedUF.union(element, this.lowerRowRoot);
        }

        if (row > 1) {
            int upperRow = row - 1;
            int upperElement = this.map2Dto1D(upperRow, col);
            if (this.isOpen(upperRow, col)) {
                this.weightedUF.union(upperElement, element);
            }
        }

        if (row < this.size) {
            int lowerRow = row + 1;
            int lowerElement = this.map2Dto1D(lowerRow, col);
            if (this.isOpen(lowerRow, col)) {
                this.weightedUF.union(lowerElement, element);
            }
        }

        if (col > 1) {
            int leftCol = col - 1;
            int leftElement = this.map2Dto1D(row, leftCol);
            if (this.isOpen(row, leftCol)) {
                this.weightedUF.union(leftElement, element);
            }
        }

        if (col < this.size) {
            int rightCol = col + 1;
            int rightElement = this.map2Dto1D(row, rightCol);
            if (this.isOpen(row, rightCol)) {
                this.weightedUF.union(rightElement, element);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        this.validate(row, col);

        int element = this.map2Dto1D(row, col);
        return this.grid[element] == 1;
    }

    public boolean isFull(int row, int col) {
        this.validate(row, col);

        if (this.upperRowRoot == -1) {
            return false;
        }

        int element = this.map2Dto1D(row, col);

        return this.weightedUF.find(element) == this.weightedUF.find(this.upperRowRoot);
    }

    public int numberOfOpenSites() {
        return this.openSites;
    }

    public boolean percolates() {
        if (this.upperRowRoot == -1 || this.lowerRowRoot == -1) {
            return false;
        }

        return this.weightedUF.find(upperRowRoot) == this.weightedUF.find(lowerRowRoot);
    }
}
