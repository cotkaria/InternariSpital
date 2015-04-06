package com.database.internarispital.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Section 
{
	private IntegerProperty mSectionId;
	private StringProperty mSectionName;
	
	public Section (int sectionId, String sectionName)
	{
		mSectionId = new SimpleIntegerProperty(sectionId);
		mSectionName = new SimpleStringProperty(sectionName);
	}
	
	public IntegerProperty sectionIdProperty()
	{
		return mSectionId;
	}
	
	public StringProperty sectionNameProperty()
	{
		return mSectionName;
	}
	@Override
	public String toString()
	{
		return mSectionName.getValue(); 
	}
}
