package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class LinkedDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {
    private LinkedNode head;
    private LinkedNode tail;
    private int size;


    public LinkedDeque() {
        this.head = null;
        this.tail = null;
        this.size = 0;

    }
    private class LinkedNode  {
        E data;
        LinkedNode next;
        LinkedNode previous;

        public LinkedNode(E data) {
            this(data, null, null);
        }

        public LinkedNode(E data, LinkedNode previous, LinkedNode next) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
    }

    @Override
    public void addFront(E e) {
        LinkedNode newNode = new LinkedNode(e, null, this.head);
        if(this.size == 0) {
            this.tail = newNode;
        }
        else {
            this.head.previous = newNode;
        }
        this.head = newNode;
        this.size++;
    }

    @Override
    public void addBack(E e) {
        LinkedNode newNode = new LinkedNode(e, this.tail, null);
        if(this.size == 0) {
            this.head = newNode;
        }
        else {
            this.tail.next = newNode;
        }
        this.tail = newNode;
        this.size++;
    }

    @Override
    public E removeFront() {
        if(this.size > 1) {
            LinkedNode firstNode = this.head;
            this.head = firstNode.next;
            firstNode.next = null;
            this.head.previous = null;
            this.size--;
            return firstNode.data;
        }
        else if (this.size == 1) {
            LinkedNode onlyNode = this.head;
            this.head = null;
            this.tail = null;
            this.size--;
            return onlyNode.data;
        }
        return null;
    }

    @Override
    public E removeBack() {
        if(this.size > 1) {
            LinkedNode lastNode = this.tail;
            this.tail = lastNode.previous;
            this.tail.next = null;
            lastNode.previous = null;
            this.size--;
            return lastNode.data;
        }
        else if (this.size == 1) {
            LinkedNode onlyNode = this.tail;
            this.head = null;
            this.tail = null;
            this.size--;
            return onlyNode.data;
        }
        return null;
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
        if(this.size == 0) {
            return null;
        }
        return this.head.data;
    }

    @Override
    public E peekBack() {
        if(this.size == 0) {
            return null;
        }
        return this.tail.data;
    }

    @Override
    public Iterator<E> iterator() {
        return new linkedIterator();

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
        LinkedNode currNode = this.head;
        while(currNode != null) {
            output += (currNode.data + ", ");
            currNode = currNode.next;
        }
        output = output.substring(0, output.length() - 2);
        return output + "]";
    }

    private class linkedIterator implements Iterator<E> {
        private LinkedNode currNode = LinkedDeque.this.head;
        private int numNodesPassed = 0;

        @Override
        public boolean hasNext() {
            if(currNode != null) {
                return this.numNodesPassed < LinkedDeque.this.size;
            }
            return false;
        }

        @Override
        public E next() {
            if(hasNext()) {
                E result = this.currNode.data;
                this.currNode = this.currNode.next;
                this.numNodesPassed++;
                return result;
            }
            return null;
        }
    }
}
