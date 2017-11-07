package com.trygve.oving12;

import java.nio.ByteBuffer;
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

    public static byte[] encode(byte[] inputData) {

        int[] freq = getFreqTable(inputData);
        List<HuffmanBitString> huffmanBitStrings = getHuffmanStrings(getHuffmanTree(freq));

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

        byte[] encodedData = outData.toByteArray();
        byte[] outputData = new byte[1024+encodedData.length];

        for (int i = 0; i < freq.length; i++) {
            System.out.println(i + " : " + freq[i]);
            byte[] byteArray = ByteBuffer.allocate(4).putInt(freq[i]).array();
            outputData[i*4] = byteArray[0];
            outputData[i*4+1] = byteArray[1];
            outputData[i*4+2] = byteArray[2];
            outputData[i*4+3] = byteArray[3];
        }

        for (int i = 1024; i < outputData.length; i++) {
            outputData[i] = encodedData[i-1024];
        }

        return outputData;
    }

    public static byte[] decode(byte[] inputData) {

        BitSet bitSet = BitSet.valueOf(inputData);
        int inSize = inputData.length*8, inIterator = 0;

        int[] freq = new int[256];
        for (int i = 0; i < 256; i++) {
            freq[i] = inputData[i*4+3] & 0xFF |
                    (inputData[i*4+2] & 0xFF) << 8|
                    (inputData[i*4+1] & 0xFF) << 16|
                    (inputData[i*4] & 0xFF) << 24;
            System.out.println(i + " : " + freq[i]);
        }

        HuffmanNode rootNode = getHuffmanTree(freq);

        int totalSize = 0;
        for (int i = 0; i < freq.length; i++) {
            totalSize += freq[i];
        }

        byte[] outputData = new byte[totalSize];

        inIterator = 1024*8;

        for (int outIterator = 0; outIterator < totalSize; outIterator++) {
            int depth = 0;
            HuffmanNode currentNode = rootNode;
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

    public static int byteArrayToInt(byte[] b)
    {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a)
    {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }
}

