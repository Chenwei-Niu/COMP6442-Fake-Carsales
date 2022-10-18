package com.example.buy.avltree;
//author:Chenwei Niu
import junit.framework.TestCase;

import org.junit.Test;

public class AvlTreeTest{
    @Test
    public void avlTreeTest(){
        AvlTree<Integer> avlTree = new AvlTree<>();
        avlTree.insert(2);
        avlTree.insert(1);
        avlTree.insert(5);
        avlTree.insert(3);
        avlTree.insert(6);
        avlTree.insert(4);
        avlTree.toString();
        System.out.println(avlTree.root);
        System.out.println(avlTree.root.left);
        System.out.println(avlTree.root.left.left);
        System.out.println(avlTree.root.right);
        System.out.println(avlTree.root.right.left);
        System.out.println(avlTree.root.right.right);
    }
}