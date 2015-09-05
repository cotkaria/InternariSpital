package com.database.internarispital.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Ward {
	
	private IntegerProperty mWardId;
	private IntegerProperty mSectionId;
	private IntegerProperty mWardNumber;
	
	public Ward(int wardId, int sectionId, int wardNumber)
	{
		mWardId = new SimpleIntegerProperty(wardId);
		mSectionId = new SimpleIntegerProperty(sectionId);
		mWardNumber = new SimpleIntegerProperty(wardNumber);
	}
	
	public IntegerProperty wardIdProperty()
	{
		return mWardId;
	}
	public IntegerProperty sectionIdProperty()
	{
		return mSectionId;
	}
	public IntegerProperty wardNumberProperty()
	{
		return mWardNumber;
	}
	@Override
	public String toString()
	{
		return mWardNumber.getValue().toString();
	}

}
