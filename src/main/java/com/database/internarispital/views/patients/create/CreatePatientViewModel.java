package com.database.internarispital.views.patients.create;

import javafx.stage.Stage;
import javafx.util.Callback;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.patients.Patient;
import com.database.internarispital.entities.patients.PatientData;

public class CreatePatientViewModel implements ICreatePatientViewModel
{
	CreatePatientViewController mViewController;
	DataBase mDataBase;
	Stage mStage;
	Callback<Patient, Void> mOnPatientCreateCB;
	
	public CreatePatientViewModel(CreatePatientViewController viewController, DataBase dataBase, 
		Stage stage, Callback<Patient, Void> onPatientCreateCB)
	{
		mViewController = viewController;
		mDataBase = dataBase;
		mStage = stage;
		mOnPatientCreateCB = onPatientCreateCB;
		mViewController.setViewModel(this);
	}
	
	@Override
	public void createPatient(PatientData patientData)
	{
		Patient patient = mDataBase.insertNewPatient(patientData);
		if(mOnPatientCreateCB != null)
		{
			mOnPatientCreateCB.call(patient);
		}
		mStage.close();
	}
}
