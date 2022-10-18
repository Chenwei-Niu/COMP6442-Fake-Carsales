package com.example.buy.avltree;

import com.example.buy.entity.Car;
import com.example.buy.entity.Market;
import com.example.buy.parser.MyJsonReader;
import com.example.buy.avltree.AvlTree;


import org.junit.Test;

import java.util.ArrayList;

public class KiaTree extends AvlTree {
    private int id;
    private String brand;
    private int price;
    MyJsonReader myJsonReader = new MyJsonReader();
    private ArrayList<Car> carsList = new ArrayList<>();
    AvlNode node = createKiaTree(Market.getMarket().getCarArray());

    private AvlNode createKiaTree(ArrayList<Integer> carsList2){
        return createKiaTree(carsList,0,carsList.size()-1);

    }

    public AvlNode createKiaTree(ArrayList<Car> carsList, int start, int end){
        if(end<start)
            return null;

        int mid = (start+end)/2;
        AvlNode node = new AvlNode(carsList.get(mid));
        node.left = createKiaTree(carsList,start,mid-1);
        node.right = createKiaTree(carsList,mid-1,end);

        return node;
    }

    public void printTree(){
        if (isEmpty()){
            System.out.println("Empty");
        }
        else {
            printTree(root);
        }
    }
    public void printTree(AvlNode root){
        if (root != null){
            System.out.print(root.element);
            if (null != root.left){
                System.out.print("leftroot" + root.left.element);
            }
            if (null != root.right){
                System.out.println("rightroot"+root.right.element);

            }
            System.out.println();
            printTree(root.left);
            printTree(root.right);
        }
    }


}




