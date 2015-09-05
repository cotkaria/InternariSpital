package com.database.internarispital.views.patients.common;

import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.exceptions.MissingSelectionException;
import com.database.internarispital.util.InputHelper;
import com.database.internarispital.views.ViewManager;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
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

	public static <T> T getSelectedItem(ComboBox<T> cb) throws MissingSelectionException
	{
		T selectedItem = null;
		SingleSelectionModel<T> selectionModel = cb.getSelectionModel();
		if(selectionModel.isEmpty() == false)
		{
			selectedItem = selectionModel.getSelectedItem();
		}
		else
		{
			throw new MissingSelectionException("No selection is made in " + cb.toString());
		}
		return selectedItem;
	}

	public static <T> void setAndSelectFirst(ComboBox<T> cb, ObservableList<T> items)
    {
    	PatientsHelper.clearAndSetItems(cb, items);
    	PatientsHelper.selectFirstItem(cb);
    }
	
	public static <T> void clearAndSetItems(ComboBox<T> cb, ObservableList<T> items)
	{
		cb.getItems().clear();
		cb.setItems(items);
	}
	
	public static <T> void selectFirstItem(ComboBox<T> cb)
	{
		ObservableList<T> items = cb.getItems();
		if(items.isEmpty() == false)
		{
			cb.getSelectionModel().select(0);
		}
	}
}
