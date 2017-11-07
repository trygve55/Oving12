package shrink;

import java.io.File;

public class ZCompressClient {
	public static void main(String[] args) {
		System.out.println("Compression.");
		File[] f = ClientCommon.readInput();
		
		byte[] data = null;
		if (f[0].exists()) {data = LempelZ.compress(FileScanner.loadFile(f[0].getPath()));}
		else {System.out.println("Input file does not exist.");}
		
		ClientCommon.output(f[1], data);
		System.out.println("Compression complete.");
	}
}