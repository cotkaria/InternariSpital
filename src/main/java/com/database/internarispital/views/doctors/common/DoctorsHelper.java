package com.database.internarispital.views.doctors.common;

import com.database.internarispital.entities.Consultation;
import com.database.internarispital.entities.Diagnostic;
import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.exceptions.MissingSelectionException;
import com.database.internarispital.views.ViewManager;
import com.database.internarispital.util.InputHelper;

import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableView;

public class DoctorsHelper
{
	public static void showPatientsRecordOnDoubleClick(TableView<Consultation> tableView)
	{
		tableView.setOnMouseClicked(event->
    	{
    		if (InputHelper.isDoubleClick(event))
    		{
    			try
    			{
    				ViewManager.showPatientsRecord(getSelectedPatient(tableView.getSelectionModel()));
				}
    			catch (MissingSelectionException e) 
    			{
    				// Its fine if no selection was made
				}
    		}
    	});
	}
	
	public static void showDoctorHistoryOnDoubleClick(TableView<Doctor> tableView)
	{
		tableView.setOnMouseClicked(event->
    	{
    		if (InputHelper.isDoubleClick(event))
    		{
    			try
    			{
    				ViewManager.showDoctorHistory(getSelectedDoctor(tableView.getSelectionModel()));
				}
    			catch (MissingSelectionException e) 
    			{
    				// Its fine if no selection was made
				}
    		}
    	});
	}
	
	public static Doctor getSelectedDoctor(SelectionModel<Doctor> selectionModel) throws MissingSelectionException
    {
    	Doctor doctor = null;
    	if(!selectionModel.isEmpty())
    	{
    		doctor = selectionModel.getSelectedItem();
    	}
    	else 
    	{
    		throw new MissingSelectionException("Please select a doctor from the list");
    	}
    	return doctor;
    }
	
	public static HospitalizedPatient getSelectedPatient(SelectionModel<Consultation> selectionModel) throws MissingSelectionException
    {
    	HospitalizedPatient patient = null;
    	if(!selectionModel.isEmpty())
    	{	//focus on the table can be lost so we should check if there is really a patient selected 
    		Consultation consultation = selectionModel.getSelectedItem();
    		if(consultation != null)
    		{
    			patient = consultation.getPatient();
    		}
    	}    	
    	if(patient == null)
    	{
    		throw new MissingSelectionException("Please select a patient from the table");
    	}
		return patient;  
    }
	public static Diagnostic getSelectedDiagnostic(SelectionModel<Diagnostic> selectionModel) throws MissingSelectionException
    {
    	Diagnostic diagnostic = null;
    	if(selectionModel.isEmpty() == false)
    	{
    		diagnostic = selectionModel.getSelectedItem();
    	}
    	else
    	{
    		throw new MissingSelectionException("Please select a diagnostic from the list");
    	}
    	return diagnostic;
    }
}
