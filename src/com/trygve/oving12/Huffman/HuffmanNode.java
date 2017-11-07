package com.trygve.oving12.Huffman;

public class HuffmanNode {
    private boolean bit;
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

    public boolean isBit() {
        return bit;
    }

    public void setBit(boolean bit) {
        this.bit = bit;
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "value=" + value +
                "freq=" + freq;
    }
}
