/* *****************************************************************************
 *  Name: Sharun Garg
 *  Date: April 5th, 2021
 *  Description: Algorithms Part 1 by Princeton University - Week 2 Assignment
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int first = -1;
    private int last = -1;
    private Item[] itemArray;
    private int noOfElements = 0;

    public Deque() {
        this.itemArray = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return this.first == -1;
    }

    public int size() {
        int size = this.noOfElements;
        return size;
    }

    public void addFirst(Item item) {
        this.validateItem(item);
        if (this.first == -1) {
            this.first++;
            this.last++;
            this.itemArray[first] = item;
            this.noOfElements++;
            return;
        }

        if (this.isDequeFull()) this.resizeArray(2 * this.itemArray.length);

        if (this.first == 0) {
            this.first = this.itemArray.length - 1;
            this.itemArray[first] = item;
        }
        else {
            this.itemArray[--this.first] = item;
        }
        this.noOfElements++;
    }

    public void addLast(Item item) {
        this.validateItem(item);
        if (this.last == -1) {
            this.first++;
            this.last++;
            this.itemArray[first] = item;
            this.noOfElements++;
            return;
        }

        if (this.isDequeFull()) this.resizeArray(2 * this.itemArray.length);

        if (this.last == this.itemArray.length - 1) {
            this.last = 0;
            this.itemArray[last] = item;
        }
        else {
            this.itemArray[++this.last] = item;
        }
        this.noOfElements++;
    }

    public Item removeFirst() {
        this.validateDequeHasValue();

        Item firstItem = this.itemArray[this.first];
        this.itemArray[first] = null;
        if (this.first == this.itemArray.length - 1) {
            this.first = 0;
        }
        else {
            this.first++;
        }
        this.noOfElements--;
        if (this.isDequeQuarter()) this.resizeArray(
                this.itemArray.length % 2 != 0 ? (this.itemArray.length / 2) + 1 :
                (this.itemArray.length / 2));


        if (this.noOfElements == 0) {
            this.first = -1;
            this.last = -1;
        }
        return firstItem;
    }

    public Item removeLast() {
        this.validateDequeHasValue();

        Item lastItem = this.itemArray[this.last];
        this.itemArray[last] = null;
        if (this.last == 0) {
            this.last = this.itemArray.length - 1;
        }
        else {
            this.last--;
        }
        this.noOfElements--;
        if (this.isDequeQuarter()) this.resizeArray(
                this.itemArray.length % 2 != 0 ? (this.itemArray.length / 2) + 1 :
                (this.itemArray.length / 2));


        if (this.noOfElements == 0) {
            this.first = -1;
            this.last = -1;
        }
        return lastItem;
    }

    private void validateItem(Item item) {
        if (item == null) throw new IllegalArgumentException();
    }

    private void validateDequeHasValue() {
        if (this.noOfElements == 0) throw new NoSuchElementException();
    }

    private void resizeArray(int quantity) {
        Item[] newArray = (Item[]) new Object[quantity];
        if (this.noOfElements == 0) {
            this.itemArray = (Item[]) new Object[1];
            this.first = -1;
            this.last = -1;
            return;
        }

        if (this.noOfElements == 1) {
            newArray[0] = this.itemArray[first];
            this.itemArray = newArray;
            this.first = 0;
            this.last = 0;
            return;
        }

        if (this.first < this.last) {
            for (int i = this.first, j = 0; i <= this.last; i++, j++) {
                newArray[j] = this.itemArray[i];
            }
        }
        else {
            int j = 0;
            for (int i = this.first; i < this.itemArray.length; i++) {
                newArray[j++] = this.itemArray[i];
            }
            for (int i = 0; i <= this.last; i++) {
                newArray[j++] = this.itemArray[i];
            }
        }
        this.first = 0;
        this.last = this.noOfElements - 1;
        this.itemArray = newArray;
    }

    private boolean isDequeFull() {
        boolean isFull = this.noOfElements == this.itemArray.length;
        return isFull;
    }

    private boolean isDequeQuarter() {
        boolean isQuarter = ((double) this.noOfElements / this.itemArray.length) <= 0.25;
        return isQuarter;
    }

    public Iterator<Item> iterator() {
        Iterator<Item> iterator = new IterableArrayDeque();
        return iterator;
    }

    private class IterableArrayDeque implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            boolean hasNext = i < Deque.this.noOfElements;
            return hasNext;
        }

        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException();

            Item returnItem = Deque.this.itemArray[this.getIndex()];
            return returnItem;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private int getIndex() {
            int index = Deque.this.first + i++;
            if (index >= Deque.this.itemArray.length) {
                index -= Deque.this.itemArray.length;
            }
            return index;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> test = new Deque<Integer>();
        test.addFirst(1);
        test.addLast(2);
        for (int i : test) {
            System.out.println("Iterated element: " + i);
        }
        System.out.println("Queue size: " + test.size());
        System.out.println("Queue empty: " + test.isEmpty());
        System.out.println("Removed first elements: " + test.removeFirst());
        System.out.println("Removed last elements: " + test.removeLast());
    }
}
