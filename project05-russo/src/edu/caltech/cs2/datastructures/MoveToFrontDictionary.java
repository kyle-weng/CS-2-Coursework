package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDictionary;

import java.util.Iterator;

public class MoveToFrontDictionary<K, V> implements IDictionary<K,V> {

    private Node head;
    private int size;

    public MoveToFrontDictionary () {
        this.head = null;
        this.size = 0;
    }

    private class Node {
        K key;
        V value;
        Node next;
        Node prev;

        Node(K key, V value, Node next, Node prev) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        Node(K key, V value) {
            this(key, value, null, null);
        }
    }
    @Override
    public V get(K key) {
        if (head == null) {
            return null;
        }

        if (head.key.equals(key)) {
            return head.value; //hello
        }

        Node temp = head;
        while (temp != null && !temp.key.equals(key)) {
            temp = temp.next;
        }
        //case 1: temp = null
        if (temp == null) {
            return null;
        }

        //case 2: temp.key.equals(key)
        if (temp.next != null) {
            temp.next.prev = temp.prev;
        }
        temp.prev.next = temp.next;
        head.prev = temp;
        temp.next = head;
        temp.prev = null;
        head = temp;
        return head.value;
    }

    @Override
    public V remove(K key) {
        V value = get(key);
        if (value != null) {
            this.head = head.next;
            if (head != null)
                this.head.prev = null;
            size--;
        }
        return value;
    }

    /*
    * Adds new node with key and value to front of list.
     */
    @Override
    public V put(K key, V value) {
        /*
        * check if node with key already exists
        * save old value of node
        * remove node
        * add node with key and new value passed in at front of list
        * if node with key DNE then return null and add node with key and new value
         */
        V temp = get(key);
        if (temp == null) {
            head = new Node(key, value, head, null);
            if (size > 0) {
                head.next.prev = head;
            }
            size++;
        }
        else{
            head.value = value;
        }
        return temp;
    }

    @Override
    public boolean containsKey(K key) {
        return this.keySet().contains(key);
    }

    @Override
    public boolean containsValue(V value) {
        return this.values().contains(value);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ICollection<K> keySet() {
        ICollection<K> toReturn = new ArrayDeque<>();
        Node currentNode = this.head;
        while(currentNode != null) {
            toReturn.add(currentNode.key);
            currentNode = currentNode.next;
        }
        return toReturn;
    }

    @Override
    public ICollection<V> values() {
        ICollection<V> toReturn = new ArrayDeque<>();
        Node currentNode = this.head;
        while(currentNode != null) {
            toReturn.add(currentNode.value);
            currentNode = currentNode.next;
        }
        return toReturn;
    }

    @Override
    public Iterator<K> iterator() {
        return this.keySet().iterator();
    }
}
