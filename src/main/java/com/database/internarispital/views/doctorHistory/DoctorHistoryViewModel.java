package com.database.internarispital.views.doctorHistory;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.doctors.Doctor;

public class DoctorHistoryViewModel implements IDoctorHistoryViewModel 
{
	private DoctorHistoryViewController mViewController;
	private DataBase mDataBase;
	private Doctor mDoctor;
	
	public DoctorHistoryViewModel(DoctorHistoryViewController viewController, DataBase database, Doctor doctor)
	{
		mViewController = viewController;
		mDataBase = database;
		mDoctor = doctor;
	
		assert(mViewController != null);
		if(mViewController != null)
		{
			configController();
		}
	}

	private void configController()
	{
		mViewController.setViewModel(this);
		mViewController.setDoctorHistory(mDataBase.getDoctorHistory(mDoctor));
	}
}
