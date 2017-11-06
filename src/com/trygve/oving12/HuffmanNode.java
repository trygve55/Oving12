package com.trygve.oving12;

public class HuffmanNode {
    private byte value;
    private int freq;
    private HuffmanNode leftNode;
    private HuffmanNode rightNode;

    public HuffmanNode(byte value, int freq, HuffmanNode leftNode, HuffmanNode rightNode) {
        this.value = value;
        this.freq = freq;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public HuffmanNode(int freq, HuffmanNode leftNode, HuffmanNode rightNode) {
        this.freq = freq;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public byte getValue() {
        return value;
    }

    public int getFreq() {
        return freq;
    }

    public HuffmanNode getLeftNode() {
        return leftNode;
    }

    public HuffmanNode getRightNode() {
        return rightNode;
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "value=" + value +
                ", freq=" + freq +
                ", leftNode=" + leftNode +
                ", rightNode=" + rightNode +
                '}';
    }
}
