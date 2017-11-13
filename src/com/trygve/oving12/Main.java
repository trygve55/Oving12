package com.trygve.oving12;

import com.trygve.oving12.Huffman.Huffman;
import com.trygve.oving12.Huffman.HuffmanBitString;
import shrink.LempelZ;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        //byte[] inputData = shrink.FileScanner.loadFile("testFiles/opcodes.txt");

        //char[] test = (("Jeg hadde sittet og pratet med Solveig Kjus i nesten to timer da det gikk opp for meg hvor mye det kostet for henne å sitte der. At dette kanskje ikke hadde vært så lurt.44-åringen hadde virket så uanstrengt og åpen og komfortabel, der vi satt i hagemøblene på terrassen hennes i Spikkestad med lydopptaker og kaffe. Vi hadde ledd masse, hun hadde servert is og brownies, det var sol og hun hadde kattunge og vi hørte på P1. Solveig elsker P1.").toCharArray());
        char[] test = (("JA").toCharArray());
        byte[] inputData = new byte[test.length];

        for (int i = 0; i < test.length; i++) inputData[i] = (byte) test[i];

        int[] test3 = Huffman.getFreqTable(inputData);

        for (int i = 0; i < test3.length; i++) {
            //if (test3[i] > 0) System.out.println((char)i + "," + test3[i]);
        }

        System.out.println();

        List<HuffmanBitString> huffmanBitStrings = Huffman.getHuffmanStrings(Huffman.getHuffmanTree(Huffman.getFreqTable(inputData)));

        //System.out.println(huffmanBitStrings);

        byte[] outData = Huffman.adaptiveEncode(inputData);

        System.out.println("From: " + inputData.length + " To: " + outData.length);

        byte[] decodedData = Huffman.adaptiveDecode(outData);

        //printChars(outData);

        System.out.println("From: " + outData.length + " To: " + decodedData.length);

        printChars(decodedData);

    }

    public static void printChars(byte[] data) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            sb.append((char) data[i]);
        }

        System.out.println(sb.toString());
    }
}
