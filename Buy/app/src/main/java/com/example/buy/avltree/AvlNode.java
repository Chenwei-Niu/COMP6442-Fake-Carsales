package com.example.buy.avltree;
// author:Chenwei Niu, Xinyu Wu


import java.util.ArrayList;

public class AvlNode<T extends Comparable<? super T>> {
    T element;
    ArrayList<T> arrayList;
    int height;
    AvlNode<T> left;
    AvlNode<T> right;

    public AvlNode(T element) {
        this(element, null, null);
    }

    public AvlNode(T element, AvlNode<T> left, AvlNode<T> right) {
        this.element = element;
        this.arrayList = new ArrayList<>();
        this.left = left;
        this.right = right;
        this.height = 0;
    }

    @Override
    public String toString() {
        return "AvlNode{" +
                "element=" + element +
                ", arrayList=" + arrayList +
                ", height=" + height +
                ", left=" + left +
                ", right=" + right +
                '}';
    }



    public T getElement() {
        return element;
    }

    public ArrayList<T> getArrayList() {
        return arrayList;
    }




}
