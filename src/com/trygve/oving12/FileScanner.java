package com.trygve.oving12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileScanner {
	// Read every byte of the file.
	public static byte[] loadFile(String path) {
		try {return Files.readAllBytes(Paths.get(path));}
		catch (IOException e) {return null;}
	}
}