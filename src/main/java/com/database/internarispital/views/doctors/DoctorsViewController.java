package com.database.internarispital.views.doctors;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import com.database.internarispital.entities.Consultation;
import com.database.internarispital.entities.DiagCategory;
import com.database.internarispital.entities.Diagnostic;
import com.database.internarispital.entities.Doctor;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;
import com.database.internarispital.views.exception.MissingSelectionException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Window;
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
	private Button patientsViewButton;
	
	@FXML 
	private Button diagnosticateButton;
	
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
	
	private Window mWindow;
	private IDoctorsViewModel mDoctorsViewModel;
	private Callback<Void, Void> mShowPatientsViewCb;
	private Callback<Patient, Void> mShowPatientsRecordCb;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
    	configurePatientsTable();
    	consultationTable.setOnMouseClicked(event->
    	{
    		if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY))
    		{
    			try
    			{
					showPatientsRecord(getSelectedPatient());
				}
    			catch (MissingSelectionException e) 
    			{
    				// Is fine if no selection was made
				}
    		}
    	});
		patientsViewButton.setOnAction(event->onShowPatientsView());
		diagCategoryList.setOnAction(event->onDiagCategorySelected());
		diagnosticateButton.setOnAction(event->onDiagnosticate());
		
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

    public void setOnShowPatientsView(Callback<Void, Void> showPatientsViewCb)
    {
    	mShowPatientsViewCb = showPatientsViewCb;
    }
    private void onShowPatientsView()
	{
		if(mShowPatientsViewCb != null)
		{
			mShowPatientsViewCb.call(null);
		}
	}
    
	public void setOnShowPatientsRecord(Callback<Patient, Void> showPatientsRecordCb)
	{
		mShowPatientsRecordCb = showPatientsRecordCb;
	}
    private void showPatientsRecord(Patient patient)
    {
    	if(mShowPatientsRecordCb != null)
    	{
    		mShowPatientsRecordCb.call(patient);
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
    		HospitalizedPatient patient = getSelectedPatient();
    		Doctor doctor = getSelectedDoctor();
    		Diagnostic diagnostic = getSelectedDiagnostic();
    		String diagnosticDate = getSelectedDiagnosticDate();
    		mDoctorsViewModel.createConsultation(patient, doctor, diagnostic, diagnosticDate);
    	}
    	catch(MissingSelectionException e)
    	{
    		showErrorPopUp(e.getMessage());
    	}
    }
    
    private HospitalizedPatient getSelectedPatient() throws MissingSelectionException
    {
    	HospitalizedPatient patient = null;
    	SelectionModel<Consultation> selectionModel = consultationTable.getSelectionModel();
    	if(!selectionModel.isEmpty())
    	{
    		patient = selectionModel.getSelectedItem().getPatient();
    	}
    	else 
    	{
    		throw new MissingSelectionException("Please select a patient from the table");
    	}
		return patient;  
    }
    
    private Diagnostic getSelectedDiagnostic() throws MissingSelectionException
    {
    	Diagnostic diagnostic= null;
    	SelectionModel<Diagnostic> selectionModel = diagnosticsList.getSelectionModel();
    	if(!selectionModel.isEmpty())
    	{
    		diagnostic = selectionModel.getSelectedItem();
    	}
    	else 
    	{
    		throw new MissingSelectionException("Please select a diagnostic from the list");
    	}
    	return diagnostic;
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
    
    private Doctor getSelectedDoctor() throws MissingSelectionException
    {
    	Doctor doctor = null;
    	SelectionModel<Doctor> selectionModel = doctorsList.getSelectionModel();
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
    
    public void setDiagnostics(ObservableList<Diagnostic> diagnostics)
    {
    	diagnosticsList.setItems(diagnostics);
    }
    
//    public void resetInsertedValues()
//    {
//    	//TODO
//    }
    private void showErrorPopUp(String message)
	{
		Dialogs.create().owner(mWindow).title("Error").message(message).showError();
	}
}
