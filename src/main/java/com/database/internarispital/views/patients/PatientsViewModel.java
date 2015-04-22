package com.database.internarispital.views.patients;

import javafx.util.Callback;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.Bed;
import com.database.internarispital.entities.Section;
import com.database.internarispital.entities.Ward;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;
import com.database.internarispital.entities.patients.PatientData;

public class PatientsViewModel implements IPatientsViewModel
{
	private PatientsViewController mPatientsViewController;
	private DataBase mDataBase;
	private Callback<Void,Void> mShowDoctorsView;
	private Callback<Void,Void> mShowAdminView;
	
	public PatientsViewModel(PatientsViewController patientsViewController, DataBase dataBase
			, Callback<Void,Void> showDoctorsView
			, Callback<Void,Void> showAdminView)
	{
		mPatientsViewController = patientsViewController;
		mDataBase = dataBase;
		mShowDoctorsView = showDoctorsView;
		mShowAdminView = showAdminView;
		if(mPatientsViewController != null)
		{
			configController();
		}
		
	}
	
	private void configController()
	{
		mPatientsViewController.setViewModel(this);
		mPatientsViewController.setPatients(mDataBase.getHospitalizedPatients());
		mPatientsViewController.setSection(mDataBase.getSections());
		mPatientsViewController.setOnShowDoctorsView(mShowDoctorsView);
		mPatientsViewController.setOnShowAdminView(mShowAdminView);
	}
	public void selectSection(Section section)
	{
		mPatientsViewController.setWards(mDataBase.getWards(section.sectionIdProperty().getValue()));
	}
	
	public void selectWard(Ward ward)
	{
		mPatientsViewController.setBeds(mDataBase.getVacantBeds(ward.wardIdProperty().getValue()));
	}
	
	public void hospitalizePatient(PatientData patientData, Bed bed)
	{
		Patient patient = mDataBase.insertNewPatient(patientData);
		HospitalizedPatient hospitalizedPatient = mDataBase.hospitalizePatient(patient, bed.bedIdProperty().getValue());
		mPatientsViewController.addPatient(hospitalizedPatient);
		mPatientsViewController.setBeds(mDataBase.getVacantBeds(bed.wardIdProperty().getValue()));
		mPatientsViewController.resetInsertedValues();
	}
	public void dischargePatient(HospitalizedPatient hospitalizedPatient)
	{
		Bed bed = mDataBase.getPatientBed(hospitalizedPatient.getPatientId());
		
		mDataBase.dischargePatient(hospitalizedPatient.getPatientId());
		mPatientsViewController.removePatient(hospitalizedPatient);
		
		int wardToRefreshID = bed.wardIdProperty().getValue();
		if(mPatientsViewController.getSelectedWardId() == wardToRefreshID)
		{
			mPatientsViewController.setBeds(mDataBase.getVacantBeds(wardToRefreshID));
		}
	}
}
