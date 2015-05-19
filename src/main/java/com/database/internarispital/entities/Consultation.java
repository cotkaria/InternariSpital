package com.database.internarispital.entities;


import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.patients.HospitalizedPatient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Consultation 
{
	private int mConsultationId;
	private HospitalizedPatient mPatient;
	private Doctor mDoctor;
	private Diagnostic mDiagnostic;
	private StringProperty mConsultationDate;
	
	public Consultation(HospitalizedPatient patient)
	{
		this(-1, patient, new Doctor(), new Diagnostic(), "");
	}
	
	public Consultation(int consultationId, HospitalizedPatient patient, Doctor doctor, Diagnostic diagnostic, String consultationDate)
	{
		mConsultationDate = new SimpleStringProperty(consultationDate);
		mPatient = patient;
		mDoctor = doctor;
		mDiagnostic = diagnostic;
		mConsultationId = consultationId;
	}
	
	public int getConsultationId()
	{
		return mConsultationId;
	}
	
	public HospitalizedPatient getPatient()
	{
		return mPatient;
	}
	
	public Doctor getDoctor()
	{
		return mDoctor;
	}
	
	public Diagnostic getDiagnostic()
	{
		return mDiagnostic;
	}
	
	public StringProperty consultationDateProperty()
	{
		return mConsultationDate;
	}	
}
