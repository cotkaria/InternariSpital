package com.database.internarispital.views.login;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.accounts.Account;
import com.database.internarispital.login.LoginManager;
import com.database.internarispital.util.DialogHelper;
import com.database.internarispital.views.ViewManager;

public class LoginViewModel implements ILoginViewModel
{
	LoginViewController mViewController;
	DataBase mDataBase;
	
	public LoginViewModel(LoginViewController viewController, DataBase dataBase)
	{
		mViewController = viewController;
		mDataBase = dataBase;
		mViewController.setViewModel(this);
	}

	@Override
	public void login(String userName, String password)
	{
		Account account = mDataBase.login(userName, password);
		if(account != null)
		{
			onLoginSucceded(account);
		}
		else
		{
			onLoginFailed();
		}
	}
	
	private void onLoginSucceded(Account account)
	{
		LoginManager.setLoginAccount(account);
		ViewManager.showMainView();
	}
	
	private void onLoginFailed()
	{
		DialogHelper.showErrorPopup("Login failed! Your user name and/or password are invalid.");
	}
}
