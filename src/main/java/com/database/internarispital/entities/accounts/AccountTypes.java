package com.database.internarispital.entities.accounts;

public enum AccountTypes
{
	Patient,
	Doctor,
	Admin,
	Root;
	
	public static AccountTypes getValueFromString(String name)
	{
		AccountTypes type = Patient;
		try
		{
			type = valueOf(name);
		}
		catch(IllegalArgumentException|NullPointerException ex)
		{
			//ignore
		}
		return type;
	}
}
