package com.database.internarispital.entities.doctors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DoctorData
{
	private StringProperty mDoctorName;
	private StringProperty mDoctorSurname;
	private StringProperty mSpeciality;
	private StringProperty mGrade;
	
	public DoctorData(String doctorName, String doctorSurname, String grade, String speciality)
	{
		mDoctorName = new SimpleStringProperty(doctorName);
		mDoctorSurname = new SimpleStringProperty(doctorSurname);
		mGrade = new SimpleStringProperty(grade);
		mSpeciality = new SimpleStringProperty(speciality);
	}
	public DoctorData(DoctorData data)
	{
		this(data.mDoctorName.getValue(), data.mDoctorSurname.getValue(), data.mGrade.getValue(), data.mSpeciality.getValue());
	}
	public StringProperty doctorNameProperty()
	{
		return mDoctorName;
	}
	public StringProperty doctorSurnameProperty()
	{
		return mDoctorSurname;
	}
	
	public StringProperty gradeProperty()
	{
		return mGrade;
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
