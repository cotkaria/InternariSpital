package com.database.internarispital.views.common;

public class PeriodOccupancy<T>
{
	private String mPeriodName;
	private T mOccupancy;
	
	public PeriodOccupancy(String name, T occupancy)
	{
		mPeriodName = name;
		mOccupancy = occupancy;
	}
	
	public String getPeriodName()
	{
		return mPeriodName;
	}
	
	public T getOccupancy()
	{
		return mOccupancy;
	}
}
