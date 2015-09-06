package com.database.internarispital.views.patients.hospitalize;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.facilities.Bed;
import com.database.internarispital.entities.facilities.Section;
import com.database.internarispital.entities.facilities.Ward;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;
import com.database.internarispital.views.ViewManager;

public class PatientsViewModel implements IPatientsViewModel
{
	private HospitalizationViewController mPatientsViewController;
	private DataBase mDataBase;
	
	public PatientsViewModel(HospitalizationViewController patientsViewController, DataBase dataBase)
	{
		mPatientsViewController = patientsViewController;
		mDataBase = dataBase;
		if(mPatientsViewController != null)
		{
			configController();
		}
	}
	
	private void configController()
	{
		mPatientsViewController.setViewModel(this);
		mPatientsViewController.setHospitalizedPatients(mDataBase.getHospitalizedPatients());
		mPatientsViewController.setNotHospitalizedPatients(mDataBase.getNotHospitalizedPatients());
		mPatientsViewController.setSections(mDataBase.getSections());
	}
	
	public void createPatient()
	{
		ViewManager.showCreatePatientView(patient -> 
		{
			onPatientCreated(patient);
			return null;
		});
	}
	
	private void onPatientCreated(Patient patient)
	{
		mPatientsViewController.addPatientAndSelect(patient);
	}
	
	public void selectSection(Section section)
	{
		mPatientsViewController.setWards(mDataBase.getWards(section.sectionIdProperty().getValue()));
	}
	
	public void selectWard(Ward ward)
	{
		mPatientsViewController.setBeds(mDataBase.getVacantBeds(ward.wardIdProperty().getValue()));
	}
	
	public void hospitalizePatient(Patient patient, Bed bed)
	{
		HospitalizedPatient hospitalizedPatient = mDataBase.hospitalizePatient(patient, bed.bedIdProperty().getValue());
		mPatientsViewController.removeNotHospitalizedPatient(patient);
		mPatientsViewController.addHospitalizedPatient(hospitalizedPatient);
		mPatientsViewController.setBeds(mDataBase.getVacantBeds(bed.wardIdProperty().getValue()));
	}
	
	public void dischargePatient(HospitalizedPatient hospitalizedPatient)
	{
		Bed bed = mDataBase.getPatientBed(hospitalizedPatient.getPatientId());
		
		mDataBase.dischargePatient(hospitalizedPatient.getPatientId());
		mPatientsViewController.removeHospitalizedPatient(hospitalizedPatient);
		mPatientsViewController.addNotHospitalizedPatient(hospitalizedPatient);
		
		int wardToRefreshID = bed.wardIdProperty().getValue();
		if(mPatientsViewController.getSelectedWardId() == wardToRefreshID)
		{
			mPatientsViewController.setBeds(mDataBase.getVacantBeds(wardToRefreshID));
		}
	}
}
