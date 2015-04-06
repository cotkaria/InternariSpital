package com.database.internarispital.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Diagnostic {
	
	private IntegerProperty mDiagId;
	private StringProperty mDiagName;
	private StringProperty mDiagCategoryName;
	private StringProperty mDiagDscription;
	
	public Diagnostic()
	{
		this(-1, "", "", "");
	}
	public Diagnostic(int diagId, String diagName, String diagCategoryNameProperty, String diagDescription)
	{
		mDiagId = new SimpleIntegerProperty(diagId);
		mDiagName =new SimpleStringProperty(diagName);
		mDiagCategoryName = new SimpleStringProperty(diagCategoryNameProperty);		
		mDiagDscription = new SimpleStringProperty(diagDescription);
		
	}
	
	public IntegerProperty diagIdProperty()
	{
		return mDiagId;
	}
	public StringProperty diagNameProperty()
	{
		return mDiagName;
	}
	public StringProperty diagCategoryNameProperty()
	{
		return mDiagCategoryName;
	}
	public StringProperty diagDescriptionProperty()
	{
		return mDiagDscription;
	}

	@Override
	public String toString()
	{
		return "[" + mDiagId.getValue().toString() + "], " + mDiagName.getValue();
	}
}
