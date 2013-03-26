package clueGame;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class BadConfigFormatException extends RuntimeException {
	private FileWriter writer;
	public BadConfigFormatException() {
		// TODO Auto-generated constructor stub
	}

	public BadConfigFormatException(String arg0) {
		super(arg0);
		try {
			writer = new FileWriter("errorLog.txt");
			writer.write(arg0);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
