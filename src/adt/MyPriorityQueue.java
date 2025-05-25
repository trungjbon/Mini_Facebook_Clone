package adt;

import java.io.Serializable;

public class MyPriorityQueue<K extends Comparable<K>, E> implements Serializable {
    static class ArrEntry<Key, Value> {
        private final Key key;
        private final Value element;

        public ArrEntry(Key key, Value element) {
            this.key = key;
            this.element = element;
        }

        public Key getKey() {
            return this.key;
        }

        public Value getValue() {
            return this.element;
        }
    }

    private ArrEntry<K, E>[] heapPQ;
    private int n;
    private int defaultSize = 100;

    public MyPriorityQueue() {
        this.heapPQ = new ArrEntry[defaultSize];
        this.n = 0;
    }

    public boolean isEmpty() {
        return this.n == 0;
    }

    protected int parent(int i) {
        return (i - 1) / 2;
    }

    protected int left(int i) {
        return 2 * i + 1;
    }

    protected int right(int i) {
        return 2 * i + 2;
    }

    protected boolean hasLeft(int i) {
        return left(i) < this.n;
    }

    protected boolean hasRight(int i) {
        return right(i) < this.n;
    }

    protected void swap(int i, int j) {
        ArrEntry<K, E> temp = this.heapPQ[i];
        this.heapPQ[i] = this.heapPQ[j];
        this.heapPQ[j] = temp;
    }

    // Min heap
    // parent < child
    protected void upHeap() {
        int i = this.n;
        while (i > 0) {
            int p = parent(i);
            if (this.heapPQ[i].getKey().compareTo(this.heapPQ[p].getKey()) > 0) {
                break;
            }
            swap(i, p);
            i = p;
        }
    }

    protected void downHeap() {
        int i = 0;
        while (i < this.n) {
            if (hasRight(i)) {
                if (this.heapPQ[i].getKey().compareTo(this.heapPQ[left(i)].getKey()) < 0 &&
                        this.heapPQ[i].getKey().compareTo(this.heapPQ[right(i)].getKey()) < 0) {
                    break;
                } else {
                    if (this.heapPQ[left(i)].getKey().compareTo(this.heapPQ[right(i)].getKey()) < 0) {
                        swap(i, left(i));
                        i = left(i);
                    } else {
                        swap(i, right(i));
                        i = right(i);
                    }
                }
            } else if (hasLeft(i)) {
                if (this.heapPQ[i].getKey().compareTo(this.heapPQ[left(i)].getKey()) > 0) {
                    swap(i, left(i));
                }
                break;
            } else {
                break;
            }
        }
    }

    public void insert(K k, E e) {
        if (this.n >= this.defaultSize) {
            grow();
        }
        this.heapPQ[n] = new ArrEntry<>(k, e);
        upHeap();
        this.n++;
    }

    private void grow() {
        int newCapacity = this.defaultSize * 2;
        ArrEntry<K, E>[] newData = new ArrEntry[newCapacity];

        for (int i = 0; i < n; i++) {
            newData[i] = this.heapPQ[i];
        }
        this.defaultSize = newCapacity;
        this.heapPQ = newData;
    }


    public void insert(ArrEntry<K, E> entry) {
        this.insert(entry.getKey(), entry.getValue());
    }

    public ArrEntry<K, E> removeMin() {
        if (isEmpty()) {
            return null;
        }
        ArrEntry<K, E> min = this.heapPQ[0];
        swap(0, this.n - 1);
        this.n--;
        downHeap();
        return min;
    }

    public ArrEntry<K, E> min() {
        if (isEmpty()) {
            return null;
        }
        return this.heapPQ[0];
    }
}
