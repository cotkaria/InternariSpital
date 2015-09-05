package com.database.internarispital.views.menuBar;

public class MenuBarViewModel implements IMenuBarViewModel
{
	MenuBarViewController mViewController;
	
	public MenuBarViewModel(MenuBarViewController viewController)
	{
		mViewController = viewController;
		mViewController.setViewModel(this);
	}
}
