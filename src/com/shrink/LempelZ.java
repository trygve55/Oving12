package shrink;
import java.util.ArrayList;

public class LempelZ {
	private static final int REFERENCE_BYTES = 3;
	
	public static byte[] compress(byte[] data) {
		ArrayList<Byte> out = new ArrayList<Byte>();
		
		byte unreplaced = REFERENCE_BYTES;
		for (int i = REFERENCE_BYTES; i < data.length; i++) {
			boolean found = false;
			short distance = 0;
			byte lengthFound = 0;
			for (short d = 1; d <= i && d < 32767; d++) {
				if (data[i - d] == data[i]) {
					int detected = 1;
					int length = 0;
					for (length = i - d; length < d && length < 128; length++) {
						if (i + length < data.length) {
							if (data[i + length] == data[i + length - d]) {detected++;}
							else {break;}
						}
						else {break;}
					}
					if (detected > REFERENCE_BYTES && length > lengthFound) {
						found = true;
						lengthFound = (byte)length;
						distance = d;
						if (lengthFound == 127) {break;}
					}
				}
			}
			if (found) {
				//System.out.println(i + " Found. " + unreplaced + " ,distance: " + distance + " ,length: " + lengthFound);
				out.add(unreplaced);
				for (int j = i - unreplaced; j < i; j++) {out.add(data[j]);}
				unreplaced = 0;
				out.add((byte)(-lengthFound));	// REFERENCE_BYTES.
				out.add((byte)(distance & 0xff));
				out.add((byte)((distance >> 8) & 0xff));
				
				//System.out.println("Byte data: " + -lengthFound + "," + (distance & 0xff | ((distance >> 8) & 0xff)));
				i += lengthFound - 1;
			}
			else {unreplaced++;}
			if (unreplaced == 127) {
				out.add(unreplaced);
				//System.out.println("Start: " + (i - unreplaced) + " ,End: " + i);
				for (int j = i - unreplaced + 1; j < i; j++) {out.add(data[j]);}
				unreplaced = 0;
			}
		}
		return listToArray(out);
	}

	public static byte[] decompress(byte[] data) {
		ArrayList<Byte> out = new ArrayList<Byte>();
		for (int i = 0, iOut = 0; i < data.length; i++) {
			byte x = data[i];
			while (x == 0) {x = data[++i];}
			if (x < 0) {
				short distance = (short)(data[i + 1] | (data[i + 2] << 8));
				int start = iOut;
				for (int j = start - distance; j < start - distance - x; j++) {
					out.add(out.get(j));
					iOut++;
				}
				i += (REFERENCE_BYTES - 1);
			}
			else {
				for (int j = i + 1; j <= i + x; j++) {
					out.add(data[j]);
					iOut++;
				}
				i += (int)x;
			}
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
