package com.mycompany.sf.exceptions;


/*
 * This is an exception class, that is used by the Salesforce auth class
 * when the authorization token has expired
 */
@SuppressWarnings("serial")
public class AuthTokenExpiredException extends Exception{
	public AuthTokenExpiredException(String message){
		super(message);
	}
}
