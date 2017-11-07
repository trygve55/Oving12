package com.trygve.oving12;

import com.trygve.oving12.Huffman.Huffman;
import com.trygve.oving12.Huffman.HuffmanBitString;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        byte[] inputData = com.trygve.oving12.ClientCommon.FileScanner.loadFile("testFiles/furyroad2.java");

        char[] test = (("Jeg hadde sittet og pratet med Solveig Kjus i nesten to timer da det gikk opp for meg hvor mye det kostet for henne å sitte der. At dette kanskje ikke hadde vært så lurt.44-åringen hadde virket så uanstrengt og åpen og komfortabel, der vi satt i hagemøblene på terrassen hennes i Spikkestad med lydopptaker og kaffe. Vi hadde ledd masse, hun hadde servert is og brownies, det var sol og hun hadde kattunge og vi hørte på P1. Solveig elsker P1.").toCharArray());
        //byte[] inputData = new byte[test.length];

        for (int i = 0; i < test.length; i++) inputData[i] = (byte) test[i];

        int[] test3 = Huffman.getFreqTable(inputData);

        for (int i = 0; i < test3.length; i++) {
            if (test3[i] > 0) System.out.println((char)i + "," + test3[i]);
        }

        System.out.println("");

        List<HuffmanBitString> huffmanBitStrings = Huffman.getHuffmanStrings(Huffman.getHuffmanTree(Huffman.getFreqTable(inputData)));

        System.out.println(huffmanBitStrings);

        byte[] outData = Huffman.encode(inputData);

        System.out.println("From: " + inputData.length + " To: " + outData.length);

        byte[] decodedData = Huffman.decode(outData);

        //printChars(outData);

        System.out.println("From: " + outData.length + " To: " + decodedData.length);

        //printChars(decodedData);

    }

    public static void printChars(byte[] data) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            sb.append((char) data[i]);
        }

        System.out.println(sb.toString());
    }
}
