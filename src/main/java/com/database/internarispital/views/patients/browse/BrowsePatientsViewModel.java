package com.database.internarispital.views.patients.browse;

import javafx.collections.ObservableList;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;

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
		ObservableList<HospitalizedPatient> allPatients = mDataBase.getHospitalizedPatients();
		ObservableList<Patient> patients = mDataBase.getNotHospitalizedPatients();
		for(Patient patient: patients)
		{
			allPatients.add(new HospitalizedPatient(patient));
		}
		
		mController.setPatients(allPatients);
	}
}
