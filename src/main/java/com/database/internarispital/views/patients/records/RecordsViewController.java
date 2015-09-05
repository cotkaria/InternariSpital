package com.database.internarispital.views.patients.records;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.entities.Consultation;
import com.database.internarispital.entities.Diagnostic;
import com.database.internarispital.entities.HospitalizationPeriod;
import com.database.internarispital.entities.doctors.Doctor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class RecordsViewController implements Initializable
{
    @FXML
    private Label patientNameRecordLabel;

    @FXML
    private TableView<Consultation> recordsTable;

    @FXML
    private TableColumn<Consultation, String> diagnosticColumn;
    
    @FXML
    private TableColumn<Consultation, String> consultationDate;
    
    @FXML
    private TableColumn<Consultation, String> doctorNameColumn;
    
    @FXML
    private ComboBox<HospitalizationPeriod> hospitalizationPeriods;
    
    private IRecordsViewModel mRecordsViewModel;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		configurePatientsTable();
		hospitalizationPeriods.setOnAction(event -> onHospitalizationPeriodSelected());
	}

	private void configurePatientsTable()
    {    	
    	diagnosticColumn.setCellValueFactory(new Callback<CellDataFeatures<Consultation, String>, ObservableValue<String>>()
		{
			public ObservableValue<String> call (CellDataFeatures<Consultation, String> consultationCellData)
			{
				Diagnostic diagnostic = consultationCellData.getValue().getDiagnostic();
				
				return new SimpleStringProperty(diagnostic.diagNameProperty().getValue());
			}
		});
    	consultationDate.setCellValueFactory(new PropertyValueFactory<Consultation, String>("consultationDate"));
    	doctorNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Consultation, String>, ObservableValue<String>>()
		{
			public ObservableValue<String> call (CellDataFeatures<Consultation, String> consultationCellData)
			{
				Doctor doctor = consultationCellData.getValue().getDoctor();
				return new SimpleStringProperty(doctor.getName());
			}
		});
    }
	
	public void setViewModel(IRecordsViewModel recordsViewModel)
	{
		mRecordsViewModel = recordsViewModel;
	}
	
	public void setHospitalizationPeriods(ObservableList<HospitalizationPeriod> hospitalizations)
	{
		hospitalizationPeriods.setItems(hospitalizations);
	}
	
	public void setConsultations(ObservableList<Consultation> consultations)
	{
		recordsTable.setItems(consultations);
	}
	
	private void onHospitalizationPeriodSelected()
	{
		HospitalizationPeriod hospitalizationPeriod = hospitalizationPeriods.getSelectionModel().getSelectedItem();
		if(hospitalizationPeriod != null)
		{
			mRecordsViewModel.requestConsultations(hospitalizationPeriod);
		}
	}
}
