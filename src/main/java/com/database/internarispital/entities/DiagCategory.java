package com.database.internarispital.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DiagCategory 
{

	private int mDiagCategoryId;
	private StringProperty mDiagCategoryName;
	private StringProperty mDiagCategoryDescription;
	public DiagCategory(int diagCategoryId, String diagCategoryName, String diagCategoryDescription)
	{
		mDiagCategoryId = diagCategoryId;
		mDiagCategoryName = new SimpleStringProperty(diagCategoryName);
		mDiagCategoryDescription = new SimpleStringProperty(diagCategoryDescription);
	}

	public int getDiagCategoryId()
	{
		return mDiagCategoryId;
	}
	
	public StringProperty diagCategoryNameProperty()
	{
		return mDiagCategoryName;
	}
	
	public StringProperty diagCategoryDescription()
	{
		return mDiagCategoryDescription;
	}
	
	@Override
	public String toString()
	{
		return mDiagCategoryName.getValue() + " [" + mDiagCategoryDescription.getValue() + "]";
	}
	
}
