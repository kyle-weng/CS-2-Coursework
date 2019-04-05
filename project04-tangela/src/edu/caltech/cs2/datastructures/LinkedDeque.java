package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class LinkedDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {
    private Node head;
    private Node tail;
    int size = 0;

    public LinkedDeque() {
        head = null;
        tail = null;
    }

    private class Node {
        private E data;
        private Node next;
        private Node prev;

        public Node(E data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private class deqIterator implements Iterator<E> {
        Node it;
        E val;

        public deqIterator() {
            it = LinkedDeque.this.head;
        }

        public boolean hasNext() {
            return it != null;
        }

        public E next() {
            val = it.data;
            it = it.next;
            return val;
        }
    }

    @Override
    public void addFront(E e) {
        if (head == null) {
            head = new Node(e, null, null);
            tail = head;
        } else if (size == 1) {
            head = new Node(e, this.tail, null);
            tail.prev = head;
        } else {
            Node tmp = new Node(e, this.head, null);
            head.prev = tmp;
            head = tmp;
        }
        size++;
    }

    @Override
    public void addBack(E e) {
        if (head == null) {
            addFront(e);
        }
        else {
            tail.next = new Node(e, null, tail);
            tail = tail.next;
            size++;
        }
    }

    @Override
    public E removeFront() {
        if(this.size == 0) {
            return null;
        } else if(this.size == 1) {
            E var = this.head.data;
            this.head = null;
            this.tail = null;
            this.size--;
            return var;
        } else {
            E var = this.head.data;
            this.head = this.head.next;
            this.head.prev = null;
            this.size--;
            return var;
        }
    }

    @Override
    public E removeBack() {
        if (this.size == 0) {
            return null;
        } else if (this.size == 1) {
            return removeFront();
        } else {
            E var = this.tail.data;
            this.tail = this.tail.prev;
            this.tail.next = null;
            this.size--;
            return var;
        }
    }


    @Override
    public boolean enqueue(E e) {
        addFront(e);
        return true;
    }

    @Override
    public E dequeue() {
        if (size == 0) {
            return null;
        }
        else {
            return removeBack();
        }
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
        if (head != null) {
            return head.data;
        }
        return null;
    }

    @Override
    public E peekBack() {
        if (size == 1) {
            return head.data;
        }
        else if(size == 0) {
            return null;
        }
        return tail.data;
    }

    @Override
    public Iterator<E> iterator() {
        return new deqIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        if (size == 0 || head == null) {
            return "[]";
        }

        String str = "[";
        Node it = head;
        while (it != null) {
            if (it.next != null) {
                str += it.data;
                if (it.next.data != null) {
                    str += ", ";
                }
            }
            it = it.next;
        }
        if (tail != null) {
            str += tail.data;
        }
        return str += "]";
    }
}
