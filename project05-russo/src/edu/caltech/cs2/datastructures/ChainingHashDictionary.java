package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDictionary;
import edu.caltech.cs2.textgenerator.NGram;

import java.util.Iterator;
import java.util.function.Supplier;

public class ChainingHashDictionary<K, V> implements IDictionary<K, V> {
    private Supplier<IDictionary<K, V>> chain;
    private IDictionary<K, V>[] arr;
    private int size;
    private double maxLoadFactor = 1.0;

    public ChainingHashDictionary(Supplier<IDictionary<K, V>> supplier) {
        arr = (IDictionary<K, V>[]) new IDictionary[11];
        for (int i = 0; i < 11; i++) {
            arr[i] = supplier.get();
        }
        chain = supplier;
        size = 0;
    }

    @Override
    public V get(K key) {
        return arr[key.hashCode() % arr.length].get(key);
    }

    @Override
    public V remove(K key) {
        V temp = arr[key.hashCode() % arr.length].remove(key);
        //System.out.println(containsKey(key));
        if (temp != null) {
            size--;
        }
        return temp;
    }

    @Override
    public V put(K key, V value) {
        if (arr[key.hashCode() % arr.length].get(key) != value) {
            size++;
        }

        //rehash
        if ((double)this.size / arr.length > maxLoadFactor) {
            rehash();
        }

        return arr[key.hashCode() % arr.length].put(key, value);
    }

    /* hello adam blank */
    private void rehash() {
        IDictionary<K, V>[] newArr = (IDictionary<K, V>[]) new IDictionary[arr.length * 2 + 1];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = chain.get();
        }
        for (int i = 0; i < arr.length; i++) {
            for (K key : arr[i].keySet()) {
                newArr[key.hashCode() % newArr.length].put(key, arr[i].get(key));
            }
        }
        arr = newArr;
    }

    @Override
    public boolean containsKey(K key) {
        return arr[key.hashCode() % arr.length].containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
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
