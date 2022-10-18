package com.example.buy.avltree;
// author:Chenwei Niu, Xinyu Wu
import java.util.ArrayList;

public class AvlTree<T extends Comparable<? super T>> {

    public AvlNode<T> root;

    public void insert(T x) {
        root = insert(x, root);
    }

    public void remove(T x) {
        root = remove(x, root);
    }

    public T findMin() {
        return findMin(root).element;
    }


    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Add a node
     * @param x Insert Node
     * @param t Parent Node
     */
    private AvlNode<T> insert(T x, AvlNode<T> t) {

        if (null == t) {
            return new AvlNode(x);
        }

        int compareResult = x.compareTo(t.element);

        //小于当前根节点 将x插入根节点的左边
        if (compareResult < 0) {
            t.left = insert(x, t.left);
        } else if (compareResult > 0) {
            //大于当前根节点 将x插入根节点的右边
            t.right = insert(x, t.right);
        } else {
            // if equals to the current node, insert into the arrayList
            // Edited by Chenwei Niu
            t.arrayList.add(x);
        }
        return balance(t);
    }

    private static final int ALLOWED_IMBALANCE = 1;

    private AvlNode<T> balance(AvlNode<T> t) {
        if (t == null) {
            return t;
        }

        if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
            if (height(t.left.left) >= height(t.left.right)) {
                t = rotateWithLeftChild(t);
            } else {
                t = doubleWithLeftChild(t);
            }
        } else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
            if (height(t.right.right) >= height(t.right.left)) {
                t = rotateWithRightChild(t);
            } else {
                t = doubleWithRightChild(t);
            }
        }
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    private AvlNode<T> doubleWithRightChild(AvlNode<T> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }

    private AvlNode<T> rotateWithRightChild(AvlNode<T> k2) {
        AvlNode k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        k2.height = Math.max(height(k2.right), height(k2.left)) + 1;
        k1.height = Math.max(height(k1.right), k2.height) + 1;
        return k1;
    }

    private AvlNode<T> doubleWithLeftChild(AvlNode<T> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private AvlNode<T> rotateWithLeftChild(AvlNode<T> k2) {
        AvlNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    private int height(AvlNode<T> t) {
        return t == null ? -1 : t.height;
    }

    /**
     * @param x    Node
     * @param t    The parent node
     */
    private AvlNode<T> remove(T x, AvlNode<T> t) {

        if (null == t) {
            return t;
        }

        int compareResult = x.compareTo(t.element);

        //小于当前根节点
        if (compareResult < 0) {
            t.left = remove(x, t.left);
        } else if (compareResult > 0) {
            //大于当前根节点
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null) {
            //找到右边最小的节点
            t.element = findMin(t.right).element;
            //当前节点的右边等于原节点右边删除已经被选为的替代节点
            t.right = remove(t.element, t.right);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }
        return balance(t);
    }

    /**
     * Min Node
     *
     * @param root
     */
    private AvlNode<T> findMin(AvlNode<T> root) {
        if (root == null) {
            return null;
        } else if (root.left == null) {
            return root;
        }
        return findMin(root.left);
    }

    /**
     * Max Node
     *
     * @param root
     */
    private AvlNode<T> findMax(AvlNode<T> root) {
        if (root == null) {
            return null;
        } else if (root.right == null) {
            return root;
        } else {
            return findMax(root.right);
        }
    }

    // author:Chenwei Niu
    public AvlNode<T> find(AvlNode<T> node, T t){
        if(node == null){
            return null;
        }
        if (node.element.compareTo(t) < 0){
            return find(node.right,t);
        } else if (node.element.compareTo(t) > 0){
            return find(node.left,t);
        } else {
            return node;
        }
    }

    // author:Chenwei Niu
    public void findGreaterThan(AvlNode<T> node,T t, ArrayList<T> cars){
        if(node == null){
            return;
        }
        if (node.element.compareTo(t) > 0){
            cars.add(node.element);
            if (!node.arrayList.isEmpty()){
                for (T t1: node.arrayList ){
                    cars.add(t1);
                }
            }
            findGreaterThan(node.left,t,cars);
            findGreaterThan(node.right,t,cars);
        } else {
            findGreaterThan(node.right,t,cars);
        }
    }

    // author:Chenwei Niu
    public void findGreaterOrEqualThan(AvlNode<T> node,T t, ArrayList<T> cars){
        if(node == null){
            return;
        }
        if (node.element.compareTo(t) > 0 || node.element.compareTo(t) == 0){
            cars.add(node.element);
            if (!node.arrayList.isEmpty()){
                for (T t1: node.arrayList ){
                    cars.add(t1);
                }
            }
            findGreaterOrEqualThan(node.left,t,cars);
            findGreaterOrEqualThan(node.right,t,cars);
        } else {
            findGreaterOrEqualThan(node.right,t,cars);
        }
    }

    // author:Chenwei Niu
    public void findLessThan(AvlNode<T> node,T t, ArrayList<T> cars){
        if(node == null){
            return;
        }
        if (node.element.compareTo(t) < 0){
            cars.add(node.element);
            if (!node.arrayList.isEmpty()){
                for (T t1: node.arrayList ){
                    cars.add(t1);
                }
            }
            findLessThan(node.right,t,cars);
            findLessThan(node.left,t,cars);
        } else {
            findLessThan(node.left,t,cars);
        }
    }

    // author:Chenwei Niu
    public void findLessOrEqualThan(AvlNode<T> node,T t, ArrayList<T> cars){
        if(node == null){
            return;
        }
        if (node.element.compareTo(t) < 0 || node.element.compareTo(t) == 0){
            cars.add(node.element);
            if (!node.arrayList.isEmpty()){
                for (T t1: node.arrayList ){
                    cars.add(t1);
                }
            }
            findLessOrEqualThan(node.right,t,cars);
            findLessOrEqualThan(node.left,t,cars);
        } else {
            findLessOrEqualThan(node.left,t,cars);
        }
    }

    // author:Chenwei Niu
    public ArrayList<T> toArrayList(){
        ArrayList<T> arrayList = new ArrayList<>();
        preOrderTraverse(root,arrayList);
        return arrayList;
    }

    // author:Chenwei Niu
    private void preOrderTraverse(AvlNode<T> node, ArrayList<T> arrayList){
        if(node == null){
            return;
        }
        arrayList.add(node.element);
        if (!node.getArrayList().isEmpty()){
            for (T t1: node.arrayList ){
                arrayList.add(t1);
            }
        }
        preOrderTraverse(node.left,arrayList);
        preOrderTraverse(node.right,arrayList);
    }




}
