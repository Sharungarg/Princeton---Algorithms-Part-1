/* *****************************************************************************
 *  Name: Sharun Garg
 *  Date: April 5th, 2021
 *  Description: Algorithms Part 1 by Princeton University - Week 2 Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int pointer = -1;
    private Item[] itemArray;
    private int emptyIndex = -1;
    private int emptySpaces = 0;

    public RandomizedQueue() {
        this.itemArray = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        boolean isEmpty = this.size() == 0;
        return isEmpty;
    }

    public int size() {
        int items = this.pointer - this.emptySpaces + 1;
        if (this.pointer == -1) items = 0;
        return items;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (this.emptyIndex != -1) {
            this.itemArray[this.emptyIndex] = item;
            this.emptyIndex = -1;
            this.emptySpaces--;
            return;
        }

        if (this.isEmpty()) {
            this.pointer++;
            this.itemArray[this.pointer] = item;
            return;
        }

        if (this.pointer == this.itemArray.length - 1) this.resizeArray(2 * this.itemArray.length);
        this.itemArray[++this.pointer] = item;
    }

    public Item dequeue() {
        this.validateQueueHasItems();
        int randomIndex = this.getRandomIndex();
        Item value = this.itemArray[randomIndex];
        this.itemArray[randomIndex] = null;
        this.emptySpaces++;
        this.emptyIndex = randomIndex;

        if (isDequeQuarterFull())
            this.resizeArray(this.itemArray.length % 2 != 0 ? (this.itemArray.length / 2) + 1 :
                             (this.itemArray.length / 2));
        if (this.emptySpaces >= this.size()) this.deleteEmptySpaces();
        return value;
    }

    public Item sample() {
        this.validateQueueHasItems();
        Item randomSample = this.itemArray[this.getRandomIndex()];
        return randomSample;
    }

    private void validateQueueHasItems() {
        if (this.size() == 0) throw new NoSuchElementException();
    }

    private int getRandomIndex() {
        int randomIndex = StdRandom.uniform(this.pointer + 1);
        Item value = this.itemArray[randomIndex];
        while (value == null) {
            randomIndex = StdRandom.uniform(this.pointer + 1);
            value = this.itemArray[randomIndex];
        }
        return randomIndex;
    }

    private boolean isDequeQuarterFull() {
        boolean isQuarterFull = ((double) this.size() / this.itemArray.length) <= 0.25;
        return isQuarterFull;
    }

    private void resizeArray(int length) {
        Item[] resizedArray = (Item[]) new Object[length];
        int j = 0;
        for (int i = 0; i <= this.pointer; i++) {
            Item value = this.itemArray[i];
            if (value == null) continue;
            resizedArray[j++] = value;
        }
        this.pointer = j - 1;
        this.emptySpaces = 0;
        this.emptyIndex = -1;
        this.itemArray = resizedArray;
    }

    private void deleteEmptySpaces() {
        if (this.emptySpaces == 0) return;
        Item[] resizedArray = (Item[]) new Object[this.itemArray.length];
        int j = -1;
        for (int i = 0; i <= this.pointer; i++) {
            Item value = this.itemArray[i];
            if (value == null) continue;
            resizedArray[++j] = value;
        }
        this.pointer = j;
        this.emptySpaces = 0;
        this.emptyIndex = -1;
        this.itemArray = resizedArray;
    }

    public Iterator<Item> iterator() {
        Iterator<Item> iterator = new IterableArrayRandomizedQueue();
        return iterator;
    }

    private class IterableArrayRandomizedQueue implements Iterator<Item> {
        private Item[] shuffledArray;
        private int pointer;

        public IterableArrayRandomizedQueue() {
            if (RandomizedQueue.this.size() < 1) return;
            this.pointer = 0;
            this.shuffledArray = (Item[]) new Object[RandomizedQueue.this.size()];
            int j = -1;
            for (int i = 0; i <= RandomizedQueue.this.pointer; i++) {
                Item value = RandomizedQueue.this.itemArray[i];
                if (value == null) continue;
                this.shuffledArray[++j] = value;
            }
            StdRandom.shuffle(this.shuffledArray);
        }

        public boolean hasNext() {
            boolean hasNext = this.pointer >= 0 && this.pointer <= this.shuffledArray.length - 1;
            return hasNext;
        }

        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException();
            Item returnItem = this.shuffledArray[this.pointer++];
            return returnItem;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        test.enqueue(1);
        test.enqueue(2);
        System.out.println("Queue size: " + test.size());
        System.out.println("Queue empty: " + test.isEmpty());
        System.out.println("Queue sample: " + test.sample());
        for (int i : test) {
            System.out.println("Iterated element: " + i);
        }
        System.out.println("Queue dequeue: " + test.dequeue());
        System.out.println("Queue size: " + test.size());
        System.out.println("Queue empty: " + test.isEmpty());
        System.out.println("Queue sample: " + test.sample());
    }
}
