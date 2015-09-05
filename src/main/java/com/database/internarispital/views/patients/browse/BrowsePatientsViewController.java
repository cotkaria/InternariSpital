package com.database.internarispital.views.patients.browse;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.views.patients.common.PatientsHelper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class BrowsePatientsViewController implements Initializable
{
	@FXML
	private TableView<HospitalizedPatient> tableView;

	@FXML
    private TableColumn<HospitalizedPatient, String> firstNameColumn;

	@FXML
	private TableColumn<HospitalizedPatient, String> lastNameColumn;

	@FXML
	private TableColumn<HospitalizedPatient, String> birthDateColumn;
	
	@FXML
	private TableColumn<HospitalizedPatient, String> isAdmittedColumn;
	
	@FXML
	private TableColumn<HospitalizedPatient, String> sectionNameColumn;
	
	@FXML
	private TableColumn<HospitalizedPatient, Integer> wardNumberColumn;
	
	@FXML
	private TableColumn<HospitalizedPatient, Integer> bedNumberColumn;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
    	configurePatientsTable();
    	
    	PatientsHelper.showPatientsRecordOnDoubleClick(tableView);
	}
	
	private void configurePatientsTable()
	{
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, String>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, String>("lastName"));
		birthDateColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, String>("birthDate"));
		isAdmittedColumn.setCellValueFactory(new Callback<CellDataFeatures<HospitalizedPatient, String>, ObservableValue<String>>()
		{
    		public ObservableValue<String> call (CellDataFeatures<HospitalizedPatient, String> cellData)
    		{
    			HospitalizedPatient patient = cellData.getValue();
    			String label = patient.isHospitalized() ? "true" : "false";
    			return new SimpleStringProperty(label);
    		}
		});
		sectionNameColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, String>("sectionName"));
		wardNumberColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, Integer>("wardNumber"));
		bedNumberColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, Integer>("bedNumber"));
	}
	
	public void setPatients(ObservableList<HospitalizedPatient> patients)
	{
		tableView.setItems(patients);
	}
}
