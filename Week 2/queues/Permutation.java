/* *****************************************************************************
 *  Name: Sharun Garg
 *  Date: April 5th, 2021
 *  Description: Algorithms Part 1 by Princeton University - Week 2 Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int i = 1;
        RandomizedQueue<String> randomQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            randomQueue.enqueue(StdIn.readString());
        }
        for (String item : randomQueue) {
            if (i > k) break;
            System.out.println(item);
            i++;
        }
    }
}
