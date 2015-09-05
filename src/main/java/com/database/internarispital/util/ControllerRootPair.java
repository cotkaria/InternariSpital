package com.database.internarispital.util;

import javafx.scene.Parent;

public class ControllerRootPair
{
	private Object mController;
	private Parent mRoot;
	
	public ControllerRootPair(Object controller, Parent root)
	{
		mController = controller;
		mRoot = root;
	}
	
	public Object getController()
	{
		return mController;
	}
	public Parent getRoot()
	{
		return mRoot;
	}
}
