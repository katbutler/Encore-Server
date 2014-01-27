package com.katbutler.encore.logging;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.katbutler.encore.common.Utilities;

public class EncoreLogger {
	
	/**
	 * Logger ID.
	 */
	public static String LOGGER_ID = "com.katbutler.encore.logging";

	/**
	 * Default logger.
	 */
	private static EncoreLogger encoreLogger;

	/**
	 * Underlying Java logger, used by the default logger.
	 */
	private Logger logger;
	
	// Constants
	private static String SPACE = " ";
	private static String LF = "\n";
	private static String ALL = "ALL";
	private static String SEVERE = "SEVERE";
	private static String OFF = "OFF";
	//set the log file
	private static String LOG_FILE_NAME = "/tmp/Encore-Server.log";
	
	/**
	 * Returns default logger.
	 * 
	 * @return
	 */
	public static EncoreLogger getDefault(){
		if (encoreLogger == null)
			 encoreLogger = new EncoreLogger();
		return encoreLogger;
	}
	
	/**
	 * Private constructor, used within the class.
	 */
	private EncoreLogger () {
		
		try {
			// Create an appending file handler
			boolean append = true;
			FileHandler handler = new FileHandler(LOG_FILE_NAME, append); //read file name from file if you want to dynamically change it
			handler.setFormatter(new SimpleFormatter());
			String level = EncoreLogger.ALL; //read level from file if you want to dynamically change it
			
			if (level.equals(EncoreLogger.ALL)) {
				handler.setLevel(Level.ALL);
			}
			else if (level.equals(EncoreLogger.SEVERE)) {
				handler.setLevel(Level.SEVERE);
			}
			else if (level.equals(EncoreLogger.OFF)) {
				handler.setLevel(Level.OFF);
			}
			else {
				handler.setLevel(Level.ALL);
			}
			// Add to the desired logger
			setLogger(Logger.getLogger(LOGGER_ID));
			getLogger().addHandler(handler);
		} catch (IOException e) {
		}
	}

	/**
	 * Sets receiver's Java logger.
	 * 
	 * @param logger
	 */
	private void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * Returns receiver's Java logger.
	 * 
	 * @return
	 */
	private Logger getLogger() {
		return logger;
	}
	
	/**
	 * Formats given parameters for logging.
	 * Used by other logging methods before logging happens.
	 * 
	 * @param object
	 * @param method
	 * @param message
	 * @param exception
	 * @return
	 */
	private String format(Object object, String method, String message, Exception exception) {
		
		StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
		StringBuffer buffer = new StringBuffer();
		
		if (object == null) {
			buffer.append(method + SPACE + message + SPACE);
		} else {
			buffer.append(object.getClass().getName() + SPACE + method + SPACE + message + SPACE);
		}
		if (exception != null) {
			 exception.printStackTrace(printWriter);
			 printWriter.close();
		     buffer.append(stringWriter.toString());
		}
		buffer.append(LF);
		return buffer.toString();
	}
	
	/**
	 * Logs given parameters as a warning.
	 * 
	 * @param object
	 * @param method
	 * @param message
	 * @param exception
	 */
	public void warn(Object object, String method, String message, Exception exception) {
		
		String displayMessage = format (object, method, message, exception);
		getLogger().log(Level.WARNING, displayMessage, object);
		//showMessage("Warning log generated,");
	}
	
	/**
	 * Logs given parameters as an info. 
	 * 
	 * @param object
	 * @param method
	 * @param message
	 * @param exception
	 */
	public void info(Object object, String method, String message, Exception exception) {
		
		String displayMessage = format (object, method, message, exception);
		getLogger().log(Level.INFO, displayMessage, object);
		//showMessage("Info log generated,");
	}
	
	/**
	 * Logs given parameters as severe.
	 * 
	 * @param object
	 * @param method
	 * @param message
	 * @param exception
	 */
	public void severe(Object object, String method, String message, Exception exception) {
		
		String displayMessage = format (object, method, message, exception);
		getLogger().log(Level.SEVERE, displayMessage, object);
		//showMessage("Severe log generated,");
	}
	
	@SuppressWarnings("unused")
	private void showMessage(String message) {
		
		String newMessage = message + SPACE + "check log file: " + LOG_FILE_NAME;
		Utilities.showMessage(newMessage);
	}
}

