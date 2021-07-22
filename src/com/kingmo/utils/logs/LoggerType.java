package com.kingmo.utils.logs;

public enum LoggerType {
	/**
	 * General message. Not serious. Typical for logging
	 */
	GENERAL_MESSAGE("[MESSAGE]"),
	/**
	 * Warning message. Could lead to errors later on.
	 */
	WARNING_MESSAGE("[WARNING]"),
	/**
	 * Error message. This is serious and could lead to corruption. 
	 */
	ERROR_MESSAGE("[ERROR]");
	
	/**
	 * prefix used to indicate what kind of message this is.
	 */
	private String prefix;
	
	LoggerType(String prefix){
		this.prefix = prefix;
	}
	/**
	 * 
	 * @return prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	
}
