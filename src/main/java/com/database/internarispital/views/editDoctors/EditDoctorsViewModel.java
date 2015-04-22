package com.database.internarispital.views.editDoctors;

import javafx.util.Callback;

import com.database.internarispital.DataBase;

public class EditDoctorsViewModel implements IEditDoctorsViewModel
{
	private EditDoctorsViewController mViewController;
	private DataBase mDataBase;
	private Callback<Void, Void> mReturnCallback;
	
	public EditDoctorsViewModel(EditDoctorsViewController controller, DataBase dataBase, 
			Callback<Void, Void> returnCallback)
	{
		mViewController = controller;
		mDataBase = dataBase;
		mReturnCallback = returnCallback;
		if(mViewController != null)
		{
			configController();
		}
	}
	
	private void configController()
	{
		mViewController.setViewModel(this);
		mViewController.setOnExit(mReturnCallback);
	}
}
