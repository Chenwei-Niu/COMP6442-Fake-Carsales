package com.example.buy.avltree;

import org.junit.Test;

public class treeTest {
    @Test(timeout = 1000)
    public void test1(){
        AvlTree testTree = new AvlTree();
        testTree.insert(1);
        testTree.insert(3);
        testTree.insert(5);
        testTree.insert(7);
    }
}
