package com.database.internarispital.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ward {
	
	private IntegerProperty mWardId;
	private StringProperty mSection;
	private IntegerProperty mWardNumber;
	
	public Ward(int wardId, String section, int wardNumber)
	{
		mWardId = new SimpleIntegerProperty(wardId);
		mSection = new SimpleStringProperty(section);
		mWardNumber = new SimpleIntegerProperty(wardNumber);
	}
	
	public IntegerProperty wardIdProperty()
	{
		return mWardId;
	}
	public StringProperty sectionProperty()
	{
		return mSection;
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
