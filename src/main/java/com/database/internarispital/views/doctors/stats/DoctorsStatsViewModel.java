package com.database.internarispital.views.doctors.stats;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.doctors.Doctor;

public class DoctorsStatsViewModel implements IDoctorsStatsViewModel
{
	private DoctorsStatsViewController mViewController;
	private DataBase mDataBase;
	
	public DoctorsStatsViewModel(DoctorsStatsViewController controller, DataBase dataBase)
	{
		mViewController = controller;
		mDataBase = dataBase;
		if(mViewController != null)
		{
			configController();
		}
	}
	
	private void configController()
	{
		mViewController.setViewModel(this);
		mViewController.setActiveDoctorsCount(mDataBase.getActiveDoctorsCount());
		mViewController.setConsultationsMadeCount(mDataBase.getConsultationsMadeCount());
		mViewController.setMostActiveDoctor(mDataBase.getMostActiveDoctor());
		mViewController.setMostCommonDiagnostic(mDataBase.getMostCommonDiagnostic());
		setConsultationsCountMadeByEachDoctor();
	}
	
	private void setConsultationsCountMadeByEachDoctor()
	{
		Map<Doctor, Integer> doctorConsults = new HashMap<Doctor, Integer>();
		
		ObservableList<Doctor> doctors = mDataBase.getActiveDoctors();
		for(Doctor doctor: doctors)
		{
			int consults = mDataBase.getConsultationsCount(doctor);
			doctorConsults.put(doctor, consults);
		}
		mViewController.setConsultationsCountMadeByEachDoctor(doctorConsults);
	}
}
