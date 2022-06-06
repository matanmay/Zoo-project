package Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/** 
 * A utility class for logging output text to a file
 * note: final means that we can't extends this class
 * @author Java Course Team 2020 - Gaston & Or
 * @author University Of Haifa - Israel
 */
public final class MyFileLogWriter{
	
	/** an output file */
	static private File outputLogFile;
	
	/** a file writer buffer */
	static private FileWriter writer; 
	
	/**
	 * Creates a file and a writer for logging
	 */
	public static void initializeMyFileWriter(){
		 /* If we don't want to override the same file each time then
		  * we can use Calendar.getInstance().getTimeInMillis() by adding it 
		  * to the name of the file. 
		  */
		 outputLogFile = new File("output.txt");
		try {
            writer = new FileWriter(outputLogFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Writes a text message to the log file in a separate line
	 * @param message
	 */
	public static void println(String message){

		try {
			writer.write(message);
			writer.write(System.getProperty("line.separator"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the log file (by closing the file writer)
	 */
	public static void saveLogFile(){
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}//END OF ~ MyFileLogWriter
