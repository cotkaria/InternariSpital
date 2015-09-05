package com.database.internarispital.views.doctors.create;

import javafx.stage.Stage;
import javafx.util.Callback;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.accounts.Account;
import com.database.internarispital.entities.accounts.AccountTypes;
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
		createAccount(data);
		Doctor patient = mDataBase.insertDoctor(data);
		if(mOnDoctorCreatedCB != null)
		{
			mOnDoctorCreatedCB.call(patient);
		}
		mStage.close();
	}
	
	private void createAccount(DoctorData doctorData)
	{
		String userName = doctorData.nameProperty().getValue() + "." + doctorData.surnameProperty().getValue();
		String password = "doctor"; //FIXME
		Account account = mDataBase.insertAccount(userName, password, AccountTypes.Doctor);
		doctorData.setAccountId(account.getId());
	}
}
