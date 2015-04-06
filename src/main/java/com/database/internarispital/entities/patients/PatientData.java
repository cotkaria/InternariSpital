package com.database.internarispital.entities.patients;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PatientData 
{
	private StringProperty mFirstName;
	private StringProperty mLastName;
	private StringProperty mBirthDate;
	
	public PatientData(PatientData patientData)
	{
		this(patientData.firstNameProperty().getValue(), patientData.lastNameProperty().getValue(), patientData.birthDateProperty().getValue());
	}
	public PatientData( String firstName, String lastName, String birthDate)
	{
		mFirstName = new SimpleStringProperty(firstName);
		mLastName = new SimpleStringProperty(lastName);
		mBirthDate = new SimpleStringProperty(birthDate);			
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
}