package com.database.internarispital.views.records;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.patients.Patient;

public class RecordsViewModel implements IRecordsViewModel 
{
	private RecordsVewController mRecordsVewController;
	private DataBase mDataBase;
	private Patient mPatient;
	
	public RecordsViewModel(RecordsVewController recordsViewController, DataBase database, Patient patient)
	{
		mRecordsVewController = recordsViewController;
		mDataBase = database;
		mPatient = patient;
	
		assert(mRecordsVewController != null);
		if(mRecordsVewController != null)
		{
			configController();
		}
	}

	private void configController()
	{
		mRecordsVewController.setViewModel(this);
		mRecordsVewController.setHospitalizationPeriods(mDataBase.getHospitalizationPeriods(mPatient));
	}
}
