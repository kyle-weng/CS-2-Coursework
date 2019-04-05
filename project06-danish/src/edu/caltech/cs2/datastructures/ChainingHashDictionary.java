package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDictionary;

import java.util.Iterator;
import java.util.function.Supplier;

public class ChainingHashDictionary<K, V> implements IDictionary<K, V> {
    private static final int INITIAL_CAPACITY = 1009;

    private Supplier<IDictionary<K, V>> chain;
    private IDictionary<K, V>[] arr;
    private int size;
    private double maxLoadFactor = 1.0;

    public ChainingHashDictionary(Supplier<IDictionary<K, V>> supplier) {
        arr = (IDictionary<K, V>[]) new IDictionary[INITIAL_CAPACITY];
        chain = supplier;
        size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % arr.length;
    }

    private IDictionary<K, V> cast(Object o) {
        return ((IDictionary<K, V>) chain.get().getClass().cast(o));
    }

    @Override
    public V get(K key) {
        int hash = hash(key);
        if(arr[hash] == null) {
            return null;
        }
        return cast(arr[hash]).get(key);
    }

    @Override
    public V remove(K key) {
        int hash = hash(key);
        if(arr[hash] == null) {
            return null;
        }
        V temp = cast(arr[hash]).remove(key);
        if (temp != null) {
            size--;
        }
        return temp;
    }

    @Override
    public V put(K key, V value) {
        int hash = hash(key);
        if(arr[hash] == null) {
            arr[hash] = chain.get();
        }
        V temp = cast(arr[hash]).put(key, value);
        if (temp == null) {
            size++;
        }
        //rehash
        if ((double)this.size / arr.length > maxLoadFactor) {
            // rehash();
        }
        return temp;
    }

    /*
    private void rehash() {
        IDictionary<K, V>[] newArr = (IDictionary<K, V>[]) new IDictionary[arr.length * 2 + 1];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = chain.get();
        }
        for (int i = 0; i < arr.length; i++) {
            for (K key : arr[i].keySet()) {
                int hash = hash(key);
                newArr[hash].put(key, arr[i].get(key));
            }
        }
        arr = newArr;
    }
    */

    @Override
    public boolean containsKey(K key) {
        if(arr[hash(key)] == null) {
            return false;
        }
        return arr[hash(key)].containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        // return values().contains(value);
        for (IDictionary<K, V> x : arr) {
            for (V valueToCompare : x.values()) {
                if (valueToCompare.equals(value)) {
                    return true;
                }
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
        ArrayDeque<K> kyS = new ArrayDeque<K>();
        for (IDictionary<K, V> x : arr) {
            if(x == null) {
                continue;
            }
            for (K key : x.keySet()) {
                kyS.add(key);
            }
        }
        return kyS;
    }

    @Override
    public ICollection<V> values() {
        ArrayDeque<V> kyS = new ArrayDeque<V>();
        for (IDictionary<K, V> x : arr) {
            if(x == null) {
                continue;
            }
            for (K key : x.keySet()) {
                kyS.add(x.get(key));
            }
        }
        return kyS;
    }

    @Override
    public Iterator<K> iterator() {
        return this.keySet().iterator();
    }
}
