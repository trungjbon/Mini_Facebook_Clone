package adt;

import java.io.Serializable;

public class MyLinkedList<T> implements Serializable {
    class Node {
        T data;
        Node next;

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node top;
    private Node bot;
    private int n;

    public MyLinkedList() {
        this.top = null;
        this.bot = null;
        this.n = 0;
    }

    public void addTop(T data) {
        Node node = new Node(data, null);
        if (this.n != 0) {
            node.next = this.top;
            if (this.n == 1) {
                this.bot = this.top;
            }
        }
        this.top = node;
        this.n++;
    }

    public void addBot(T data) {
        if (this.n == 0) {
            this.top = new Node(data, null);
        } else if (this.n == 1) {
            this.bot = new Node(data, null);
            this.top.next = bot;
        } else {
            Node newNode = new Node(data, null);
            this.bot.next = newNode;
            this.bot = newNode;
        }
        this.n++;
    }

    public T get(int i) {
        if (i < 0 || i >= this.n) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.getNodeByIndex(i).data;
    }

    public void set(int i, T data) {
        if (i < 0 || i >= this.n) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Node node = this.getNodeByIndex(i);
        node.data = data;
    }

    public boolean isContain(T data) {
        Node node = top;
        for (int i = 0; i < this.n; i++) {
            if (node.data.equals(data)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public int size() {
        return this.n;
    }

    public boolean isEmpty() {
        return this.n == 0;
    }

    public T removeTop() {
        if (this.n == 0) {
            return null;
        }
        T oldTopData = this.top.data;
        if (this.n > 1) {
            this.top = top.next;
            if (this.n == 2) {
                this.bot = null;
            }
        } else {
            this.top = null;
        }
        this.n--;
        return oldTopData;
    }

    public T removeBot() {
        if (this.n == 0) {
            return null;
        }
        T oldBotData;
        if (this.n == 1) {
            oldBotData = this.top.data;
            this.top = null;
        } else {
            oldBotData = this.bot.data;
            Node prevNode = this.getNodeByIndex(this.n - 2);
            prevNode.next = null;
            if (this.n > 2) {
                this.bot = prevNode;
            }
        }
        this.n--;
        return oldBotData;
    }

    public void remove(T data) {
        Node node = top;
        int index = -1;
        for (int i = 0; i < this.n; i++) {
            if (node.data.equals(data)) {
                index = i;
                break;
            }
            node = node.next;
        }
        if (index == -1) {
            return;
        }

        if (index == 0) {
            removeTop();
        } else if (index == this.n - 1) {
            removeBot();
        } else {
            Node prevNode = this.getNodeByIndex(index - 1);
            Node nextNode = this.getNodeByIndex(index + 1);
            prevNode.next = nextNode;
        }
        this.n--;
    }

    private Node getNodeByIndex(int index) {
        Node tempNode = top;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.next;
        }
        return tempNode;
    }
}
