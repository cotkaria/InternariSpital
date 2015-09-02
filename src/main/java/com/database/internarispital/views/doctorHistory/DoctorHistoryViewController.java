package com.database.internarispital.views.doctorHistory;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.database.internarispital.entities.Consultation;
import com.database.internarispital.entities.Diagnostic;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;
import com.database.internarispital.util.InputHelper;
import com.database.internarispital.views.exception.MissingSelectionException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class DoctorHistoryViewController  implements Initializable
{
	@FXML
    private TableView<Consultation> consultationTable;
	
	@FXML
    private TableColumn<Consultation, String> patientNameColumn;
	
    @FXML
    private TableColumn<Consultation, String> consultationDate;

    @FXML
    private TableColumn<Consultation, String> diagnosticColumn;
    
    @FXML
    private RadioButton selectIntervalRb;

    @FXML
    private RadioButton allConsultationsRb;

    @FXML
    private ToggleGroup radioGroup;
	
    @FXML
    private Label labelTip;
    
    @FXML
    private DatePicker startPeriod;
    
    @FXML
    private DatePicker endPeriod;
    
    @FXML
    private CheckBox currentlyAdmittedCkB;
    
	private IDoctorHistoryViewModel mViewModel;
	private Callback<HospitalizedPatient, Void> mShowPatientsRecordCb;
	private boolean showAllHistory;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		configureHistoryTable();
		startPeriod.setValue(LocalDate.now());
		startPeriod.setOnAction(event -> onHistoryIntervalChanged());
		endPeriod.setValue(LocalDate.now());
		endPeriod.setOnAction(event -> onHistoryIntervalChanged());
		consultationTable.setOnMouseClicked(event->
    	{
    		if (InputHelper.isDoubleClick(event))
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
		radioGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
			{ 
				 if (radioGroup.getSelectedToggle() != null) 
				 {
					 showAllHistory = (selectIntervalRb.isSelected() == false);
					 startPeriod.setDisable(showAllHistory);
					 endPeriod.setDisable(showAllHistory);
					 onHistoryIntervalChanged();
				 }    
		    }	
		});
//		currentlyAdmittedCkB.selectedProperty().addListener(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
//	            if()    
//	        }
//	    });
		currentlyAdmittedCkB.setOnAction(event -> onHistoryIntervalChanged());
	}
	
	public void setViewModel(IDoctorHistoryViewModel viewModel)
	{
		mViewModel = viewModel;
		assert(mViewModel != null);
	}

	public void setOnShowPatientsRecord(Callback<HospitalizedPatient, Void> showPatientsRecordCb)
	{
		mShowPatientsRecordCb = showPatientsRecordCb;
	}
	
    private void showPatientsRecord(HospitalizedPatient patient)
    {
    	if(mShowPatientsRecordCb != null)
    	{
    		mShowPatientsRecordCb.call(patient);
    	}
    }
    private HospitalizedPatient getSelectedPatient() throws MissingSelectionException
    {
    	HospitalizedPatient patient = null;
    	SelectionModel<Consultation> selectionModel = consultationTable.getSelectionModel();
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
	
	private void configureHistoryTable()
    {    	
    	patientNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Consultation, String>, ObservableValue<String>>()
    			{
    				public ObservableValue<String> call (CellDataFeatures<Consultation, String> consultationCellData)
    				{
    					Patient patient = consultationCellData.getValue().getPatient();
    					return new SimpleStringProperty(patient.getName());
    				}
    			});
    	consultationDate.setCellValueFactory(new PropertyValueFactory<Consultation, String>("consultationDate"));
    	diagnosticColumn.setCellValueFactory(new Callback<CellDataFeatures<Consultation, String>, ObservableValue<String>>()
    			{
    				public ObservableValue<String> call (CellDataFeatures<Consultation, String> consultationCellData)
    				{
    					Diagnostic diagnostic = consultationCellData.getValue().getDiagnostic();
    					
    					return new SimpleStringProperty(diagnostic.diagNameProperty().getValue());
    				}
    			});
    }
	

	public void setDoctorHistory(ObservableList<Consultation> consultations)
	{
		consultationTable.setItems(consultations);
	}
	
	
	private void onHistoryIntervalChanged()
	{
		mViewModel.showDoctorHistory(startPeriod.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE), 
    								 endPeriod.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE), 
    								 currentlyAdmittedCkB.selectedProperty().getValue(),
    								 showAllHistory);
	}
}
