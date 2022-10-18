package com.example.buy.avltree;

public class AvlTree<T extends Comparable<? super T>> {

    AvlNode<T> root;

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

    private void preOrder (AvlNode por)
    {
        if (por != null)
        {
            preOrder (por.left);
            preOrder (por.right);
        }
    }




}
