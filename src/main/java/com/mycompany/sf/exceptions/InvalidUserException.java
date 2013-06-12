package com.mycompany.sf.exceptions;

/*
 * Exception class for an invalid user when a salesforce user is not found
 * based on the credentials
 */
@SuppressWarnings("serial")
public class InvalidUserException extends Exception{
	public InvalidUserException(String message){
		super(message);
	}
}
