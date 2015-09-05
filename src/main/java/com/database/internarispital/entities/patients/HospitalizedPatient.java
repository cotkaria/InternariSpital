package com.database.internarispital.entities.patients;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HospitalizedPatient extends Patient
{
	private StringProperty mSectionName;
	private IntegerProperty mWardNumber;
	private IntegerProperty mBedNumber;
	private boolean mIsHospitalized;
	
	public HospitalizedPatient(Patient patient)
	{
		this(patient, "", -1, -1);
		mIsHospitalized = false;
	}
	
	public HospitalizedPatient(Patient patient, String sectionName, int wardNumber, int bedNumber)
	{
		super(patient);
		mSectionName = new SimpleStringProperty(sectionName);
		mWardNumber = new SimpleIntegerProperty(wardNumber);
		mBedNumber = new SimpleIntegerProperty(bedNumber);
		mIsHospitalized = true;
	}

	public StringProperty sectionNameProperty()
	{
		return mSectionName;
	}
	public IntegerProperty wardNumberProperty()
	{
		return mWardNumber;
	}
	public IntegerProperty bedNumberProperty()
	{
		return mBedNumber;
	}
	public boolean isHospitalized()
	{
		return mIsHospitalized;
	}
}
