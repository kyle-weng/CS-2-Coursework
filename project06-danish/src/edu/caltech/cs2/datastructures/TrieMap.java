package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.ITrieMap;

import java.util.function.Function;
import java.util.Iterator;

public class TrieMap<A, K extends Iterable<A>, V> extends ITrieMap<A, K, V> {
    private int size;
    private LinkedDeque<K> keySet = new LinkedDeque<>();

    public TrieMap(Function<IDeque<A>, K> collector) {
        super(collector);
    }

    @Override
    public boolean isPrefix(K key) {
        TrieNode<A, V> currentNode = this.root;
        for(A character: key) {
            if(!currentNode.pointers.containsKey(character)) {
                return false;
            }
            else {
                currentNode = currentNode.pointers.get(character);
            }
        }
        return true;
    }

    private IDeque<V> getCompletions(TrieNode<A, V> node) {
        IDeque<V> returnDeque = new ArrayDeque<>();
        if (node.pointers.size() == 0) {
            if (node.value != null) {
                returnDeque.addBack(node.value);
            }
            return returnDeque;
        } else {
            for (A character: node.pointers.keySet()) {
                IDeque<V> anotherDeque = getCompletions(node.pointers.get(character));
                returnDeque.addAll(anotherDeque);
            }
            if (node.value != null) {
                returnDeque.addFront(node.value);
            }
        }
        return returnDeque;
    }

    @Override
    public IDeque<V> getCompletions(K prefix) {
        IDeque<V> returnDeque;
        TrieNode<A, V> currentNode = this.root;
        for (A character : prefix) {
            if (currentNode.pointers.keySet().contains(character)) {
                currentNode = currentNode.pointers.get(character);
            } else {
                return new ArrayDeque<>();
            }
        }
        // Currently at the desired node
        // Want to search through all next possible nodes
        returnDeque = getCompletions(currentNode);

        return returnDeque;
    }

    @Override
    public V get(K key) {
        TrieNode<A, V> currentNode = this.root;
        for(A character: key) {
            if(currentNode.pointers.containsKey(character)) {
                currentNode = currentNode.pointers.get(character);
            }
            else {
                return null;
            }
        }
        return currentNode.value;
    }

    private TrieNode<A, V> remove(Iterator<A> iter, TrieNode<A, V> node){
        TrieNode<A, V> anotherNode;
        if (node == null) {
            return null;
        }
        if (!iter.hasNext()) {
            if (node.value != null) {
                this.size--;
            }
            if (node.pointers.size() == 0) {
                return null;
            }
            node.value = null;
            return node;
        } else {
            A key = iter.next();
            anotherNode = remove(iter, node.pointers.get(key));
            if (anotherNode == null) {
                node.pointers.remove(key);
            }
            if (node.pointers.size() == 0 && node.value == null) {
                return null;
            }
            return node;
        }
    }

    @Override
    public V remove(K key) {
        V toRemove = get(key);
        remove(key.iterator(), this.root);
        return toRemove;
    }

    @Override
    public V put(K key, V value) {
        TrieNode<A, V> currentNode = this.root;
        for (A character: key) {
            if (currentNode.pointers.containsKey(character)) {
                currentNode = currentNode.pointers.get(character);
            }
            else {
                TrieNode<A, V> newNode = new TrieNode<>();
                currentNode.pointers.put(character, newNode);
                currentNode = newNode;
            }
        }
        if (!containsKey(key)) {
            this.size++;
            this.keySet.enqueue(key);
        }
        currentNode.value = value;
        return value;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        IDeque<V> heyThereHowAreYouDoing = getCompletions(this.root);
        for (V certainValues : heyThereHowAreYouDoing) {
            if (certainValues == value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ICollection<K> keySet() {
        return this.keySet;
    }

    @Override
    public ICollection<V> values() {
        LinkedDeque<V> valueSet = new LinkedDeque<>();
        for (K key : this.keySet) {
            V value = get(key);
            if (!valueSet.contains(value)) {
                valueSet.enqueue(value);
            }
        }
        return valueSet;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
