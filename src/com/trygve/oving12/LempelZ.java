package shrink;
import java.util.ArrayList;

public class LempelZ {
	public static byte[] compress(String path) {
		ArrayList<Byte> out = new ArrayList<Byte>();
		byte[] data = FileScanner.loadFile(path);
		
		byte unreplaced = 0;
		for (int i = 0; i < data.length; i++) {
			boolean found = false;
			short distance = 0;
			byte length = 0;
			for (distance = 1; distance <= i && distance < 32767; distance++) {
				if (data[i - distance] == data[i]) {
					int detected = 1;
					for (length = 1; length <= i && length < 128; length++) {
						if (data[i - length] == data[i - distance - length]) {detected++;}
						else {break;}
					}
					if (detected > 3) {found = true;}
				}
			}
			if (found) {
				//out.add([unreplaced]);
				for (int j = i - unreplaced - length; j < i - length; j++) {out.add(data[j]);}
				unreplaced = 0;
				//out.add([-length]);
				//out.add([distance]);
			}
			else {unreplaced++;}
			if (unreplaced == 127) {
				//out.add([unreplaced]);
				for (int j = i - unreplaced; j < i; j++) {out.add(data[j]);}
				unreplaced = 0;
			}
		}
		return listToArray(out);
	}
	
	public static byte[] deCompress(String path) {
		ArrayList<Byte> out = new ArrayList<Byte>();
		byte[] data = FileScanner.loadFile(path);
		for (int i = 0; i < data.length;) {
			byte x = data[i];
			if (x < 0) {
				for (int j = i - data[i + 1]; j < i - data[i + 1] + x; j++) {out.add(data[j]);}
			}
			else {for (int j = 1; j < x; j++) {out.add(data[j]);}}
			i += x;
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
