package com.database.internarispital.views.doctors.edit;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.doctors.DoctorData;

public class EditDoctorsViewModel implements IEditDoctorsViewModel
{
	private EditDoctorsViewController mViewController;
	private DataBase mDataBase;
	
	public EditDoctorsViewModel(EditDoctorsViewController controller, DataBase dataBase)
	{
		mViewController = controller;
		mDataBase = dataBase;
		if(mViewController != null)
		{
			configController();
		}
	}
	
	private void configController()
	{
		mViewController.setViewModel(this);
		mViewController.setDoctors(mDataBase.getDoctors());		
	}
	
	public void addDoctor(DoctorData data)
	{
		Doctor doctor = mDataBase.insertDoctor(data);
		mViewController.addDoctor(doctor);
	}
	
	public void removeDoctor(Doctor doctor)
	{
		mDataBase.setDoctorInactive(doctor);
	}
}
