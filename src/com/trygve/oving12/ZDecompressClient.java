package shrink;

import java.io.File;

public class ZDecompressClient {
	public static void main(String[] args) {
		File[] f = ClientCommon.readInput();
		
		byte[] data = null;
		if (f[0].exists()) {data = LempelZ.decompress(FileScanner.loadFile(f[0].getPath()));}
		else {System.out.println("Input file does not exist.");}
		
		ClientCommon.output(f[1], data);
	}
}