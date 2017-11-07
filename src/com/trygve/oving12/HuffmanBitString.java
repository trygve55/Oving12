package com.trygve.oving12;

import java.util.BitSet;

/**
 * Created by Trygve on 06.11.2017.
 */
public class HuffmanBitString {
    private byte aByte;
    private BitSet bitSet;
    private int bitLength;

    public HuffmanBitString(byte aByte, BitSet bitSet, int bitLength) {
        this.aByte = aByte;
        this.bitSet = bitSet;
        this.bitLength = bitLength;
    }

    public byte getaByte() {
        return aByte;
    }

    public BitSet getBitSet() {
        return bitSet;
    }

    public int length() {
        return bitLength;
    }

    @Override
    public String toString() {
        String bit = "";
        for (int i = 0; i < bitLength; i++) {
            bit += ((bitSet.get(i)) ? '1' : '0');
        }

        return "HuffmanBitString{" +
                "aByte=" + (char)aByte +
                ", bitSet=" + bit +
                "}\n";
    }
}
