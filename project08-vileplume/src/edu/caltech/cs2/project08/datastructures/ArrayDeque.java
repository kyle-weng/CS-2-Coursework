package edu.caltech.cs2.project08.datastructures;

import edu.caltech.cs2.project08.interfaces.IDeque;
import edu.caltech.cs2.project08.interfaces.IQueue;
import edu.caltech.cs2.project08.interfaces.IStack;

import java.util.Iterator;

public class ArrayDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {

    /*
     * convention used in this code:
     * front = index 0
     * top = index 0
     */

    private static final int defaultCapacity = 10;
    private static final int growFactor = 2;
    private E[] data;
    private int size;

    public ArrayDeque() {
        this(defaultCapacity);
    }

    public ArrayDeque(int initialCapacity) {
        this.data = (E[]) new Object[initialCapacity];
        this.size = 0;
    }

    @Override
    public void addFront(E e) {

        //grow function
        if (this.size >= this.data.length) {
            E[] data_new = (E[]) new Object[this.size * growFactor];
            for (int i = 0; i < this.size; i++) {
                data_new[i] = this.data[i];
            }
            this.data = data_new;
        }

        //shift function
        for (int i = this.size; i > 0; i--) {
            this.data[i] = this.data[i - 1];
        }

        //replace function
        this.data[0] = e;

        //increment
        this.size++;
    }

    @Override
    public void addBack(E e) {

        //grow function
        if (this.size >= this.data.length) {
            E[] data_new = (E[]) new Object[size() * growFactor];
            for (int i = 0; i < this.size; i++) {
                data_new[i] = this.data[i];
            }
            this.data = data_new;
        }

        //replace function
        this.data[this.size] = e;

        //increment
        this.size++;
    }

    @Override
    public E removeFront() {

        //size checker function
        if (this.size == 0) {
            return null;
        }

        //shift function
        E temp = this.data[0];
        for (int i = 1; i < this.size; i++) {
            this.data[i - 1] = this.data[i];
        }
        this.data[this.size - 1] = null;

        //decrement
        this.size--;

        //return function
        return temp;

    }

    @Override
    public E removeBack() {

        //size checker function
        if (this.size == 0) {
            return null;
        }

        //return function
        E temp = this.data[this.size - 1];
        //this.data[this.size] = null;

        //decrement
        this.size--;

        return temp;
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
        addBack(e);
        return true;
    }

    @Override
    public E pop() {
        return removeBack();
    }

    @Override
    public E peek() {
        return peekBack();
    }

    @Override
    public E peekFront() {
        //size checker function
        if (this.size == 0) {
            return null;
        }
        return this.data[0];
    }

    @Override
    public E peekBack() {
        //size checker function
        if (this.size == 0) {
            return null;
        }
        return this.data[this.size - 1];
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public int size() {
        return this.size;
    }

    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        }

        String result = "[";
        for (int i = 0; i < this.size; i++) {
            result += this.data[i] + ", ";
        }

        result = result.substring(0, result.length() - 2);
        return result + "]";
    }

    private class ArrayDequeIterator implements Iterator<E> {

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < ArrayDeque.this.size();
        }

        @Override
        public E next() {
            E result = ArrayDeque.this.data[this.currentIndex];
            this.currentIndex++;
            return result;
        }
    }
}
