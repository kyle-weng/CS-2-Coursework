package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.ITrieMap;

import java.util.function.Function;
import java.util.Iterator;

public class TrieMap<A, K extends Iterable<A>, V> extends ITrieMap<A, K, V> {
    private TrieNode root = new TrieNode<A, V>();
    private LinkedDeque<K> keySet = new LinkedDeque<>();
    private int size = 0;

    public TrieMap(Function<IDeque<A>, K> collector) {
        super(collector);
    }

    @Override
    public boolean isPrefix(K key) {
        TrieNode<A, V> tmp = root;
        for (A i: key) {
            if (tmp != null && tmp.pointers.keySet().contains(i)) {
                tmp = tmp.pointers.get(i);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public IDeque<V> getCompletions(K prefix) {
        TrieNode<A, V> tmp = root;
        IDeque<V> vals = new LinkedDeque<>();
        if (!isPrefix(prefix)) {
            return vals;
        }

        for (A i: prefix) {
            if (tmp != null && tmp.pointers.keySet().contains(i)) {
                tmp = tmp.pointers.get(i);
            }
        }
        if (tmp.value != null) {
            vals.add(tmp.value);
        }

        getCompletions(tmp, vals);
        return vals;
    }

    private void getCompletions(TrieNode<A, V> node, IDeque<V> deq) {
        for (TrieNode<A, V> n: node.pointers.values()) {
            if (n.value != null) {
                deq.add(n.value);
            }
            getCompletions(n, deq);
        }
    }

    @Override
    public V get(K key) {
        TrieNode<A, V> tmp = root;
        for (A i: key) {
            if (tmp != null && tmp.pointers.keySet().contains(i)) {
                tmp = tmp.pointers.get(i);
            } else {
                return null;
            }
        }
        return tmp.value;
    }

    @Override
    public V remove(K key) {
        /*
        TrieNode<A, V> tmp = root;
        LinkedDeque<TrieNode<A, V>> nodeDeq = new LinkedDeque<>();
        V value = get(key);
        for (A i: key) {
            if (tmp != null && tmp.pointers.keySet().contains(i)) {
                nodeDeq.add(tmp);
                tmp = tmp.pointers.get(i);
            }
        }
        for (TrieNode<A, V> n: nodeDeq) {
            IDeque<V> values = new LinkedDeque<>();
        }
        return value;
        */
        V toRemove = get(key);
        remove(key.iterator(), this.root);
        return toRemove;
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
    public V put(K key, V value) {
        /*
        TrieNode<A, V> tmp = root;
        for (A i: key) {
            if (!(tmp.pointers.keySet().contains(i))) {
                tmp.pointers.put(i, new TrieNode<>());
            }
            tmp = tmp.pointers.get(i);
        }
        if (tmp.value == null) {
            size++;
        }
        tmp.value = value;
        return value;
        */
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
        if (get(key) == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean containsValue(V value) {
        if (values().contains(value)) {
            return true;
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
        ICollection<V> vals = new LinkedDeque<>();
        vals = values(root, vals);
        return vals;
    }

    private ICollection<V> values(TrieNode<A, V> node, ICollection<V> collection) {
        /*
        for (TrieNode<A, V> n: node.pointers.values()) {
            collection.add(n.value);
            values(n, collection);
        }
        */
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
        return this.keySet.iterator();
    }
}