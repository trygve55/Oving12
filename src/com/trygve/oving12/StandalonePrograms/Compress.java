package com.trygve.oving12.StandalonePrograms;

import com.trygve.oving12.Huffman.Huffman;
import shrink.FileScanner;
import shrink.LempelZ;

/**
 * Created by Trygve on 08.11.2017.
 */
public class Compress {
    public static void main(String[] args) {
        String inPath, outPath;

        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments");
        } else if (args.length == 1) {
            inPath = args[0];
            outPath = args[0] + ".compressed";
        } else if (args.length == 2) {
            inPath = args[0];
            outPath = args[1];
        } else {
            throw new IllegalArgumentException("To many arguments");
        }

        byte[] indata = FileScanner.loadFile(inPath), outData = Huffman.encode(LempelZ.compress(indata));

        System.out.println("Compressed (" + Math.round(((double) outData.length/indata.length) * 100) + "%) from: " + indata.length + "B to " + outData.length + "B.");

        FileScanner.writeFile(outPath, outData);
    }
}
