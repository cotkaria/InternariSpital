package com.database.internarispital.entities.patients;

public class Patient extends PatientData
{

	private int mPatientId;
	
	public Patient(Patient patient)
	{
		this(patient.getPatientId(), (PatientData)patient);
	}
	public Patient(int patientId, PatientData patientData)
	{
		super(patientData);
		mPatientId = patientId;
	}

	public int getPatientId()
	{
		return mPatientId;
	}
	
	public String getName()
	{
		return this.firstNameProperty().getValue() + " " + this.lastNameProperty().getValue();
	}
	
}
