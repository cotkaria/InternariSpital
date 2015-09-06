package com.database.internarispital.views.patients.hospitalize;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.entities.facilities.Bed;
import com.database.internarispital.entities.facilities.Section;
import com.database.internarispital.entities.facilities.Ward;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;
import com.database.internarispital.exceptions.MissingSelectionException;
import com.database.internarispital.util.DialogHelper;
import com.database.internarispital.views.patients.common.PatientsHelper;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HospitalizationViewController implements Initializable
{
	@FXML
	private TableView<HospitalizedPatient> hospitalizedPatientsTable;

	@FXML
    private TableColumn<HospitalizedPatient, String> firstNameColumn;

	@FXML
	private TableColumn<HospitalizedPatient, String> lastNameColumn;

	@FXML
	private TableColumn<HospitalizedPatient, String> birthDateColumn;
	
	@FXML
	private TableColumn<HospitalizedPatient, String> sectionNameColumn;
	
	@FXML
	private TableColumn<HospitalizedPatient, Integer> wardNumberColumn;
	
	@FXML
	private TableColumn<HospitalizedPatient, Integer> bedNumberColumn;
		
    @FXML
    private Button insertButton;
    
    @FXML 
    private Button dischargeButton;
    
    @FXML
    private Button createPatientButton;
    
    @FXML
    private ComboBox<Section> sectionsList;
    
    @FXML
    private ComboBox<Ward> wardsList;
    
    @FXML
    private ComboBox<Bed> bedsList;
    
    @FXML
    private ComboBox<Patient> notHospitalizedPatientsCB;
     
    private IHospitalizationViewModel mPatientsViewModel;
  
    @Override
	public void initialize(URL location, ResourceBundle resources) 
    {
    	configurePatientsTable();
    	
    	PatientsHelper.showPatientsRecordOnDoubleClick(hospitalizedPatientsTable);
    	
    	sectionsList.setOnAction(event -> onSectionSelected());
    	wardsList.setOnAction(event -> onWardSelected());
    	
    	createPatientButton.setOnAction(event -> showCreatePatientView());
    	insertButton.setOnAction(event -> onHospitalizePatient());
    	dischargeButton.setOnAction(event -> onDischargePatient());
	}
    
    private void configurePatientsTable()
	{
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, String>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, String>("lastName"));
		birthDateColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, String>("birthDate"));
		sectionNameColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, String>("sectionName"));
		wardNumberColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, Integer>("wardNumber"));
		bedNumberColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, Integer>("bedNumber"));
	}
    
    public void setViewModel(IHospitalizationViewModel patientsViewModel)
    {
    	mPatientsViewModel = patientsViewModel;
    	assert(mPatientsViewModel != null);
    }
    private void showCreatePatientView()
    {
    	mPatientsViewModel.createPatient();
    }
    
	public void setHospitalizedPatients(ObservableList<HospitalizedPatient> hospitalizedPatients)
	{
		hospitalizedPatientsTable.setItems(hospitalizedPatients);
	}
	public void addHospitalizedPatient(HospitalizedPatient hospitalizedPatient)
	{
		hospitalizedPatientsTable.getItems().add(hospitalizedPatient);
	}
	public void removeHospitalizedPatient(HospitalizedPatient hospitalizePatient)
	{
		hospitalizedPatientsTable.getItems().remove(hospitalizePatient);
	}
	
	private void onHospitalizePatient()
	{
		try
		{
			Patient patient = getSelectedPatient();
			Bed bed = getSelectedBed();
			mPatientsViewModel.hospitalizePatient(patient, bed);
		}
		catch(MissingSelectionException exception)
		{
			DialogHelper.showErrorPopup(exception.getMessage());
		}
	}
	
	private Patient getSelectedPatient() throws MissingSelectionException
	{
		Patient patient = null;
		SelectionModel<Patient> selectionModel = notHospitalizedPatientsCB.getSelectionModel();
		if(selectionModel.isEmpty() == false)
		{
			patient = selectionModel.getSelectedItem();
		}
		else
		{
			throw new MissingSelectionException("Please select a patient to hospitalize!");
		}
		return patient;
	}
	
	private Bed getSelectedBed() throws MissingSelectionException
	{
		// Ensure that a section and a ward are selected
		getSelectedSection();
		getSelectedWard();
		
		Bed bed = null;
		SingleSelectionModel<Bed> bedSelectionModel = bedsList.getSelectionModel();
		if (bedSelectionModel.isEmpty())
		{
			DialogHelper.showErrorPopup("Select a bed to hospitalize the patient in.");
		}
		else 
		{
			bed = bedSelectionModel.getSelectedItem();
		}
		return bed;
	}
	
	private Section getSelectedSection() throws MissingSelectionException
	{
		Section section = null;
		SingleSelectionModel<Section> sectionSelectionModel = sectionsList.getSelectionModel();
		if(sectionSelectionModel.isEmpty())
		{
			DialogHelper.showErrorPopup("Please select a section!");
		}
		else
		{
			section = sectionSelectionModel.getSelectedItem();
		}
		return section;
	}
	
	private Ward getSelectedWard() throws MissingSelectionException
	{
		Ward ward = null;
		SingleSelectionModel<Ward> sectionSelectionModel = wardsList.getSelectionModel();
		if(sectionSelectionModel.isEmpty())
		{
			DialogHelper.showErrorPopup("Please select a ward!");
		}
		else
		{
			ward = sectionSelectionModel.getSelectedItem();
		}
		return ward;
	}
	
	public void onDischargePatient()
	{
		SelectionModel<HospitalizedPatient> selectionModel = hospitalizedPatientsTable.getSelectionModel();
		if(!selectionModel.isEmpty())
		{
			mPatientsViewModel.dischargePatient(selectionModel.getSelectedItem());
		}
	}
	
	public void setNotHospitalizedPatients(ObservableList<Patient> patients)
	{
		notHospitalizedPatientsCB.setItems(patients);
	}
	public void addNotHospitalizedPatient(Patient patient)
	{
		notHospitalizedPatientsCB.getItems().add(patient);
	}
	public void removeNotHospitalizedPatient(Patient patient)
	{
		notHospitalizedPatientsCB.getItems().remove(patient);
	}
	public void addPatientAndSelect(Patient patient)
	{
		addNotHospitalizedPatient(patient);
		notHospitalizedPatientsCB.getSelectionModel().select(patient);
	}
	
	public void setSections(ObservableList<Section> sections)
	{
		PatientsHelper.setAndSelectFirst(sectionsList, sections);
		if(sections.isEmpty() == false)
		{
			mPatientsViewModel.selectSection(sections.get(0));
		}
	}
	public void setWards(ObservableList<Ward> wards)
	{
		PatientsHelper.setAndSelectFirst(wardsList, wards);
		if(wards.isEmpty() == false)
		{
			mPatientsViewModel.selectWard(wards.get(0));
		}
	}
	public void setBeds(ObservableList<Bed> beds)
	{
		PatientsHelper.setAndSelectFirst(bedsList, beds);
	}
	
	private void onSectionSelected()
	{
		mPatientsViewModel.selectSection(sectionsList.getSelectionModel().getSelectedItem());
	}
	
	public int getSelectedWardId()
	{
		SelectionModel<Ward> selectionModel = wardsList.getSelectionModel();
		if(!selectionModel.isEmpty())
		{
			return selectionModel.getSelectedItem().wardIdProperty().getValue();
		}
		return -1;
	}
	
	private void onWardSelected()
	{
		SingleSelectionModel<Ward> selectionModel = wardsList.getSelectionModel();
		if(!selectionModel.isEmpty())
		{
			mPatientsViewModel.selectWard(selectionModel.getSelectedItem());
		}
		else
		{
			bedsList.getItems().clear();
		}   
	}
}
