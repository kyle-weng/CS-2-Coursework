package edu.caltech.cs2.sorts;

import edu.caltech.cs2.datastructures.MinFourHeap;
import edu.caltech.cs2.interfaces.IPriorityQueue;

public class TopKSort {
    /**
     * Sorts the largest K elements in the array in descending order.
     * @param array - the array to be sorted; will be manipulated.
     * @param K - the number of values to sort
     * @param <E> - the type of values in the array
     */
    public static <E> void sort(IPriorityQueue.PQElement<E>[] array, int K) {
        if (K < 0) {
            throw new IllegalArgumentException("K cannot be negative!");
        }
        if(K == 0) {
            for(int i = 0; i < array.length; i++) {
                array[i] = null;
            }
            return;
        }
        if(array == null) {
            return;
        }
        MinFourHeap<E> heap = new MinFourHeap<>();

        // enqueue first K elements
        for(int i = 0; i < K && i < array.length; i++) {
            heap.enqueue(array[i]);
        }
        // check each subsequent element for priority and see if we should add it in
        // determine if we add it in by seeing if arr[i]'s priority > lower priority in heap
        for(int i = K; i < array.length; i++) {
            if(heap.peek() != null && heap.peek().priority < array[i].priority) {
                heap.dequeue();
                heap.enqueue(array[i]);
            }
        }

        // change array to have first K elements as the greatest values and everything else as null
        for(int i = array.length - 1; i >= 0; i--) {
            if(i >= K) {
                array[i] = null;
            } else {
                array[i] = heap.dequeue();
            }
        }
        
    }
}
