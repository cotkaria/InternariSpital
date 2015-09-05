package com.database.internarispital.entities.doctors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DoctorData
{
	private StringProperty mDoctorName;
	private StringProperty mDoctorSurname;
	private StringProperty mSpeciality;
	private StringProperty mGrade;
	private int mAccountId;
	
	public DoctorData(DoctorData data)
	{
		this(data.mDoctorName.getValue(), data.mDoctorSurname.getValue(), 
				data.mGrade.getValue(), data.mSpeciality.getValue(),
				data.mAccountId);
	}
	public DoctorData(String doctorName, String doctorSurname, String grade, 
			String speciality)
	{
		this(doctorName, doctorSurname, 
				grade, speciality,
				-1);
	}
	public DoctorData(String doctorName, String doctorSurname, String grade, 
			String speciality, int accountId)
	{
		mDoctorName = new SimpleStringProperty(doctorName);
		mDoctorSurname = new SimpleStringProperty(doctorSurname);
		mGrade = new SimpleStringProperty(grade);
		mSpeciality = new SimpleStringProperty(speciality);
		mAccountId = accountId;
	}
	public StringProperty nameProperty()
	{
		return mDoctorName;
	}
	public StringProperty surnameProperty()
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
	
	public void setAccountId(int id)
	{
		mAccountId = id;
	}
	
	public int getAccountId()
	{
		return mAccountId;
	}
	
	@Override
	public String toString()
	{
		return mDoctorName.getValue() + " " + mDoctorSurname.getValue() + ", " + mSpeciality.getValue();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean areEqual = true;
		if(obj != this)
		{
			if(obj instanceof DoctorData)
			{
				DoctorData other = (DoctorData)obj;
				areEqual = (areEqual && mDoctorName.equals(other.mDoctorName));
				areEqual = (areEqual && mDoctorSurname.equals(other.mDoctorSurname));
				areEqual = (areEqual && mGrade.equals(other.mGrade));
				areEqual = (areEqual && mSpeciality.equals(other.mSpeciality));
			}
		}
		return areEqual;
	}
}
