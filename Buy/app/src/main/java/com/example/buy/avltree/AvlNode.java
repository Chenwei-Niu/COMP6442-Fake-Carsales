package com.example.buy.avltree;

public class AvlNode<T> {
    T element;
    int height;
    AvlNode<T> left;
    AvlNode<T> right;

    public AvlNode(T element) {
        this(element, null, null);
    }

    public AvlNode(T element, AvlNode<T> left, AvlNode<T> right) {
        this.element = element;
        this.left = left;
        this.right = right;
        this.height = 0;
    }
}
