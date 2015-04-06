package com.database.internarispital.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Doctor {
	
	private IntegerProperty mDoctorId;
	private StringProperty mDoctorName;
	private StringProperty mDoctorSurname;
	private StringProperty mSpeciality;
	
	public Doctor()
	{
		this(-1, "", "", "");
	}
	public Doctor(int doctorId, String doctorName, String doctorSurname, String speciality)
	{
		mDoctorId = new SimpleIntegerProperty(doctorId)	;
		mDoctorName = new SimpleStringProperty(doctorName);
		mDoctorSurname = new SimpleStringProperty(doctorSurname);
		mSpeciality = new SimpleStringProperty(speciality);
	}
	public IntegerProperty doctorIdProperty()
	{
		return mDoctorId;
	}
	public StringProperty doctorNameProperty()
	{
		return mDoctorName;
	}
	public StringProperty doctorSurnameProperty()
	{
		return mDoctorSurname;
	}
	public StringProperty specialityProperty()
	{
		return mSpeciality;
	}
	
	public String getName()
	{
		return mDoctorName.getValue() + " " + mDoctorSurname.getValue();
	}
	@Override
	public String toString()
	{
		return mDoctorName.getValue() + " " + mDoctorSurname.getValue() + ", " + mSpeciality.getValue();
	}
	
}
