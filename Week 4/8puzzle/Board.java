/* *****************************************************************************
 *  Name: Sharun Garg
 *  Date: May 2nd, 2021
 *  Description: Week 4 Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public final class Board {
    private final int[][] tiles;
    private final int n;
    private int zeroX;
    private int zeroY;
    private int hammingDistance = -1;
    private int manhattanDistance = -1;

    public Board(int[][] tiles) {
        this.n = tiles.length;
        int size = tiles.length;
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int tile = tiles[i][j];
                copy[i][j] = tile;
                if (tile == 0) {
                    this.zeroX = i;
                    this.zeroY = j;
                }
            }
        }
        this.tiles = copy;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.n + "\n");
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                s.append(String.format("%2d ", this.tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int dimension() {
        return this.n;
    }

    public int hamming() {
        if (this.hammingDistance != -1) {
            return this.hammingDistance;
        }
        this.calculateBoardDistance();
        return this.hammingDistance;
    }

    public int manhattan() {
        if (this.manhattanDistance != -1) {
            return this.manhattanDistance;
        }
        this.calculateBoardDistance();
        return this.manhattanDistance;
    }

    private void calculateBoardDistance() {
        int hamming = 0;
        int manhattan = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                int currentElement = this.tiles[i][j];
                if (currentElement == 0) continue;

                // Hamming distance calculation
                int goalElement = i * this.n + j + 1;
                if (currentElement != goalElement) {
                    hamming++;
                }

                // Manhattan distance calculation
                int[] correctIndex = this.getCorrectIndicesFor(currentElement);
                manhattan += Math.abs(correctIndex[0] - i) + Math.abs(correctIndex[1] - j);
            }
        }

        this.hammingDistance = hamming;
        this.manhattanDistance = manhattan;
    }

    private int[] getCorrectIndicesFor(int element) {
        int[] correctIndices = new int[2];
        int row = element / this.n;
        int col = element - row * this.n - 1;
        if (element % this.n == 0) {
            row--;
            col = this.n - 1;
        }
        correctIndices[0] = row;
        correctIndices[1] = col;
        return correctIndices;
    }

    public boolean isGoal() {
        return this.hamming() == 0;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return this.dimension() == that.dimension() && Arrays.deepEquals(this.tiles, that.tiles);
    }

    public Iterable<Board> neighbors() {
        Queue<Board> queueOfNeighbours = new Queue<>();
        this.generateNeighbours(queueOfNeighbours);
        return queueOfNeighbours;
    }

    private void generateNeighbours(Queue<Board> boardQueue) {
        int[] zeroPlace = this.positionOfZero();
        int rowOfEmptySpace = zeroPlace[0];
        int colOfEmptySpace = zeroPlace[1];
        int size = this.tiles.length;
        if (rowOfEmptySpace == 0) {
            if (colOfEmptySpace == 0) {
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace,
                                                                     colOfEmptySpace + 1)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace + 1,
                                                                     colOfEmptySpace)));
            }
            else if (colOfEmptySpace == size - 1) {
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace + 1,
                                                                     colOfEmptySpace)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace,
                                                                     colOfEmptySpace - 1)));
            }
            else {
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace,
                                                                     colOfEmptySpace + 1)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace + 1,
                                                                     colOfEmptySpace)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace,
                                                                     colOfEmptySpace - 1)));
            }
        }
        else if (rowOfEmptySpace == size - 1) {
            if (colOfEmptySpace == 0) {
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace - 1,
                                                                     colOfEmptySpace)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace,
                                                                     colOfEmptySpace + 1)));
            }
            else if (colOfEmptySpace == size - 1) {
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace,
                                                                     colOfEmptySpace - 1)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace - 1,
                                                                     colOfEmptySpace)));
            }
            else {
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace,
                                                                     colOfEmptySpace - 1)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace - 1,
                                                                     colOfEmptySpace)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace,
                                                                     colOfEmptySpace + 1)));
            }
        }
        else {
            if (colOfEmptySpace == 0) {
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace - 1,
                                                                     colOfEmptySpace)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace,
                                                                     colOfEmptySpace + 1)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace + 1,
                                                                     colOfEmptySpace)));
            }
            else if (colOfEmptySpace == size - 1) {
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace + 1,
                                                                     colOfEmptySpace)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace,
                                                                     colOfEmptySpace - 1)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace - 1,
                                                                     colOfEmptySpace)));
            }
            else {
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace - 1,
                                                                     colOfEmptySpace)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace,
                                                                     colOfEmptySpace + 1)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace + 1,
                                                                     colOfEmptySpace)));
                boardQueue.enqueue(Board.mutateBoardWith(this.tiles, zeroPlace,
                                                         Board.point(rowOfEmptySpace,
                                                                     colOfEmptySpace - 1)));
            }
        }
    }

    private int[] positionOfZero() {
        int[] zero = { this.zeroX, this.zeroY };
        return zero;
    }

    private static int[] point(int i, int j) {
        int[] point = { i, j };
        return point;
    }

    private static Board mutateBoardWith(int[][] tilesClone, int[] elementOne, int[] elementTwo) {
        Board.swap(tilesClone, elementOne, elementTwo);
        Board neighbour = new Board(tilesClone);
        Board.swap(tilesClone, elementOne, elementTwo);
        return neighbour;
    }

    private static void swap(int[][] tilesClone, int[] elementOne, int[] elementTwo) {
        int temp = tilesClone[elementOne[0]][elementOne[1]];
        tilesClone[elementOne[0]][elementOne[1]] = tilesClone[elementTwo[0]][elementTwo[1]];
        tilesClone[elementTwo[0]][elementTwo[1]] = temp;
    }

    public Board twin() {
        int[] indexOne = { 0, 0 };
        int[] indexTwo = { 1, 1 };
        if (this.zeroX == 0 && this.zeroY == 0) indexOne[0] = 1;
        if (this.zeroX == 1 && this.zeroY == 1) indexTwo[0] = 0;
        Board twin = Board.mutateBoardWith(this.tiles, indexOne, indexTwo);
        return twin;
    }

    public static void main(String[] args) {
        // puzzle3x3-01.txt puzzle3x3-02.txt puzzle3x3-03.txt puzzle3x3-04.txt puzzle3x3-05.txt puzzle3x3-06.txt puzzle3x3-07.txt puzzle3x3-08.txt puzzle3x3-09.txt puzzle3x3-10.txt puzzle3x3-11.txt puzzle3x3-12.txt puzzle3x3-13.txt puzzle3x3-14.txt puzzle3x3-15.txt puzzle3x3-16.txt puzzle3x3-17.txt puzzle3x3-18.txt puzzle3x3-19.txt puzzle3x3-20.txt puzzle3x3-21.txt puzzle3x3-22.txt puzzle3x3-23.txt puzzle3x3-24.txt puzzle3x3-25.txt puzzle3x3-26.txt puzzle3x3-27.txt puzzle3x3-28.txt puzzle3x3-29.txt puzzle3x3-30.txt puzzle3x3-31.txt
        // puzzle2x2-00.txt puzzle2x2-01.txt puzzle2x2-02.txt puzzle2x2-03.txt puzzle2x2-04.txt puzzle2x2-05.txt puzzle2x2-06.txt

        for (String file : args) {
            System.out.println("File: " + file);
            In in = new In(file);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            System.out.println("Board: ----" + initial.toString());
            System.out.println("Is Goal Board? : " + initial.isGoal());
            System.out.println("Dimension: " + initial.dimension());
            System.out.println("Hamming Distance: " + initial.hamming());
            System.out.println("Manhattan Distance: " + initial.manhattan() + "\n");
            System.out.println("Twin: ----" + initial.twin().toString());
            System.out.println("Is Twin Equal? : " + initial.equals(initial.twin()) + "\n");
            System.out.println("Neighbours:");
            for (Board board : initial.neighbors()) {
                System.out.println(board.toString());
            }
            System.out.println("______________________________________");
        }
    }
}
