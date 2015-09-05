package com.database.internarispital.views.doctors.diagnosticate;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.DiagCategory;
import com.database.internarispital.entities.Diagnostic;
import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.patients.HospitalizedPatient;

public class DoctorsViewModel implements IDoctorsViewModel
{
	private DoctorsViewController mDoctorsViewController;
	private DataBase mDataBase;
	
	public DoctorsViewModel(DoctorsViewController doctorsViewController, DataBase dataBase)
	{
		mDoctorsViewController = doctorsViewController;
		mDataBase = dataBase;
		if(doctorsViewController != null)
		{
			configController();
		}
	}
	
	private void configController()
	{
		mDoctorsViewController.setViewModel(this);
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
