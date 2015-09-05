package com.database.internarispital.views.doctors.edit;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.doctors.DoctorData;
import com.database.internarispital.exceptions.InvalidInputException;
import com.database.internarispital.exceptions.MissingSelectionException;
import com.database.internarispital.util.DialogHelper;
import com.database.internarispital.util.InputHelper;
import com.database.internarispital.views.doctors.common.DoctorsHelper;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditDoctorsViewController implements Initializable
{
	@FXML
    private TableView<Doctor> doctorsTable;
	@FXML
	private TableColumn<Doctor, String> nameColumn;
	@FXML               
	private TableColumn<Doctor, String> surnameColumn;
	@FXML               
	private TableColumn<Doctor, String> gradeColumn;
	@FXML               
    private TableColumn<Doctor, String> specialityColumn;
    @FXML
    private TextField doctorSurnameTF;
    @FXML
    private TextField doctorNameTF;
    @FXML
    private Group addPatientGroup;
    @FXML
    private Button removeButton;
    @FXML
    private Button addDoctorButton;
    @FXML
    private Button updateDoctorButton;
    @FXML
    private DatePicker doctorBirthDate;
    @FXML
    private ComboBox<String> specialtyCB;
    @FXML
    private ComboBox<String> gradeCB;
    
    private IEditDoctorsViewModel mViewModel;
    
    public void setViewModel(IEditDoctorsViewModel viewModel)
    {
    	mViewModel = viewModel;
    	assert(mViewModel != null);
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources)
	{
    	addDoctorButton.setOnAction(event -> mViewModel.showCreateDoctorView());
    	removeButton.setOnAction(event -> onRemoveDoctor());
    	updateDoctorButton.setOnAction(event -> onUpdateDoctor());
    	
    	specialtyCB.setItems(DoctorsHelper.DOCTOR_SPECIALITIES);
    	gradeCB.setItems(DoctorsHelper.DOCTOR_GRADES);
    	
    	configureDoctorsTable();
	}
    
    private void configureDoctorsTable()
    {
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("name"));
    	surnameColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("surname"));
    	gradeColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("grade"));
    	specialityColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("speciality"));
    	

    	doctorsTable.setOnMouseClicked(event ->
    	{
    		if(InputHelper.isLeftClick(event))
    		{
    			try
				{
					onDoctorSelectedForEdit(DoctorsHelper.getSelectedDoctor(doctorsTable.getSelectionModel()));
				}
				catch (Exception e)
				{
					// nothing to do
				}
    		}
    	});
    }
    
    public void addDoctor(Doctor doctor)
    {
    	doctorsTable.getItems().add(doctor);
    }
    
    public void setDoctors(ObservableList<Doctor> doctors)
    {
    	doctorsTable.setItems(doctors);
    	selectDoctorIndexInTable(0);
    }
    
    private void selectDoctorIndexInTable(int index)
    {
    	if(doctorsTable.itemsProperty().getValue().isEmpty() == false)
    	{
        	TableViewSelectionModel<Doctor> sm = doctorsTable.getSelectionModel();
    		sm.select(0);
    		onDoctorSelectedForEdit(sm.getSelectedItem());
    	}
    }
    
    private void onDoctorSelectedForEdit(Doctor doctor)
    {
    	doctorNameTF.setText(doctor.nameProperty().getValue());
    	doctorSurnameTF.setText(doctor.surnameProperty().getValue());
    	specialtyCB.getSelectionModel().select(doctor.specialityProperty().getValue());
    	gradeCB.getSelectionModel().select(doctor.gradeProperty().getValue());
    }
    
    private void onRemoveDoctor()
    {
		try
		{
			Doctor selection = DoctorsHelper.getSelectedDoctor(doctorsTable.getSelectionModel());
	    	doctorsTable.getItems().remove(selection);
			mViewModel.removeDoctor(selection);
		}
		catch (MissingSelectionException e)
		{
			DialogHelper.showErrorPopup(e.getMessage());
		}
    }
    
    private void onUpdateDoctor()
    {
    	try
		{
			Doctor doctor = DoctorsHelper.getSelectedDoctor(doctorsTable.getSelectionModel());
			updateDoctor(doctor);
		}
		catch (MissingSelectionException|InvalidInputException e)
		{
			DialogHelper.showErrorPopup(e.getMessage());
		}
    }
    
    private void updateDoctor(Doctor doctor) throws InvalidInputException
    {
    	String newName = getNewDoctorName(doctor);
    	String newSurname = getDoctorSurname(doctor);
    	String newSpeciality = getDoctorSpeciality(doctor);
    	String newGrade = getDoctorGrade(doctor);
    	
    	DoctorData newDoctorData = new DoctorData(newName, newSurname, newGrade, newSpeciality);
    	if(newDoctorData.equals(doctor) == false)
    	{
    		mViewModel.updateDoctor(doctor, newDoctorData);
    	}
    }
    
    private String getNewDoctorName(Doctor doctor) throws InvalidInputException
    {
    	String oldName = doctor.nameProperty().getValue();
    	String newName = doctorNameTF.getText();
    	validOrThrow(oldName, newName, "The doctor name is invalid");
		return newName;
    }
    
    private String getDoctorSurname(Doctor doctor) throws InvalidInputException
    {
    	String oldName = doctor.surnameProperty().getValue();
    	String newName = doctorSurnameTF.getText();
    	validOrThrow(oldName, newName, "The doctor surname is invalid");
    	return newName;
    }
    
    private String getDoctorSpeciality(Doctor doctor) throws InvalidInputException
    {
    	String oldValue = doctor.specialityProperty().getValue();
    	String newValue = specialtyCB.getSelectionModel().getSelectedItem();
    	validOrThrow(oldValue, newValue, "The doctor speciality is invalid");
    	return newValue;
    }
    
    private String getDoctorGrade(Doctor doctor) throws InvalidInputException
    {
    	String oldValue = doctor.gradeProperty().getValue();
    	String newValue = gradeCB.getSelectionModel().getSelectedItem();
    	validOrThrow(oldValue, newValue, "The doctor grade is invalid");
    	return newValue;
    }
    
    private void validOrThrow(String oldName, String newName, String errorMessage) throws InvalidInputException
    {
    	if(isStringValid(newName) == false)
    	{
    		throw new InvalidInputException(errorMessage);
    	}
    }
	
	private boolean isStringValid(String value)
	{
		return ((value != null) && (value != ""));
	}
}

