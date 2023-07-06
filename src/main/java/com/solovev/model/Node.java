package com.solovev.model;

import java.util.Objects;

/**
 * Represents node for the linked list
 */
public class Node<T> {
    private Node<T> prevNode;
    private Node<T> nextNode;
    private T data;

    public Node(Node<T> prevNode, Node<T> nextNode, T data) {
        this.prevNode = prevNode;
        this.nextNode = nextNode;
        this.data = data;
    }
    public Node<T> getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Node<T> prevNode) {
        this.prevNode = prevNode;
    }

    public Node<T> getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node<T> nextNode) {
        this.nextNode = nextNode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node<?> node = (Node<?>) o;

        if (!Objects.equals(prevNode, node.prevNode)) return false;
        if (!Objects.equals(nextNode, node.nextNode)) return false;
        return Objects.equals(data, node.data);
    }

    @Override
    public int hashCode() {
        int result = prevNode != null ? prevNode.hashCode() : 0;
        result = 31 * result + (nextNode != null ? nextNode.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    /**
     * To string represents ONLY data
     * @return data in the node
     */
    @Override
    public String toString() {
        return  data.toString();
    }
}
