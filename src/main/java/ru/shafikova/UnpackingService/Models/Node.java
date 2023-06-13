package ru.shafikova.UnpackingService.Models;

import java.io.Serializable;

public class Node implements Comparable<Node>, Serializable {
    private int iData;
    private Character fData;
    private Node leftChild;
    private Node rightChild;

    @Override
    public int compareTo(Node obj) {
        return (this.iData - obj.iData);
    }

    @Override
    public String toString() {
        return "Node{" +
                "iData=" + iData +
                ", fData=" + fData +
                ", leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                '}';
    }

    public int getiData() {
        return iData;
    }

    public void setiData(int iData) {
        this.iData = iData;
    }

    public Character getfData() {
        return fData;
    }

    public void setfData(Character fData) {
        this.fData = fData;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }
}