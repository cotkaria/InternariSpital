package com.database.internarispital.views.doctors.create;

import javafx.stage.Stage;
import javafx.util.Callback;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.doctors.DoctorData;

public class CreateDoctorViewModel implements ICreateDoctorViewModel
{
	CreateDoctorViewController mViewController;
	DataBase mDataBase;
	Stage mStage;
	Callback<Doctor, Void> mOnDoctorCreatedCB;
	
	public CreateDoctorViewModel(CreateDoctorViewController viewController, DataBase dataBase, 
		Stage stage, Callback<Doctor, Void> onDoctrCreatedCB)
	{
		mViewController = viewController;
		mDataBase = dataBase;
		mStage = stage;
		mOnDoctorCreatedCB = onDoctrCreatedCB;
		mViewController.setViewModel(this);
	}
	
	@Override
	public void createDoctor(DoctorData data)
	{
		Doctor patient = mDataBase.insertDoctor(data);
		if(mOnDoctorCreatedCB != null)
		{
			mOnDoctorCreatedCB.call(patient);
		}
		mStage.close();
	}
}
