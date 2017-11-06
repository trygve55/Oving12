package com.trygve.oving12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Huffman {
    private static int[] getFreqTable(byte[] inputBytes) {
        int[] freq = new int[256];

        for (int i = 0;i < inputBytes.length;i++) {
            freq[(int) inputBytes[i]]++;
        }

        return freq;
    }

    private static HuffmanNode getHuffmanTree(int[] freqTable) {
        List<HuffmanNode> unusedNodes = new ArrayList<HuffmanNode>();
        for (int i = 0; i < freqTable.length; i++) {
            if (freqTable[i] > 0)unusedNodes.add(new HuffmanNode((byte) i , freqTable[i], null, null));
        }

        Collections.sort(unusedNodes, new Comparator<HuffmanNode>() {
            @Override
            public int compare(HuffmanNode node0, HuffmanNode node1) {
                return node0.getFreq() > node1.getFreq() ? -1 : (node0.getFreq() < node1.getFreq()) ? 1 : 0;
            }
        });

        return unusedNodes.get(0);
    }

}
