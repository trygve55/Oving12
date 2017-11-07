package com.trygve.oving12;

import java.util.*;

public class Huffman {
    public static HuffmanNode getTree(byte[] inputByte) {
        return getHuffmanTree(getFreqTable(inputByte));
    }

    public static int[] getFreqTable(byte[] inputBytes) {
        int[] freq = new int[256];

        for (int i = 0; i < inputBytes.length; i++) {
            freq[(int) inputBytes[i] & 0xFF]++;
        }

        return freq;
    }

    private static HuffmanNode getHuffmanTree(int[] freqTable) {
        PriorityQueue<HuffmanNode> nodes = new PriorityQueue<>(new Comparator<HuffmanNode>() {
            @Override
            public int compare(HuffmanNode o1, HuffmanNode o2) {
                return o1.getFreq() - o2.getFreq();
            }
        });

        for (int i = 0; i < freqTable.length; i++) {
            if (freqTable[i] > 0) nodes.add(new HuffmanNode((byte) i, freqTable[i], null, null));
        }

        while (nodes.size() > 1) {
            HuffmanNode nodeLeft = nodes.poll();
            HuffmanNode nodeRight = nodes.poll();

            nodes.add(new HuffmanNode(nodeLeft.getFreq() + nodeRight.getFreq(), nodeLeft, nodeRight));
        }

        return nodes.poll();
    }

    public static List<HuffmanBitString> getHuffmanTree(HuffmanNode rootNode) {
        List<HuffmanBitString> bitSetAll = new ArrayList<>();
        getStrings(rootNode, bitSetAll, new BitSet(), 0);

        bitSetAll.sort(new Comparator<HuffmanBitString>() {
            @Override
            public int compare(HuffmanBitString o1, HuffmanBitString o2) {
                return o1.length() - o2.length();
            }
        });

        return bitSetAll;
    }

    private static void getStrings(HuffmanNode rootNode, List<HuffmanBitString> bitSets, BitSet bitSet, int depth) {

        BitSet newBitSetLeft = new BitSet(depth + 1);
        BitSet newBitSetRight = new BitSet(depth + 1);

        for (int i = 0; i < depth; i++) {
            if (bitSet.get(i)) {
                newBitSetLeft.set(i);
                newBitSetRight.set(i);
            } else {
                newBitSetLeft.clear(i);
                newBitSetRight.clear(i);
            }
        }

        newBitSetLeft.set(depth);
        newBitSetRight.clear(depth);

        if (rootNode.getLeftNode().getLeftNode() != null) {
            getStrings(rootNode.getLeftNode(), bitSets, newBitSetLeft, depth + 1);
        } else {
            bitSets.add(new HuffmanBitString(rootNode.getLeftNode().getValue(), newBitSetLeft, depth +1));
        }

        if (rootNode.getRightNode().getRightNode() != null) {
            getStrings(rootNode.getRightNode(), bitSets, newBitSetRight, depth + 1);
        } else {
            bitSets.add(new HuffmanBitString(rootNode.getRightNode().getValue(), newBitSetRight, depth +1));
        }
    }

    public static byte[] encode(byte[] inputData, List<HuffmanBitString> huffmanBitStrings) {
        BitSet outData = new BitSet();
        int outIterator = 0;

        for (int i = 0; i < inputData.length; i++) {

            HuffmanBitString currentHuffmanBitString = null;

            for (int j = 0; j < huffmanBitStrings.size(); j++) {
                if (inputData[i] == huffmanBitStrings.get(j).getaByte()) {
                    currentHuffmanBitString = huffmanBitStrings.get(j);
                    break;
                }
            }

            BitSet bitSet = currentHuffmanBitString.getBitSet();

            for (int j = 0; j < currentHuffmanBitString.length(); j++) {
                if (bitSet.get(j)) outData.set(outIterator);
                outIterator++;
            }
        }

        return outData.toByteArray();
    }
}

