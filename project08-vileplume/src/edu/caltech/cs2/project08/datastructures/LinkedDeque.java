package edu.caltech.cs2.project08.datastructures;

import edu.caltech.cs2.project08.interfaces.IDeque;
import edu.caltech.cs2.project08.interfaces.IQueue;
import edu.caltech.cs2.project08.interfaces.IStack;

import java.util.Iterator;

public class LinkedDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {
        public final E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(E data) {
            this(data, null, null);
        }
        public Node (E data, Node<E> next, Node<E> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    public LinkedDeque() {
        this.size = 0;
        this.head = new Node<>(null);
        this.tail = new Node<>(null);
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    @Override
    public void addFront(E e) {
        Node<E> curr = this.head;
        Node<E> oldNext = this.head.next;
        Node<E> additionalNode = new Node<>(e);
        oldNext.prev = additionalNode;
        additionalNode.next = oldNext;
        curr.next = additionalNode;
        additionalNode.prev = curr;
        this.size++;
    }

    @Override
    public void addBack(E e) {
        Node<E> curr = this.tail;
        Node<E> oldPrevious = this.tail.prev;
        Node<E> additionalNode = new Node<>(e);
        oldPrevious.next = additionalNode;
        additionalNode.prev = oldPrevious;
        curr.prev = additionalNode;
        additionalNode.next = curr;
        this.size++;
    }

    @Override
    public E removeFront() {
        if (this.size == 0) {
            return null;
        }
        Node<E> deletThis = this.head.next;
        Node<E> next = deletThis.next;
        E front = deletThis.data;
        this.head.next = next;
        next.prev = this.head;
        this.size--;
        return front;
    }

    @Override
    public E removeBack() {
        if (this.size == 0) {
            return null;
        }
        Node<E> deletThis = this.tail.prev;
        Node<E> prev = deletThis.prev;
        E back = deletThis.data;
        this.tail.prev = prev;
        prev.next = this.tail;
        this.size--;
        return back;
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
        return this.tail.prev.data;
    }

    @Override
    public E peekFront() {
        return this.head.next.data;
    }

    @Override
    public E peekBack() {
        return peek();
    }

    private class LinkedDequeIterator implements Iterator<E> {
        LinkedDeque.Node<E> curr = LinkedDeque.this.head;

        public boolean hasNext() {
            return curr.next != LinkedDeque.this.tail;
        }

        public E next() {
            E result = this.curr.next.data;
            this.curr = this.curr.next;
            return result;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedDequeIterator();
    }

    @Override
    public int size() {
        return this.size;
    }

    public String toString() {
        if (this.size == 0) {
            return "[]";
        }

        Node<E> curr = this.head.next;
        String result = "";
        while(curr != null && curr.next != null) {
            result += curr.data + ", ";
            curr = curr.next;
        }
        return "[" + result.substring(0, result.length() - 2) + "]";
    }
}
