package com.database.internarispital.views.menuBar;

import com.database.internarispital.entities.accounts.Account;
import com.database.internarispital.login.ILoginListener;
import com.database.internarispital.login.LoginManager;
import com.database.internarispital.views.ViewManager;

public class MenuBarViewModel implements IMenuBarViewModel, ILoginListener
{
	MenuBarViewController mViewController;
	
	public MenuBarViewModel(MenuBarViewController viewController)
	{
		mViewController = viewController;
		
		mViewController.setViewModel(this);
		mViewController.showNavigationBar(false);
		LoginManager.registerForLoginChanges(this);
	}
	
	@Override
	public void onLoginChanged(Account loginAccount)
	{
		if(loginAccount != null)
		{
			mViewController.setLoginName(getLoginName(loginAccount));
			mViewController.setAccountType(loginAccount.getAccountType());
			mViewController.showNavigationBar(true);
		}
		else
		{
			mViewController.showNavigationBar(false);
		}
	}
	
	private String getLoginName(Account account)
	{
		String name = account.getName() + ", " + account.getAccountType().name();
		return name;
	}

	@Override
	public void logout()
	{
		LoginManager.clearLoginAccount();
		ViewManager.showLoginView();
	}
}
