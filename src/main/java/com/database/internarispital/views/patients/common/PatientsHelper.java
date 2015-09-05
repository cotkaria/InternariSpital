package com.database.internarispital.views.patients.common;

import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.exceptions.MissingSelectionException;
import com.database.internarispital.util.InputHelper;
import com.database.internarispital.views.ViewManager;

import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableView;

public class PatientsHelper
{
	public static void showPatientsRecordOnDoubleClick(TableView<HospitalizedPatient> tableView)
	{
		tableView.setOnMouseClicked(event->
    	{
    		if (InputHelper.isDoubleClick(event))
    		{
    			try
    			{
					ViewManager.showPatientsRecord(getSelectedHospitalizedPatient(tableView));
				}
    			catch (MissingSelectionException e) 
    			{
    				// Its fine if no selection was made
				}
    		}
    	});
	}
	
	public static HospitalizedPatient getSelectedHospitalizedPatient(TableView<HospitalizedPatient> tableView) throws MissingSelectionException
	{
		HospitalizedPatient patient = null;
		SelectionModel<HospitalizedPatient> selectionModel = tableView.getSelectionModel();
		if(selectionModel.isEmpty() == false)
		{
			patient = selectionModel.getSelectedItem();
		}
		else
		{
			throw new MissingSelectionException("Please select a hospitalized patient!");
		}
		return patient;
	}
}
