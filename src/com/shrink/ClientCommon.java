package com.trygve.oving12;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

class ClientCommon {
	static File[] readInput() {
		File[] f = new File[2];
		Scanner s = new Scanner(System.in);
		System.out.print("Enter input file name: ");
		f[0] = new File(s.nextLine());
		System.out.print("Enter output file name: ");
		f[1] = new File(s.nextLine());
		s.close();
		return f;
	}
	
	static void output(File f, byte[] data) {
		if (data == null) {return;}
		if (f.exists()) {FileScanner.writeFile(f.getPath(), data);}
		else {
			try {f.createNewFile();} catch (IOException e) {e.printStackTrace();}
			FileScanner.writeFile(f.getPath(), data);
		}
	}

    public static class FileScanner {
        // Read every byte of the file.
        public static byte[] loadFile(String path) {
            try {return Files.readAllBytes(Paths.get(path));}
            catch (IOException e) {return null;}
        }

        // Write every byte to file.
        public static boolean writeFile(String path, byte[] data) {
            try {Files.write(Paths.get(path), data);}
            catch (IOException e) {return false;}
            return true;
        }
    }
}