package com.database.internarispital.views.doctors;

import javafx.util.Callback;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.DiagCategory;
import com.database.internarispital.entities.Diagnostic;
import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;

public class DoctorsViewModel implements IDoctorsViewModel
{
	private DoctorsViewController mDoctorsViewController;
	private DataBase mDataBase;
	private Callback<Void, Void> mShowPatientsView;
	private Callback<HospitalizedPatient, Void> mShowPatientsRecord;
	
	public DoctorsViewModel(DoctorsViewController doctorsViewController, DataBase dataBase, 
			Callback<Void, Void> showPatientsView,
			Callback<HospitalizedPatient, Void> showPatientsRecord)
	{
		mDoctorsViewController = doctorsViewController;
		mDataBase = dataBase;
		mShowPatientsView = showPatientsView;
		mShowPatientsRecord = showPatientsRecord;
		if(doctorsViewController != null)
		{
			configController();
		}
	}
	
	private void configController()
	{
		mDoctorsViewController.setViewModel(this);
		mDoctorsViewController.setOnShowPatientsView(mShowPatientsView);
		mDoctorsViewController.setOnShowPatientsRecord(mShowPatientsRecord);
		mDoctorsViewController.setDoctors(mDataBase.getDoctors());
		mDoctorsViewController.setConsultations(mDataBase.getConsultations());
		mDoctorsViewController.setDiagCategories(mDataBase.getDiagCategories());
	}
	public void selectDiagCategory(DiagCategory diagCategory)
	{
		mDoctorsViewController.setDiagnostics(mDataBase.getSpecificCategoryDiagnostics(diagCategory.getDiagCategoryId()));
	}
	public void createConsultation(HospitalizedPatient patient, Doctor doctor, Diagnostic diagnostic, String consultationDate)
	{
		mDataBase.createConsultation(patient, doctor, diagnostic, consultationDate);
		mDoctorsViewController.setConsultations(mDataBase.getConsultations());
	}
}
