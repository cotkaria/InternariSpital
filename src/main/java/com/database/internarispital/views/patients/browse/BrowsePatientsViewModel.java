package com.database.internarispital.views.patients.browse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.accounts.Account;
import com.database.internarispital.entities.accounts.AccountTypes;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.login.LoginManager;

public class BrowsePatientsViewModel
{
	private BrowsePatientsViewController mController;
	private DataBase mDataBase;
	
	public BrowsePatientsViewModel(BrowsePatientsViewController controller, DataBase database)
	{
		mController = controller;
		mDataBase = database;
		configureController();
	}
	
	private void configureController()
	{
		Account currentAccount = LoginManager.getLoginAccount();
		if((currentAccount != null) && (currentAccount.getAccountType() == AccountTypes.Patient))
		{
			showCurrentPatient(currentAccount);
		}
		else
		{
			showAllPatients();
		}
	}
	
	private void showCurrentPatient(Account account)
	{
		HospitalizedPatient patient = mDataBase.getHospitalizedPatient(account);
		if(patient != null)
		{
			ObservableList<HospitalizedPatient> patients = FXCollections.observableArrayList();
			patients.add(patient);
			mController.setPatients(patients);
		}
	}
	
	private void showAllPatients()
	{
		mController.setPatients(mDataBase.getAllPatients());
	}
}
