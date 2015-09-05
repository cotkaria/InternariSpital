package com.database.internarispital.views.doctors.diagnosticate;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.database.internarispital.entities.Consultation;
import com.database.internarispital.entities.DiagCategory;
import com.database.internarispital.entities.Diagnostic;
import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;
import com.database.internarispital.exceptions.MissingSelectionException;
import com.database.internarispital.util.DialogHelper;
import com.database.internarispital.views.ViewManager;
import com.database.internarispital.views.doctors.common.DoctorsHelper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class DoctorsViewController implements Initializable
{
    @FXML
    private TableView<Consultation> consultationTable;

    @FXML
    private ComboBox<Doctor> doctorsList;
    
    @FXML
    private ComboBox<DiagCategory> diagCategoryList;
    
    @FXML
    private ComboBox<Diagnostic> diagnosticsList;
    
	@FXML
    private DatePicker diagDatePicker;
	
	@FXML 
	private Button diagnosticateButton;
	
	@FXML 
	private Button doctorHistoryButton;
	
	@FXML
	private TableColumn<Consultation, String> patientNameColumn;
	
	@FXML
	private TableColumn<Consultation, String> accomodationColumn;
	
	@FXML
	private TableColumn<Consultation, String> doctorNameColumn;
	
	@FXML
	private TableColumn<Consultation, String> diagnosticColumn;
	
	@FXML
	private TableColumn<Consultation, String> consultationDate;
	
	private IDoctorsViewModel mDoctorsViewModel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
    	configurePatientsTable();
    	
    	DoctorsHelper.showPatientsRecordOnDoubleClick(consultationTable);
    	
		diagCategoryList.setOnAction(event -> onDiagCategorySelected());
		diagnosticateButton.setOnAction(event -> onDiagnosticate());
		doctorHistoryButton.setOnAction(event -> showDoctorHistory());
		diagDatePicker.setValue(LocalDate.now());
		diagDatePicker.setOnAction(event->
		{
			LocalDate selectedDate = diagDatePicker.getValue();
			LocalDate currenDate = LocalDate.now();
			if(selectedDate.equals(currenDate) == false)
			{
				diagDatePicker.setValue(currenDate);
			}
		});

	}
	public void setViewModel(IDoctorsViewModel doctorsViewModel)
    {
    	mDoctorsViewModel = doctorsViewModel;
    	assert(mDoctorsViewModel != null);
    }
    private void configurePatientsTable()
    {
    	patientNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Consultation, String>, ObservableValue<String>>()
		{
    		public ObservableValue<String> call (CellDataFeatures<Consultation, String> consultationCellData)
    		{
    			Patient patient = consultationCellData.getValue().getPatient();
    			return new SimpleStringProperty(patient.getName());
    		}
		});
    	accomodationColumn.setCellValueFactory(new Callback<CellDataFeatures<Consultation, String>, ObservableValue<String>>()
		{
			public ObservableValue<String> call (CellDataFeatures<Consultation, String> consultationCellData)
			{
				HospitalizedPatient patient = consultationCellData.getValue().getPatient();
				String section = patient.sectionNameProperty().getValue();
				Integer ward = patient.wardNumberProperty().getValue();
				return new SimpleStringProperty(section + ", " + ward);
			}
		});
      	doctorNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Consultation, String>, ObservableValue<String>>()
		{
			public ObservableValue<String> call (CellDataFeatures<Consultation, String> consultationCellData)
			{
				Doctor doctor = consultationCellData.getValue().getDoctor();
				return new SimpleStringProperty(doctor.getName());
			}
		});
      	diagnosticColumn.setCellValueFactory(new Callback<CellDataFeatures<Consultation, String>, ObservableValue<String>>()
		{
			public ObservableValue<String> call (CellDataFeatures<Consultation, String> consultationCellData)
			{
				Diagnostic diagnostic = consultationCellData.getValue().getDiagnostic();
				
				return new SimpleStringProperty(diagnostic.diagNameProperty().getValue());
			}
		});
    	
      	consultationDate.setCellValueFactory(new PropertyValueFactory<Consultation, String>("consultationDate"));
    }
    
    private void showDoctorHistory()
    {
		try
		{
			ViewManager.showDoctorHistory(DoctorsHelper.getSelectedDoctor(doctorsList.getSelectionModel()));
		}
		catch (MissingSelectionException e) 
		{
			DialogHelper.showErrorPopup(e.getMessage());
		}
    }
    
	public void setConsultations(ObservableList<Consultation> consultations)
	{
		consultationTable.setItems(consultations);
	}
    public void setDoctors(ObservableList<Doctor> doctors)
    {
	    doctorsList.setItems(doctors);
    }
    
    public void setDiagCategories(ObservableList<DiagCategory> diagCategories)
    {
    	diagCategoryList.setItems(diagCategories);
    }
    
    private void onDiagCategorySelected()
    {
 	   SingleSelectionModel<DiagCategory> selectionModel = diagCategoryList.getSelectionModel();
 	   if(!selectionModel.isEmpty())
 	   {
 		   mDoctorsViewModel.selectDiagCategory(selectionModel.getSelectedItem());
 	   }
 	   else
 	   {
 		   diagnosticsList.getItems().clear();
 	   }
    }
    
    private void onDiagnosticate()
    {
    	try
    	{
    		HospitalizedPatient patient = DoctorsHelper.getSelectedPatient(consultationTable.getSelectionModel());
    		Doctor doctor = DoctorsHelper.getSelectedDoctor(doctorsList.getSelectionModel());
    		Diagnostic diagnostic = DoctorsHelper.getSelectedDiagnostic(diagnosticsList.getSelectionModel());
    		String diagnosticDate = getSelectedDiagnosticDate();
    		mDoctorsViewModel.createConsultation(patient, doctor, diagnostic, diagnosticDate);
    	}
    	catch(MissingSelectionException e)
    	{
    		DialogHelper.showErrorPopup(e.getMessage());
    	}
    }
    
    private String getSelectedDiagnosticDate() throws MissingSelectionException
    {
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    	String diagDate = LocalDateTime.now().format(dateTimeFormatter);
//    	if(diagDate.isEmpty())
//    	{
//    		throw new MissingSelectionException("Please select a date from the calendar");
//    	}
    	return diagDate;
    }
    
    public void setDiagnostics(ObservableList<Diagnostic> diagnostics)
    {
    	diagnosticsList.setItems(diagnostics);
    }
}
