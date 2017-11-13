package com.trygve.oving12.StandalonePrograms;

import com.trygve.oving12.Huffman.Huffman;
import shrink.FileScanner;
import shrink.LempelZ;

/**
 * Created by Trygve on 08.11.2017.
 */
public class Decompress {
    public static void main(String[] args) {
        String inPath, outPath;

        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments");
        } else if (args.length == 1) {
            inPath = args[0];
            outPath = args[0].replace(".compressed", "");
        } else if (args.length == 2) {
            inPath = args[0];
            outPath = args[1];
        } else {
            throw new IllegalArgumentException("To many arguments");
        }

        byte[] indata = FileScanner.loadFile(inPath), outData = LempelZ.decompress(Huffman.decode(indata));

        System.out.println("Decompressed from: " + indata.length + "B to " + outData.length + "B.");

        FileScanner.writeFile(outPath, outData);
    }
}

