package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class ArrayDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {
    private E[] arrayDeque;
    private static final int DEFAULT_CAPACITY = 10;
    private static final int GROW_FACTOR = 2;
    private int size;


    public ArrayDeque() {
        this.arrayDeque = (E[])new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public ArrayDeque(int initialCapacity) {
        this.arrayDeque = (E[])new Object[initialCapacity];
        this.size = 0;
    }


    private void ensureCapacity(int size) {
        if (size >= this.arrayDeque.length) {
            E[] newArray = (E[]) new Object[this.arrayDeque.length * GROW_FACTOR];

            for (int i = 0; i < size; i++) {
                newArray[i] = this.arrayDeque[i];
            }
            this.arrayDeque = newArray;
        }
    }

    @Override
    public void addFront(E e) {
        ensureCapacity(this.size);
        for(int i = this.size; i > 0; i--) {
            this.arrayDeque[i] = this.arrayDeque[i-1];
        }
        this.arrayDeque[0] = e;
        this.size++;
    }

    @Override
    public void addBack(E e) {
        ensureCapacity(this.size);
        this.arrayDeque[this.size] = e;
        this.size++;
    }

    @Override
    public E removeFront() {
        if(this.size == 0) {
            return null;
        }
        // this.size = 10
        E frontElement = this.arrayDeque[0];
        for(int i = 0; i < this.size - 1; i++) {
            this.arrayDeque[i] = this.arrayDeque[i+1];
        }
        this.arrayDeque[this.size - 1] = null;
        this.size--;
        return frontElement;
    }

    @Override
    public E removeBack() {
        if(this.size == 0) {
            return null;
        }
        E backElement = this.arrayDeque[this.size - 1];
        this.arrayDeque[this.size - 1] = null;
        this.size--;
        return backElement;
    }

    @Override
    public boolean enqueue(E e) {
        addFront(e);
        return true;
    }

    @Override
    public E dequeue() {
        return removeBack();
    }

    @Override
    public boolean push(E e) {
        ensureCapacity(this.size);
        this.arrayDeque[this.size] = e;
        this.size++;
        return true;
    }

    @Override
    public E pop() {
        if(this.size == 0) {
            return null;
        }
        return removeBack();
    }

    @Override
    public E peek() {
        return peekBack();
    }

    @Override
    public E peekFront() {
        if(this.size == 0) {
            return null;
        }
        return this.arrayDeque[0];
    }

    @Override
    public E peekBack() {
        if(this.size == 0) {
            return null;
        }
        return this.arrayDeque[this.size - 1];
    }


    @Override
    public Iterator<E> iterator() {
        return new DequeIterator();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        if(this.size == 0) {
            return "[]";
        }
        String output = "[";
        for(int i = 0; i < this.size; i++) {
            output += (this.arrayDeque[i] + ", ");
        }
        output = output.substring(0, output.length() - 2);
        return output + "]";
    }

    private class DequeIterator implements Iterator<E> {
        private int currIndex = 0;

        @Override
        public boolean hasNext() {
            return currIndex < ArrayDeque.this.size;
        }

        @Override
        public E next() {
            E result = ArrayDeque.this.arrayDeque[this.currIndex];
            this.currIndex++;
            return result;
        }
    }
}
