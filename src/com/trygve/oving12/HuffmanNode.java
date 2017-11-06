package com.trygve.oving12;

/**
 * Created by Trygve on 06.11.2017.
 */
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
}
