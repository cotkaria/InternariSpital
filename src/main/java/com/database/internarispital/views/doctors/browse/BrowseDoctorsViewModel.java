package com.database.internarispital.views.doctors.browse;

import com.database.internarispital.DataBase;

public class BrowseDoctorsViewModel
{
	BrowseDoctorsViewController mViewController;
	DataBase mDataBase;
	
	public BrowseDoctorsViewModel(BrowseDoctorsViewController viewController, DataBase dataBase)
	{
		mViewController = viewController;
		mDataBase = dataBase;
		
		configureViewController();
	}
	
	private void configureViewController()
	{
		mViewController.setDoctors(mDataBase.getDoctors());
	}
}
