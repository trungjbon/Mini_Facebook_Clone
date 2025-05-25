package adt;

import java.io.Serializable;

public class MyHashMap<K, V> implements Serializable {
    static class ArrEntry<Key, Value> {
        Key key;
        Value value;

        public ArrEntry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private final MyArrayList<ArrEntry<K, V>>[] map;
    private int size;
    private final int defaultSize = 100;
    private final MyArrayList<K> keyList;

    public MyHashMap() {
        this.map = new MyArrayList[defaultSize];
        for (int i = 0; i < defaultSize; i++) {
            this.map[i] = new MyArrayList<>();
        }
        this.size = 0;
        this.keyList = new MyArrayList<>();
    }

    public void put(K key, V value) {
        int index = hash(key);
        if (containsKey(key)) {
            MyArrayList<ArrEntry<K, V>> temp = this.map[index];
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).key.equals(key)) {
                    temp.set(i, new ArrEntry<>(key, value));
                    break;
                }
            }
        } else {
            this.map[index].add(new ArrEntry<>(key, value));
            this.size++;
            this.keyList.add(key);
        }
    }

    public boolean containsKey(K key) {
        int index = hash(key);
        MyArrayList<ArrEntry<K, V>> temp = this.map[index];
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public V get(K key) {
        int index = hash(key);
        MyArrayList<ArrEntry<K, V>> temp = this.map[index];
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).key.equals(key)) {
                return temp.get(i).value;
            }
        }
        return null;
    }

    public void remove(K key, V value) {
        int index = hash(key);
        MyArrayList<ArrEntry<K, V>> temp = this.map[index];
        temp.remove(new ArrEntry<>(key, value));
    }

    public int getSize() {
        return size;
    }

    public V getOrDefault(K key, V value) {
        if (containsKey(key)) {
            return get(key);
        }
        return value;
    }

    public MyArrayList<K> getKeyList() {
        return keyList;
    }

    private int hash(K key) {
        return key.hashCode() % defaultSize;
    }
}