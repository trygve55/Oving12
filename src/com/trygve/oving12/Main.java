package com.trygve.oving12;

public class Main {

    public static void main(String[] args) {
        HuffmanNode huffman = Huffman.getTree(FileScanner.loadFile("testFiles/opg12.txt"));
    }
}
