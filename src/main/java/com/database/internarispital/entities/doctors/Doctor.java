package com.database.internarispital.entities.doctors;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Doctor extends DoctorData
{	
	private IntegerProperty mDoctorId;

	public Doctor()
	{
		this(-1, new DoctorData("", "", "", ""));
	}
	
	public Doctor(int doctorId, DoctorData doctorData)
	{
		super(doctorData);
		mDoctorId = new SimpleIntegerProperty(doctorId)	;
	}
	public IntegerProperty doctorIdProperty()
	{
		return mDoctorId;
	}
}
