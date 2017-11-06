package com.trygve.oving12;

/**
 * Created by Trygve on 06.11.2017.
 */
public class LinkedList {
    private LinkedNode dummyNode;
    private int size = 0;

    public LinkedList() {
        dummyNode = new LinkedNode(null);
        dummyNode.setAfterNode(dummyNode);
        dummyNode.setNextNode(dummyNode);
    }

    public void add(HuffmanNode huffmanNode) {

        System.out.println("added" + size() + " " + huffmanNode.getFreq());

        LinkedNode currentNode = dummyNode;

        while (true) {
            if ((currentNode.getObject() != null && huffmanNode.getFreq() >= currentNode.getObject().getFreq()) || currentNode.getNextNode() == dummyNode) {
                LinkedNode newNode = new LinkedNode(huffmanNode, currentNode, currentNode.getNextNode());

                currentNode.getNextNode().setAfterNode(newNode);
                currentNode.setNextNode(newNode);

                size++;
                return;
            }

            currentNode = currentNode.getNextNode();
        }
    }

    public HuffmanNode popLowest() {
        HuffmanNode huffmanNode = null;
        //if (dummyNode.getNextNode() == dummyNode) return null;
        huffmanNode = dummyNode.getNextNode().getObject();

        System.out.println(dummyNode.getNextNode());
        System.out.println(dummyNode.getNextNode().getNextNode());

        LinkedNode nextAfterNode = dummyNode.getNextNode().getNextNode();
        nextAfterNode.setAfterNode(dummyNode);
        dummyNode.setNextNode(nextAfterNode);

        size--;
        return huffmanNode;
    }

    public int size() {
        return size;
    }
}
