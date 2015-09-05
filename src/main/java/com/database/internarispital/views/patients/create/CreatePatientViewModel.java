package com.database.internarispital.views.patients.create;

import javafx.stage.Stage;
import javafx.util.Callback;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.accounts.Account;
import com.database.internarispital.entities.accounts.AccountTypes;
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
		createAccount(patientData);
		Patient patient = mDataBase.insertPatient(patientData);
		if(mOnPatientCreateCB != null)
		{
			mOnPatientCreateCB.call(patient);
		}
		mStage.close();
	}
	
	private void createAccount(PatientData patientData)
	{
		String userName = patientData.firstNameProperty().getValue() + "." + patientData.lastNameProperty().getValue();
		String password = "patient"; //FIXME
		Account account = mDataBase.insertAccount(userName, password, AccountTypes.Patient);
		patientData.setAccountId(account.getId());
	}
}
