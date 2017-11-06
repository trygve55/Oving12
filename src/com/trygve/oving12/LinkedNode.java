package com.trygve.oving12;

/**
 * Created by Trygve on 06.11.2017.
 */
public class LinkedNode {
    private HuffmanNode object;
    private LinkedNode beforeNode;
    private LinkedNode afterNode;

    public LinkedNode() {
    }

    public LinkedNode(HuffmanNode object, LinkedNode beforeNode, LinkedNode afterNode) {
        this.object = object;
        this.beforeNode = beforeNode;
        this.afterNode = afterNode;
    }

    public LinkedNode(HuffmanNode object) {
        this.object = object;
    }

    public HuffmanNode getObject() {
        return object;
    }

    public void setObject(HuffmanNode object) {
        this.object = object;
    }

    public LinkedNode getBeforeNode() {
        return beforeNode;
    }

    public void setNextNode(LinkedNode beforeNode) {
        this.beforeNode = beforeNode;
    }

    public LinkedNode getNextNode() {
        return afterNode;
    }

    public void setAfterNode(LinkedNode afterNode) {
        this.afterNode = afterNode;
    }

    @Override
    public String toString() {
        return "LinkedNode{" +
                "object=" + object +
                '}';
    }
}
