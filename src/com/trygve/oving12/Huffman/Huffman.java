package com.trygve.oving12.Huffman;

import java.util.*;

public class Huffman {
    public static boolean debug = false;

    //Returns a frequency table for the bytes of inputBytes.
    public static int[] getFreqTable(byte[] inputBytes) {
        int[] freq = new int[256];

        for (int i = 0; i < inputBytes.length; i++) freq[(int) inputBytes[i] & 0xFF]++;

        return freq;
    }

    //Returns the root of a Huffman tree made from the frequency table freq.
    public static HuffmanNode getHuffmanTree(int[] freq) {
        PriorityQueue<HuffmanNode> nodes = new PriorityQueue<>(new Comparator<HuffmanNode>() {
            @Override
            public int compare(HuffmanNode o1, HuffmanNode o2) {
                return o1.getFreq() - o2.getFreq();
            }
        });

        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 0) nodes.add(new HuffmanNode((byte) i, freq[i], null, null));
        }

        while (nodes.size() > 1) {
            HuffmanNode nodeLeft = nodes.poll();
            HuffmanNode nodeRight = nodes.poll();

            nodes.add(new HuffmanNode(nodeLeft.getFreq() + nodeRight.getFreq(), nodeLeft, nodeRight));
        }

        return nodes.poll();
    }

    //Returns a list of all Huffman strings from the Huffman tree from rootNode
    public static List<HuffmanBitString> getHuffmanStrings(HuffmanNode rootNode) {
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

    //Recursive help method of getHuffmanStrings(HuffmanNode rootNode).
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

    //Returns the Huffman encoded bytes from inputData.
    public static byte[] encode(byte[] inputData) {

        int[] freq = getFreqTable(inputData);
        List<HuffmanBitString> huffmanBitStrings = getHuffmanStrings(getHuffmanTree(freq));

        int maxFreq = 0, max2Freq = 1, freqBits = 0, valueOut;
        for (int i = 0; i < freq.length; i++) if (freq[i] > maxFreq) maxFreq = freq[i];
        while (maxFreq > max2Freq) {
            freqBits++;
            max2Freq = max2Freq << 1;
        }

        BitSet outData = new BitSet();
        int outIterator = 0;

        int freqBitsWrite = freqBits;
        for (int i = 0; i < 8; i++) {
            if (freqBitsWrite % 2 != 0) {
                outData.set(outIterator);
            }
            outIterator++;
            freqBitsWrite = freqBitsWrite >>> 1;
        }

        for (int i = 0; i < freq.length; i++) {
            valueOut = freq[i];

            for (int j = 0; j < freqBits; j++) {
                if (valueOut % 2 == 1) outData.set(outIterator);
                outIterator++;
                valueOut = valueOut >>> 1;
            }
        }


        HuffmanBitString currentHuffmanBitString;
        for (int i = 0; i < inputData.length; i++) {
            currentHuffmanBitString = null;

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

    //Returns the decoded bytes from inputData.
    public static byte[] decode(byte[] inputData) {

        BitSet bitSet = BitSet.valueOf(inputData);
        int inIterator = 0, freqBits = 0, totalSize = 0;

        for (int i = 0; i < 8; i++) {
            if (bitSet.get(inIterator)) freqBits += 1 << i;
            inIterator++;
        }

        int[] freq = new int[256];

        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < freqBits; j++) {
                if (bitSet.get(inIterator)) freq[i] += 1 << j;
                inIterator++;
            }
        }

        HuffmanNode rootNode = getHuffmanTree(freq), currentNode;

        for (int i = 0; i < freq.length; i++) totalSize += freq[i];

        byte[] outputData = new byte[totalSize];

        for (int outIterator = 0; outIterator < totalSize; outIterator++) {
            currentNode = rootNode;
            while (currentNode.getLeftNode() != null) {
                if (bitSet.get(inIterator)) {
                    currentNode = currentNode.getLeftNode();
                } else {
                    currentNode = currentNode.getRightNode();
                }
                inIterator++;
            }
            outputData[outIterator] = currentNode.getValue();
        }

        return outputData;
    }

    private static int byteArrayToInt(byte[] b)
    {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    private static byte[] intToByteArray(int a)
    {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }
}

