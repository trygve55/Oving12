package com.trygve.oving12.Huffman;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
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

        for (int i = 0; i < 256; i++) {
            if (freq[i] > 0) nodes.add(new HuffmanNode((byte) i, freq[i], null, null));
        }

        if (freq.length == 257) {
            nodes.add(new HuffmanNode((byte) 0, freq[256], null, null, true));
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
            bitSets.add(new HuffmanBitString(rootNode.getLeftNode().getValue(), newBitSetLeft, depth +1, rootNode.getLeftNode().isNewByte()));
        }

        if (rootNode.getRightNode().getRightNode() != null) {
            getStrings(rootNode.getRightNode(), bitSets, newBitSetRight, depth + 1);
        } else {
            bitSets.add(new HuffmanBitString(rootNode.getRightNode().getValue(), newBitSetRight, depth +1, rootNode.getRightNode().isNewByte()));
        }
    }

    //Returns the Huffman encoded bytes from inputData.
    public static byte[] encode(byte[] inputData) {
        int maxFreq = 0, max2Freq = 1, freqBits = 0, outIterator = 0, valueOut;
        BitSet outData = new BitSet();

        int[] freq = getFreqTable(inputData);

        for (int i = 0; i < freq.length; i++) if (freq[i] > maxFreq) maxFreq = freq[i];

        while (maxFreq > max2Freq) {
            freqBits++;
            max2Freq = max2Freq << 1;
        }

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

        List<HuffmanBitString> huffmanBitStrings = getHuffmanStrings(getHuffmanTree(freq));

        HuffmanBitString currentHuffmanBitString;
        BitSet bitSet;

        for (int i = 0; i < inputData.length; i++) {
            currentHuffmanBitString = null;

            for (int j = 0; j < huffmanBitStrings.size(); j++) {
                if (inputData[i] == huffmanBitStrings.get(j).getaByte()) {
                    currentHuffmanBitString = huffmanBitStrings.get(j);
                    break;
                }
            }

            bitSet = currentHuffmanBitString.getBitSet();

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

        int [] freq = new int[256];

        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < freqBits; j++) {
                if (bitSet.get(inIterator)) freq[i] += 1 << j;
                inIterator++;
            }
        }

        for (int i = 0; i < freq.length; i++) totalSize += freq[i];

        HuffmanNode rootNode = getHuffmanTree(freq), currentNode;

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

    public static byte[] adaptiveEncode(byte[] inputData) {
        int[] freq = new int[257];
        int outIterator = 0;
        BitSet outData = new BitSet(), bitSet;
        List<HuffmanBitString> huffmanBitStrings = new ArrayList<HuffmanBitString>();
        HuffmanBitString currentHuffmanBitString = null;

        for (int inputIterator = 0; inputIterator < inputData.length; inputIterator++) {

            if (freq[(int) inputData[inputIterator] & 0xFF] == 0) {
                freq[256]++;
                freq[(int) inputData[inputIterator] & 0xFF]++;
                System.out.println("added to: " + (inputData[inputIterator] & 0xFF));
                huffmanBitStrings = getHuffmanStrings(getHuffmanTree(freq));
                System.out.println(huffmanBitStrings);

                if (inputIterator != 0) {
                    for (int j = 0; j < huffmanBitStrings.size(); j++) {
                        if (huffmanBitStrings.get(j).isNewByte()) {
                            currentHuffmanBitString = huffmanBitStrings.get(j);
                            break;
                        }
                    }

                    for (int k = 0; k < currentHuffmanBitString.length(); k++) {
                        if (currentHuffmanBitString.getBitSet().get(k)) outData.set(outIterator);
                        outIterator++;
                    }
                }

                int inputByte = inputData[inputIterator];
                System.out.println(inputByte);
                for (int k = 0; k < 8; k++) {
                    if (inputByte % 2 != 0) {
                        outData.set(outIterator);
                        System.out.print("1");
                    } else {
                        System.out.print("0");
                    }
                    inputByte = inputByte >>> 1;
                    outIterator++;
                }
                System.out.println();

            } else {
                freq[(int) inputData[inputIterator] & 0xFF]++;
            }

            currentHuffmanBitString = null;


            for (int j = 0; j < huffmanBitStrings.size(); j++) {
                if (inputData[inputIterator] == huffmanBitStrings.get(j).getaByte()) {
                    currentHuffmanBitString = huffmanBitStrings.get(j);
                    break;
                }
            }

            bitSet = currentHuffmanBitString.getBitSet();

            for (int k = 0; k < currentHuffmanBitString.length(); k++) {
                if (bitSet.get(k)) outData.set(outIterator);
                outIterator++;
            }
        }

        return outData.toByteArray();
    }

    public static byte[] adaptiveDecode(byte[] inputData) {
        int[] freq = new int[257];
        int outIterator = 0, inputIterator = 0;
        BitSet inBitSet = BitSet.valueOf(inputData);
        ArrayList<Byte> outData = new ArrayList<>();
        HuffmanNode rootNode, currentNode;

        for (int i = 0; i < inputData.length*8; i++) {
            System.out.print((inBitSet.get(i) ? "1" : "0" ));
        }
        System.out.println();

        freq[256]++;

        int value = 0;
        for (int i = 0; i < 8; i++) {
            if (inBitSet.get(inputIterator)) {
                value += 1 << i;
                System.out.print("1");
            } else System.out.print("0");
            inputIterator++;
        }
        System.out.println();

        freq[value]++;
        rootNode = getHuffmanTree(freq);
        outData.add((byte) value);
        System.out.println("added: " + value + " " + (char)value);
        List<HuffmanBitString> huffmanBitStrings = getHuffmanStrings(getHuffmanTree(freq));
        System.out.println(huffmanBitStrings);

        for (; inputIterator < inputData.length * 8; inputIterator++) {
            currentNode = rootNode;
            while (currentNode.getLeftNode() != null) {
                if (inBitSet.get(inputIterator)) {
                    currentNode = currentNode.getLeftNode();
                } else {
                    currentNode = currentNode.getRightNode();
                }
                inputIterator++;
            }

            if (currentNode.isNewByte()) {
                value = 0;
                for (int i = 0; i < 8; i++) {
                    if (inBitSet.get(inputIterator)) {
                        value += 1 << i;
                        System.out.print("1");
                    } else {
                        System.out.print("0");
                    }

                    inputIterator++;

                }
                System.out.println();

                freq[256]++;
                freq[value]++;
                rootNode = getHuffmanTree(freq);
                outData.add((byte) value);
                huffmanBitStrings = getHuffmanStrings(getHuffmanTree(freq));
                System.out.println(huffmanBitStrings);

                System.out.println("pos: " + inputIterator + " added: " + value + " " + (char)value);

            } else {

                freq[currentNode.getValue() & 0xFF]++;
                System.out.println("value: " + (currentNode.getValue() & 0xFF));
                outData.add(currentNode.getValue());
            }
        }

        byte[] out = new byte[outData.size()];
        for (int i = 0; i < out.length; i++) {
            out[i] = outData.get(i);
        }

        return out;
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

