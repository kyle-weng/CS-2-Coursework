package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class ArrayDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {
    private E[] arr;
    private int contents = 0;
    private final static int DEFAULTCAP = 10;
    private final static int GROWTHFACTOR = 2;

    public ArrayDeque() {
        this(DEFAULTCAP);
    }

    public ArrayDeque(int initialCapacity) {
        this.arr = (E[])new Object[initialCapacity];
    }

    @Override
    public void addFront(E e) {
        ensureCap();
        arr[this.contents] = e;
        contents++;
    }

    @Override
    public void addBack(E e) {
        ensureCap();
        for (int i = contents - 1; i >= 0; i--) {
            arr[i+1] = arr[i];
        }
        arr[0] = e;
        contents++;
    }

    @Override
    public E removeFront() {
        if (contents == 0) {
            return null;
        } else {
            E var = arr[contents-1];
            arr[contents-1] = null;
            contents--;
            return var;
        }
    }

    @Override
    public E removeBack() {
        if (contents == 0) {
            return null;
        } else {
            E var = arr[0];
            contents--;
            for (int i = 0; i < arr.length - 1; i++) {
                arr[i] = arr[i+1];
            }
            return var;
        }
    }

    @Override
    public boolean enqueue(E e) {
        addBack(e);
        return true;
    }

    @Override
    public E dequeue() {
        return removeFront();
    }

    @Override
    public boolean push(E e) {
        addFront(e);
        return true;
    }

    @Override
    public E pop() {
        return removeFront();
    }

    @Override
    public E peek() {
        if (contents == 0) {
            return null;
        }
        if (arr[contents-1] != null) {
            return arr[contents-1];
        }
        return null;
    }

    @Override
    public E peekFront() {
        return peek();
    }

    @Override
    public E peekBack() {
        return arr[0];
    }

    @Override
    public Iterator<E> iterator() {
        return new deqIterator();
    }

    class deqIterator implements Iterator<E> {
        private int cursor = 0;
        private E next;

        public deqIterator() {
            next = arr[0];
        }

        public boolean hasNext() {
            if (cursor <= arr.length && arr[cursor] != null) {
                next = arr[cursor];
                return true;
            }
            return false;
        }

        public E next() {
            E var = next;
            cursor++;
            return var;
        }
    }

    @Override
    public int size() {
        return this.contents;
    }

    @Override
    public String toString() {
        if (contents == 0) {
            return "[]";
        }
        String str = "[";
        for (int i = this.contents - 1; i > 0; i--) {
            if (arr[i] != null) {
                str += arr[i] + ", ";
            }
        }
        str += arr[0] + "]";
        return str;
    }

    private void ensureCap() {
        if (this.contents == arr.length) {
            E[] tmpArr = (E[])new Object[GROWTHFACTOR * arr.length];
            for (int i = 0; i < this.contents; i++) {
                tmpArr[i] = arr[i];
            }
            this.arr = tmpArr;
        }
    }
}
