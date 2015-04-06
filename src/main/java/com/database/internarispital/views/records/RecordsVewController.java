package com.database.internarispital.views.records;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.entities.HospitalizationPeriod;
import com.database.internarispital.views.doctors.IDoctorsViewModel;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class RecordsVewController implements Initializable
{

    @FXML
    private Label patientNameRecordLabel;

    @FXML
    private TableView<?> recordsTable;

    @FXML
    private ComboBox<HospitalizationPeriod> hospitalizationPeriods;
    
    private IRecordsViewModel mRecordsViewModel;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
	}

	public void setViewModel(IRecordsViewModel recordsViewModel)
	{
		mRecordsViewModel = recordsViewModel;
	}
	
	public void setHospitalizationPeriods(ObservableList<HospitalizationPeriod> hospitalizations)
	{
		hospitalizationPeriods.setItems(hospitalizations);
	}
	
	
	
}
