package com.database.internarispital.entities;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Bed 
{
	
	private IntegerProperty mBedId;
	private IntegerProperty mBedNumber;
	private IntegerProperty mWardId;
	private SimpleBooleanProperty mOccupancyStatus;
	
	public Bed(int bedId, int bedNumber, int wardId, boolean occupancyStatus)
	{
		mBedId = new SimpleIntegerProperty(bedId);
		mBedNumber = new SimpleIntegerProperty(bedNumber);
		mWardId = new SimpleIntegerProperty(wardId);
		mOccupancyStatus = new SimpleBooleanProperty(occupancyStatus);
	}
	
	public IntegerProperty bedIdProperty()
	{
		return mBedId;
	}
	public IntegerProperty bedNumberProperty()
	{
		return mBedNumber;
	}
	public IntegerProperty wardIdProperty()
	{
		return mWardId;
	}
	public BooleanProperty occupancyStatusBooleanProperty()
	{
		return mOccupancyStatus;
	}
	@Override
	public String toString()
	{
		return mBedNumber.getValue().toString();
	}

}