package com.database.internarispital.exceptions;

public class MissingInputException extends Exception
{
	private static final long serialVersionUID = 1L;

	public MissingInputException(String message)
	{
		super(message);
	}
}
