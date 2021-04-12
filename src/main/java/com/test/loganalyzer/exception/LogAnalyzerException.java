package com.test.loganalyzer.exception;

import java.util.logging.Logger;

public class LogAnalyzerException extends RuntimeException {
	Logger log = Logger.getLogger("LogAnalyzerException.class");

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LogAnalyzerException(String message) {
		super(message);
		log.warning(message);
	}

}
