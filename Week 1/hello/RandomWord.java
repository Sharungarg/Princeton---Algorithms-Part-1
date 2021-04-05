/* *****************************************************************************
 *  Name: Sharun Garg
 *  Date: March 23rd, 2021
 *  Description: Algorithms Part 1 by Princeton University - Week 1 Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "yoo";
        double index = 1;
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            double prob = 1 / index;
            if (StdRandom.bernoulli(prob)) {
                champion = input;
            }
            index++;
        }
        System.out.println(champion);
    }
}
