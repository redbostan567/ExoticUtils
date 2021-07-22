package com.kingmo.utils.logs;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Logger {

	
	/**
	 * In order to not take up insane amounts of memory. 
	 * {@link #gameStateLog} is reset every time the server or plugin is reloaded.
	 * 
	 * To add to this map, use {@link #log(String)}. 
	 */
	private static final Map<Date, String> gameStateLog = new HashMap<>();
	
	/**
	 * 
	 * @param str : the logged string
	 * @return the time and date at which the param is added to the log
	 */
	public static Date log(String str) {
		return log(str, LoggerType.GENERAL_MESSAGE);
	}
	
	public static Date log(String str, LoggerType type) {
		Date date = new Date();
		gameStateLog.put(date, type.getPrefix() + " " + str);
		return date;
	}
	/**
	 * 
	 * Prints the log to a desired file.
	 * 
	 * @param file : the file which the log is to be printed to.
	 * @throws IOException if the file is not readable
	 */
	public static void printLog(File file) throws IOException{
		if(!file.canWrite())throw new IOException("Cannot write to specified file");
		FileWriter writer = new FileWriter(file);
		for(Date date: gameStateLog.keySet())
			writer.write(date.toString() + " : " + gameStateLog.get(date) + System.lineSeparator());
		writer.close();
	}
	
	
}
