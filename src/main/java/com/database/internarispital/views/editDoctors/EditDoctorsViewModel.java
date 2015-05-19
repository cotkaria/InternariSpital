package com.database.internarispital.views.editDoctors;

import javafx.util.Callback;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.doctors.DoctorData;

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
		mViewController.setDoctors(mDataBase.getDoctors());
	}
	
	public void addDoctor(DoctorData data)
	{
		Doctor doctor = mDataBase.insertDoctor(data);
		mViewController.addDoctor(doctor);
		//TODO add to the list of doctors of the ViewController
	}
}
