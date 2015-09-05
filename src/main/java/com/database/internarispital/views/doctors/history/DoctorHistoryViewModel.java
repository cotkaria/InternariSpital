package com.database.internarispital.views.doctors.history;

import javafx.collections.ObservableList;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.Consultation;
import com.database.internarispital.entities.doctors.Doctor;

public class DoctorHistoryViewModel implements IDoctorHistoryViewModel 
{
	private DoctorHistoryViewController mDoctorHistoryViewController;
	private DataBase mDataBase;
	private Doctor mDoctor;
	
	public DoctorHistoryViewModel(DoctorHistoryViewController viewController, DataBase database, Doctor doctor)
	{
		mDoctorHistoryViewController = viewController;
		mDataBase = database;
		mDoctor = doctor;
	
		assert(mDoctorHistoryViewController != null);
		if(mDoctorHistoryViewController != null)
		{
			configController();
		}
	}

	private void configController()
	{
		mDoctorHistoryViewController.setViewModel(this);
		mDoctorHistoryViewController.setDoctorHistory(mDataBase.getDoctorHistory(mDoctor));
	}
	
	@Override
	public void showDoctorHistory(String beginDate, String endDate, boolean currentlyAdmittedCkB, boolean showAllHistory)
	{
		ObservableList<Consultation> consultations = mDataBase.getDoctorHistory(mDoctor, beginDate, endDate, currentlyAdmittedCkB, showAllHistory);
		mDoctorHistoryViewController.setDoctorHistory(consultations);	
	}
}
