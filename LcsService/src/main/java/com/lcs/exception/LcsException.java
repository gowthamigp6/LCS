package com.lcs.exception;

public class LcsException extends Exception{

	private String message;
	
	public LcsException(String message) {
		this.message = message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
