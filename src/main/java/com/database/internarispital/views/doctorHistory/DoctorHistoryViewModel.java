package com.database.internarispital.views.doctorHistory;

import javafx.collections.ObservableList;
import javafx.util.Callback;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.Consultation;
import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.patients.HospitalizedPatient;

public class DoctorHistoryViewModel implements IDoctorHistoryViewModel 
{
	private DoctorHistoryViewController mDoctorHistoryViewController;
	private DataBase mDataBase;
	private Doctor mDoctor;
	private Callback<HospitalizedPatient, Void> mShowPatientsRecord; 
	
	public DoctorHistoryViewModel(DoctorHistoryViewController viewController, DataBase database, Doctor doctor, Callback<HospitalizedPatient, Void> showPatientsRecord)
	{
		mDoctorHistoryViewController = viewController;
		mDataBase = database;
		mDoctor = doctor;
		mShowPatientsRecord = showPatientsRecord;
	
		assert(mDoctorHistoryViewController != null);
		if(mDoctorHistoryViewController != null)
		{
			configController();
		}
	}

	private void configController()
	{
		mDoctorHistoryViewController.setViewModel(this);
		mDoctorHistoryViewController.setOnShowPatientsRecord(mShowPatientsRecord);
		mDoctorHistoryViewController.setDoctorHistory(mDataBase.getDoctorHistory(mDoctor));
	}
	
	public void showDoctorHistory(String beginDate, String endDate, boolean currentlyAdmittedCkB, boolean showAllHistory)
	{
		ObservableList<Consultation> consultations = mDataBase.getDoctorHistory(mDoctor, beginDate, endDate, currentlyAdmittedCkB, showAllHistory);
		mDoctorHistoryViewController.setDoctorHistory(consultations);	
	}
}
