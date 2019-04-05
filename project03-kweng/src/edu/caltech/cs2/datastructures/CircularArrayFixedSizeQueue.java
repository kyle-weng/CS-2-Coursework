package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IFixedSizeQueue;

import java.util.Iterator;

public class CircularArrayFixedSizeQueue<E> implements IFixedSizeQueue<E> {

    private E[] arr;
    private int front, back;

    public CircularArrayFixedSizeQueue(int capacity) {
        arr = (E[]) new Object[capacity];
        front = 0;
        back = 0;
    }
    @Override
    public boolean isFull() {
        return (size() == arr.length);
    }

    @Override
    public int capacity() {
        return arr.length;
    }

    @Override
    public boolean enqueue(E e) {
        if (isFull())
            return false;
        arr[back] = e;
        back++;
        back %= arr.length;
        return true;
    }

    @Override
    public E dequeue() {
        if (size() == 0) {
            return null;
        }
        E temp = arr[front];
        arr[front] = null;
        front++;
        front %= arr.length;
        return temp;
    }

    @Override
    public E peek() {
        if (size() == 0) {
            return null;
        }
        return arr[front];
    }

    @Override
    public int size() {
        /*
        System.out.println("array length is " + arr.length);
        System.out.println("front index is " + front);
        System.out.println("back index is " + back);
        */
        if (front == back && arr[(back + 1) % arr.length] == null) {
            //System.out.println("size is 0\n");
            return 0;
        }
        else if (front == back && arr[(back + 1) % arr.length] != null){
            //System.out.println("size is " + arr.length + "\n");
            return arr.length;
        }
        if (front < back) {
            //System.out.println("size is " + (back - front) + "\n");
            return back - front;
        }
        if (front > back) {
            //System.out.println("size is " + (arr.length - front + back +
            // "\n");
            return (arr.length - front + back);
        }
        return 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new CircularArrayQueueIterator();
    }

    public String toString() {
        if (front == back) {
            return "[]";
        }
        String ret = "[";
        if (front < back) {
            //iterate straight through
            for (int i = front; i < back; i++) {
                ret += arr[i] + ", ";
            }
            ret = ret.substring(0, ret.length() - 2);
            ret += "]";
            return ret;
        }
        if (front > back) {
            //iterate to "end" of array first, then from "beginning"
            for (int i = front; i < arr.length; i++) {
                ret += arr[i] + ", ";
            }
            for (int i = 0; i < back; i++) {
                ret += arr[i] + ", ";
            }
            ret = ret.substring(0, ret.length() - 2);
            ret += "]";
            return ret;
        }
        return "";
    }

    private class CircularArrayQueueIterator implements Iterator<E>{
        private int currentIndex = CircularArrayFixedSizeQueue.this.front;
        private int count = 0;

        @Override
        public boolean hasNext() {
            return (this.count < CircularArrayFixedSizeQueue.this.size() && CircularArrayFixedSizeQueue.this.arr[(this.currentIndex + 1) % CircularArrayFixedSizeQueue.this.capacity()] != null);
        }

        public E next() {
            if (!hasNext()) {
                return null;
            }
            E temp = CircularArrayFixedSizeQueue.this.arr[currentIndex];
            currentIndex++;
            count++;
            currentIndex %= arr.length;

            return temp;
        }
    }
}
