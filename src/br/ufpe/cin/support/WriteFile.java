package br.ufpe.cin.support;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

    public static void logger(String content, String filename) {
	try {
	    File file = new File(filename);
	    // if file doesn't exists, then create it
	    if (!file.exists()) {
		file.createNewFile();
	    }
	    FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); //true to append
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(content + "\n");
	    bw.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
