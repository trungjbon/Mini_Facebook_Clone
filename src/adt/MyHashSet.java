package adt;

import java.io.Serializable;

public class MyHashSet<T> implements Serializable {
    private final MyHashMap<T, Object> set;

    public MyHashSet() {
        this.set = new MyHashMap<>();
    }

    public void add(T data) {
        this.set.put(data, null);
    }

    public boolean contains(T data) {
        return this.set.containsKey(data);
    }

    public void remove(T data) {
        this.set.remove(data, null);
    }

    public MyArrayList<T> dataList() {
        return this.set.getKeyList();
    }

    public int size() {
        return this.set.getSize();
    }
}
