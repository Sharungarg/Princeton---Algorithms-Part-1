/* *****************************************************************************
 *  Name: Sharun Garg
 *  Date: May 2nd, 2021
 *  Description: Week 4 Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private BoardNode finalGoalBoardNode;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        this.solveTheBoard(initial);
    }

    public boolean isSolvable() {
        boolean result = this.finalGoalBoardNode != null;
        return result;
    }

    public int moves() {
        return this.isSolvable() ? this.finalGoalBoardNode.moves : -1;
    }

    public Iterable<Board> solution() {
        if (this.finalGoalBoardNode == null) return null;
        Stack<Board> result = new Stack<Board>();
        result.push(this.finalGoalBoardNode.board);
        BoardNode previous = this.finalGoalBoardNode.previousNode;
        while (previous != null) {
            result.push(previous.board);
            previous = previous.previousNode;
        }
        return result;
    }

    private void solveTheBoard(Board initialBoard) {
        MinPQ<BoardNode> boardQueue = new MinPQ<BoardNode>();
        MinPQ<BoardNode> twinBoardQueue = new MinPQ<BoardNode>();
        boardQueue.insert(new BoardNode(initialBoard, null));
        twinBoardQueue.insert(new BoardNode(initialBoard.twin(), null));

        while (!boardQueue.isEmpty() && !twinBoardQueue.isEmpty()) {
            BoardNode dequeueNode = boardQueue.delMin();
            Board dequeueBoard = dequeueNode.board;

            if (dequeueBoard.isGoal()) {
                this.finalGoalBoardNode = dequeueNode;
                break;
            }

            for (Board neighbour : dequeueBoard.neighbors()) {
                if (dequeueNode.previousNode == null || !neighbour
                        .equals(dequeueNode.previousNode.board)) {
                    boardQueue.insert(new BoardNode(neighbour, dequeueNode));
                }
            }


            BoardNode twinDequeueNode = twinBoardQueue.delMin();
            Board twinDequeueBoard = twinDequeueNode.board;

            if (twinDequeueBoard.isGoal()) {
                break;
            }

            for (Board neighbour : twinDequeueBoard.neighbors()) {
                if (twinDequeueNode.previousNode == null || !neighbour
                        .equals(twinDequeueNode.previousNode.board)) {
                    twinBoardQueue.insert(new BoardNode(neighbour, twinDequeueNode));
                }
            }
        }
    }

    private class BoardNode implements Comparable<BoardNode> {
        private final BoardNode previousNode;
        private final int manhattanDistance;
        private final int moves;
        private final Board board;

        public BoardNode(Board node, BoardNode previousNode) {
            this.board = node;
            this.manhattanDistance = node.manhattan();
            this.moves = previousNode != null ? previousNode.moves + 1 : 0;
            this.previousNode = previousNode;
        }

        public int manhattanPriorityFunction() {
            int priorityValue = this.manhattanDistance + this.moves;
            return priorityValue;
        }

        public int compareTo(BoardNode that) {
            int result = Integer
                    .compare(this.manhattanPriorityFunction(), that.manhattanPriorityFunction());
            return result;
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
