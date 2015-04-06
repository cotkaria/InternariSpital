package com.database.internarispital.entities;

import java.util.Date;

public class HospitalizationPeriod 
{
	private int mHospitalizationId;
	private int mPatientId;
	private String mAdmittanceDate;
	private String mDischargeDate;
	
	public HospitalizationPeriod(int hospitalizationId, int patientId, String admittanceDate, String dischargeDate)
	{
		mHospitalizationId = hospitalizationId;
		mPatientId = patientId;
		mAdmittanceDate = admittanceDate;
		mDischargeDate = dischargeDate;
	}
	
	public int getHospitalizationId()
	{
		return mHospitalizationId;
	}
	
	public int getPatientId()
	{
		return  mPatientId;
	}
	
	@Override
	public String toString()
	{
		return mAdmittanceDate + " - " + mDischargeDate;
	}
	
}
