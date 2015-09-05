package com.database.internarispital.login;

import java.util.ArrayList;
import java.util.List;

import com.database.internarispital.entities.accounts.Account;

public class LoginManager
{
	private static Account mLoginAccount;
	private static List<ILoginListener> mLoginListeners = new ArrayList<ILoginListener>();
	
	public static void setLoginAccount(Account account)
	{
		if(mLoginAccount != account)
		{
			mLoginAccount = account;
			onLoginAccountChanged();
		}
	}
	public static Account getLoginAccount()
	{
		return mLoginAccount;
	}
	public static void clearLoginAccount()
	{
		setLoginAccount(null);
	}
	
	public static void registerForLoginChanges(ILoginListener listener)
	{
		if((listener != null) && (mLoginListeners.contains(listener) == false))
		{
			mLoginListeners.add(listener);
		}
	}
	public static void unregisterFromLoginChanges(ILoginListener listener)
	{
		if((listener != null) && mLoginListeners.contains(listener))
		{
			mLoginListeners.remove(listener);
		}
	}
	
	private static void onLoginAccountChanged()
	{
		for(ILoginListener listener: mLoginListeners)
		{
			listener.onLoginChanged(mLoginAccount);
		}
	}
}
