package com.database.internarispital.entities.patients;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PatientData 
{
	private StringProperty mFirstName;
	private StringProperty mLastName;
	private StringProperty mBirthDate;
	private int mAccountId;
	
	public PatientData(PatientData patientData)
	{
		this(patientData.firstNameProperty().getValue(), patientData.lastNameProperty().getValue(), 
				patientData.birthDateProperty().getValue(), patientData.mAccountId);
	}
	public PatientData( String firstName, String lastName, String birthDate)
	{
		this(firstName, lastName, birthDate, -1);
	}
	
	public PatientData( String firstName, String lastName, String birthDate, int accountId)
	{
		mFirstName = new SimpleStringProperty(firstName);
		mLastName = new SimpleStringProperty(lastName);
		mBirthDate = new SimpleStringProperty(birthDate);
		mAccountId = accountId;
	}
	
	public StringProperty firstNameProperty()
	{
		return mFirstName;
	}
	public StringProperty lastNameProperty()
	{
		return mLastName;
	}
	public StringProperty birthDateProperty()
	{
		return mBirthDate;
	}
	public void setAccountId(int id)
	{
		mAccountId = id;
	}
	public int getAccountId()
	{
		return mAccountId;
	}
}