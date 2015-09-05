package com.database.internarispital.entities.accounts;

public enum AccountTypes
{
	Visitor(1),
	Patient(2),
	Doctor(3),
	Admin(10);
	
	private int mCleareance;

	private AccountTypes(int clearence)
	{
		mCleareance = clearence;
	}
	
	public int getClearenceLevel()
	{
		return mCleareance;
	}
	
	public static AccountTypes getValueFromString(String name)
	{
		AccountTypes type = Visitor;
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
