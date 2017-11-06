package com.trygve.oving12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Huffman {
    public static HuffmanNode getTree(byte[] inputByte) {
        return getHuffmanTree(getFreqTable(inputByte));
    }

    private static int[] getFreqTable(byte[] inputBytes) {
        int[] freq = new int[256];

        for (int i = 0;i < inputBytes.length;i++) {
            freq[(int) inputBytes[i] & 0xFF]++;
        }

        return freq;
    }

    private static HuffmanNode getHuffmanTree(int[] freqTable) {
        LinkedList nodes = new LinkedList();
        for (int i = 0; i < freqTable.length; i++) {
            if (freqTable[i] > 0) nodes.add(new HuffmanNode((byte) i , freqTable[i], null, null));
        }

        while(nodes.size() > 1) {
            HuffmanNode nodeLeft = nodes.popLowest();
            HuffmanNode nodeRight = nodes.popLowest();

            System.out.println(nodeLeft);
            System.out.println(nodeRight);

            nodes.add(new HuffmanNode(nodeLeft.getFreq() + nodeRight.getFreq(), nodeLeft, nodeRight));

        }

        return nodes.popLowest();
    }

}
