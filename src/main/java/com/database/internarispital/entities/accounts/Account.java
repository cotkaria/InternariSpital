package com.database.internarispital.entities.accounts;

public class Account
{
	private int mId;
	private String mName;
	private String mPassword;
	private AccountTypes mAccountType;
	
	public Account(int id, String name, String password, String accountType)
	{
		this(id, name, password, AccountTypes.getValueFromString(accountType));
	}
	
	public Account(int id, String name, String password, AccountTypes accountType)
	{
		mId = id;
		mName = name;
		mPassword = password;
		mAccountType = accountType;
	}
	
	public int getId()
	{
		return mId;
	}
	public String getName()
	{
		return mName;
	}
	public String getPassword()
	{
		return mPassword;
	}
	public AccountTypes getAccountType()
	{
		return mAccountType;
	}
}
