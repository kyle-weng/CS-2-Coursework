package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IGraph;
import edu.caltech.cs2.interfaces.ISet;


public class Graph<V, E> extends IGraph<V, E> {

    private ChainingHashDictionary<V, ChainingHashDictionary<V, E>> listOfDicts;

    public Graph() {
        this.listOfDicts = new ChainingHashDictionary<>(MoveToFrontDictionary::new);
    }

    @Override
    public boolean addVertex(V vertex) {
        if (this.listOfDicts.containsKey(vertex)) {
            return false;
        }
        this.listOfDicts.put(vertex, new ChainingHashDictionary<>(MoveToFrontDictionary::new));
        return true;
    }

    @Override
    public boolean addEdge(V src, V dest, E e) {
        if(!this.listOfDicts.containsKey(src) || !this.listOfDicts.containsKey(dest)) {
            throw new IllegalArgumentException();
        } else {
            E edge = this.listOfDicts.get(src).put(dest, e);
            return edge == null;
        }
    }

    @Override
    public boolean addUndirectedEdge(V src, V dest, E e) {
        boolean forward = addEdge(src, dest, e);
        boolean backward = addEdge(dest, src, e);
        return forward && backward;
    }

    @Override
    public boolean removeEdge(V src, V dest) {
        if(!this.listOfDicts.containsKey(src) || !this.listOfDicts.containsKey(dest)) {
            throw new IllegalArgumentException();
        } else {
            if(this.listOfDicts.get(src).containsKey(dest)) {
                this.listOfDicts.get(src).remove(dest);
                return true;
            }
            return false;
        }
    }

    @Override
    public ISet<V> vertices() {
        return this.listOfDicts.keySet();
    }


    @Override
    public E adjacent(V i, V j) {
        if(!this.listOfDicts.containsKey(i) || !this.listOfDicts.containsKey(j)) {
            throw new IllegalArgumentException();
        } else {
            return this.listOfDicts.get(i).get(j);
        }
    }

    /**
     * Return the neighbours of a given vertex when this graph is treated as
     * DIRECTED; that is, vertices to which vertex has an outgoing edge.
     *
     * @param vertex The vertex the neighbours of which to return.
     * @throws IllegalArgumentException if vertex is not in the graph.
     * @return The set of neighbors of vertex.
     */
    @Override
    public ISet<V> neighbors(V vertex) {
        if(!this.listOfDicts.containsKey(vertex)) {
            throw new IllegalArgumentException();
        }
        return this.listOfDicts.get(vertex).keySet();
    }
}