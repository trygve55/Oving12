package com.trygve.oving12;
import java.util.ArrayList;

public class LempelZ {
	public static byte[] compress(byte[] data) {
		ArrayList<Byte> out = new ArrayList<Byte>();
		
		byte unreplaced = 1;
		for (int i = 1; i < data.length; i++) {
			boolean found = false;
			short distance = 0;
			byte lengthFound = 0;
			for (short d = 1; d <= i && d < 32767; d++) {
				if (data[i - d] == data[i]) {
					int detected = 1;
					byte length = 0;
					for (length = 1; length <= i && length < 127; length++) {	// Exclude things already compressed!!!
						if (i - d - length >= 0) {
							if (data[i - length] == data[i - d - length]) {detected++;}
							else {break;}
						}
						else {break;}
					}
					if (detected > 3 && length > lengthFound) {
						found = true;
						lengthFound = length;
						distance = d;
					}
				}
			}
			if (found) {
				out.add(unreplaced);
				for (int j = i - unreplaced; j < i - unreplaced + lengthFound && j < data.length; j++) {out.add(data[j]);}
				unreplaced = 0;
				out.add((byte)(-lengthFound));
				out.add((byte)(distance & 0xff));
				out.add((byte)(distance >> 8 & 0xff));
			}
			else {unreplaced++;}
			if (unreplaced == 127) {
				out.add(unreplaced);
				for (int j = i - unreplaced; j < i; j++) {out.add(data[j]);}
				unreplaced = 0;
			}
		}
		return listToArray(out);
	}
	
	public static byte[] decompress(byte[] data) {
		ArrayList<Byte> out = new ArrayList<Byte>();
		for (int i = 0; i < data.length;) {
			byte x = data[i];
			if (x < 0) {
				short distance = (short)((data[i + 1]) | data[i + 2] << 8);
				for (int j = i - distance; j < i - distance + x; j++) {out.add(data[j]);}
				i -= (int)x;
			}
			else {
				for (int j = 1; j < x; j++) {out.add(data[j]);}
				i += (int)x;
			}
			if (x == 0) {i++;}	// Remove?
		}
		return listToArray(out);
	}
	
	private static byte[] listToArray(ArrayList<Byte> out) {
		int length = out.size();
		byte[] result = new byte[length];
		for (int i = 0; i < length; i++) {result[i] = out.get(i);}
		return result;
	}
}
