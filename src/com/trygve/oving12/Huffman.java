package com.trygve.oving12;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trygve on 06.11.2017.
 */
public class Huffman {
    private static int[] getFreqTable(byte[] inputBytes) {
        int[] freq = new int[256];

        for (int i = 0;i < inputBytes.length;i++) {
            freq[(int) inputBytes[i]]++;
        }

        return freq;
    }

    private static HuffmanNode HuffmanNode(int[] freqTable) {
        List<HuffmanNode> unusedNodes = new ArrayList<HuffmanNode>();
        for (int i = 0; i < freqTable.length; i++) {
            if (freqTable[i] > 0)unusedNodes.add(new HuffmanNode(, freqTable[i], null, null));
        }
    }
}
