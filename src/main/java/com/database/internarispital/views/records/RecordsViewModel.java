package com.database.internarispital.views.records;

import javafx.collections.ObservableList;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.Consultation;
import com.database.internarispital.entities.HospitalizationPeriod;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;

public class RecordsViewModel implements IRecordsViewModel 
{
	private RecordsViewController mRecordsVewController;
	private DataBase mDataBase;
	private HospitalizedPatient mPatient;
	
	public RecordsViewModel(RecordsViewController recordsViewController, DataBase database, HospitalizedPatient patient)
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
	@Override
	public void requestConsultations(HospitalizationPeriod period) 
	{
		ObservableList<Consultation> consultations = mDataBase.getConsulationsForPeriod(mPatient, period);
		mRecordsVewController.setConsultations(consultations);
	}
}
