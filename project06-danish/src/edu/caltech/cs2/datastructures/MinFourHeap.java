package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDictionary;
import edu.caltech.cs2.interfaces.IPriorityQueue;

import java.util.Iterator;

public class MinFourHeap<E> implements IPriorityQueue<E> {

    private static final int DEFAULT_CAPACITY = 5;

    private final int GROWTHFACTOR = 2;

    private int size;
    private PQElement<E>[] data;
    private IDictionary<E, Integer> keyToIndexMap;

    /**
     * Creates a new empty heap with DEFAULT_CAPACITY.
     */
    public MinFourHeap() {
        this.size = 0;
        this.data = new PQElement[DEFAULT_CAPACITY];
        this.keyToIndexMap = new ChainingHashDictionary<>(MoveToFrontDictionary::new);
    }

    @Override
    public void increaseKey(PQElement<E> key) {
        // key passed in has a data and a priority
        // look through map and find element whose data matches key's data
        // corresponding value = index
        // go to this index in the array, set this to be key passed in
        // percolate down
        if(!keyToIndexMap.containsKey(key.data)) {
            throw new IllegalArgumentException();
        }
        int index = this.keyToIndexMap.get(key.data);
        data[index] = new PQElement<>(key.data, key.priority);
        percolateDown(index);

    }

    @Override
    public void decreaseKey(PQElement<E> key) {
        // key passed in has a data and a priority
        // look through map, find element whose data matches key's data
        // corresponding value = index
        // go to this index in the array, set this to be key passed in
        // percolate up since value now has too small of a priority
        // too low priority = percolate up --> start at bottom and work way up
        // too high priority = percolate down --> start at top and work way down
        if(!keyToIndexMap.containsKey(key.data)) {
            throw new IllegalArgumentException();
        }
        int index = this.keyToIndexMap.get(key.data);
        data[index] = new PQElement<>(key.data, key.priority);
        percolateUp(index);
    }

    @Override
    public boolean enqueue(PQElement<E> epqElement) {
        if(this.keyToIndexMap.containsKey(epqElement.data)) {
            throw new IllegalArgumentException();
        }
        if (size == data.length) {
            resize();
        }
        this.keyToIndexMap.put(epqElement.data, this.size);
        this.data[size] = epqElement;
        percolateUp(this.size);
        this.size++;
        return true;
    }

    @Override
    public PQElement<E> dequeue() {
        if(this.size == 0) {
            return null;
        }
        PQElement<E> temp = data[0];
        keyToIndexMap.remove(data[0].data);
        this.size--;
        data[0] = data[size];
        keyToIndexMap.put(data[0].data, 0);
        data[size] = null;
        percolateDown(0);
        return temp;
    }

    private int parent(int index) {
        return (int)Math.floor((index - 1) / 4);
    }

    private int child(int index, int childIndex) {
        return (4 * index + childIndex);
    }

    private void resize() {
        PQElement<E>[] tempData = new PQElement[data.length * 2];
        for (int i = 0; i < size; i++) {
            tempData[i] = data[i];
        }
        for (int i = size; i < tempData.length; i++) {
            tempData[i] = null;
        }
        data = tempData;
    }

    private void percolateUp(int hole) {
        while (hole > 0 && data[hole].priority < data[parent(hole)].priority) {
            swap(hole, parent(hole));
            hole = parent(hole);
        }
    }


    private void percolateDown(int hole) {

        int j = hole * 4 + 1;
        if(j >= size) {
            return;
        }
        // find smallest child
        for(int k = hole * 4 + 2; k < (hole * 4 + 5) && k < size; k++) {
            if(data[k].priority < data[j].priority) {
                j = k;
            }
        }
        // do the swap
        if(data[hole].priority > data[j].priority) {
            swap(hole, j);
            percolateDown(j);
        }

    }



    @Override
    public PQElement<E> peek() {
        return data[0];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<PQElement<E>> iterator() {
        return null;
    }

    private void swap(int first, int second) {
        this.keyToIndexMap.put(data[first].data, second);
        this.keyToIndexMap.put(data[second].data, first);

        PQElement<E> temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }
}