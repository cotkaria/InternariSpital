package com.database.internarispital.views.patients.stats;

public class PeriodOccupancy
{
	private String mPeriodName;
	private int mPatientsCount;
	
	public PeriodOccupancy(String name, int patientsCount)
	{
		mPeriodName = name;
		mPatientsCount = patientsCount;
	}
	
	public String getPeriodName()
	{
		return mPeriodName;
	}
	
	public int getPatientsCount()
	{
		return mPatientsCount;
	}
}
