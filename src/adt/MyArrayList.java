package adt;

import java.io.Serializable;

public class MyArrayList<T> implements Serializable {
    private T[] array;
    private int size;
    private int defaultSize = 100;

    public MyArrayList() {
        this.array = (T[]) new Object[defaultSize];
        this.size = 0;
    }

    public MyArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        this.defaultSize = capacity;
        this.array = (T[]) new Object[defaultSize];
    }


    public void add(T data) {
        if (this.size >= this.defaultSize) {
            grow();
        }
        this.array[size] = data;
        size++;
    }

    public void add(int index, T data) {
        if (this.size >= this.defaultSize) {
            grow();
        }
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = data;
        size++;
    }

    private void grow() {
        int newCapacity = this.defaultSize * 2;
        T[] newData = (T[]) new Object[newCapacity];
        for (int i = 0; i < this.size; i++) {
            newData[i] = this.array[i];
        }
        this.defaultSize = newCapacity;
        this.array = newData;
    }

    public T get(int i) {
        if (i < 0 || i >= this.size) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            return this.array[i];
        }
    }

    public void set(int i, T data) {
        if (i < 0 || i >= this.size) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            this.array[i] = data;
        }
    }

    public void remove(T data) {
        for (int i = 0; i < this.size; i++) {
            if (this.array[i].equals(data)) {
                for (int j = i; j < this.size - 1; j++) {
                    this.array[j] = this.array[j + 1];
                }
                this.array[size - 1] = null;
                break;
            }
        }
        this.size--;
    }

    public boolean isContain(T data) {
        for (int i = 0; i < this.size; i++) {
            if (this.array[i].equals(data)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.size - 1; i++) {
            sb.append(this.array[i]).append(", ");
        }
        sb.append(this.array[this.size - 1]).append("]");
        return sb.toString();
    }
}
