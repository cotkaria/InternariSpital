package com.database.internarispital.views.doctors.edit;

import java.net.URL;
import java.util.ResourceBundle;




import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.doctors.DoctorData;
import com.database.internarispital.exceptions.MissingSelectionException;
import com.database.internarispital.util.DialogHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private Button addDoctorBT;
    @FXML
    private DatePicker doctorBirthDate;
    @FXML
    private ComboBox<String> specialtyCB;
    @FXML
    private ComboBox<String> gradeCB;
    
    private IEditDoctorsViewModel mViewModel;
    
    @Override
	public void initialize(URL location, ResourceBundle resources)
	{
    	addDoctorBT.setOnAction(event -> onAddDoctor());
    	removeButton.setOnAction(event -> onRemoveDoctor());
    	specialtyCB.setItems(FXCollections.observableArrayList("A", "B", "C"));
    	gradeCB.setItems(FXCollections.observableArrayList("Dr", "As", "Rez"));
    	
    	configureDoctorsTable();
	}
    
    private void configureDoctorsTable()
    {
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("doctorName"));
    	surnameColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("doctorSurname"));
    	gradeColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("grade"));
    	specialityColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("speciality"));
    }
    
    public void setViewModel(IEditDoctorsViewModel viewModel)
    {
    	mViewModel = viewModel;
    	assert(mViewModel != null);
    }
    
    public void onAddDoctor()
    {   
    	try
    	{
    	   	String name = getInputText(doctorNameTF, "Please provide a name for the doctor");
    	   	String surname= getInputText(doctorSurnameTF, "Please provide a surname for the doctor");
    	   	String speciality = getComboBoxSelection(specialtyCB, "Please select a medical speciality");
    	   	String grade = getComboBoxSelection(gradeCB, "Please select a medical grade");
    	   	DoctorData data = new DoctorData(name, surname, grade, speciality);
    	   	mViewModel.addDoctor(data);
    	}
    	catch(MissingSelectionException e)
    	{
    		DialogHelper.showErrorPopup(e.getMessage());
    	}
    }
    
    public void onRemoveDoctor()
    {
    	
    	TableViewSelectionModel<Doctor> model = doctorsTable.getSelectionModel();
    	if(model.isEmpty() == false)
    	{
    		Doctor selection = model.getSelectedItem();
    		doctorsTable.getItems().remove(selection);
    		mViewModel.removeDoctor(selection);
    	}
    }
    
    public void addDoctor(Doctor doctor)
    {
    	doctorsTable.getItems().add(doctor);
    }
    
    public void setDoctors(ObservableList<Doctor> doctors)
    {    	
    	doctorsTable.setItems(doctors);
    }
        
    private String getInputText(TextField tf, String errorMessage) throws MissingSelectionException
    {
    	String doctorAttr = tf.getText();
    	if(doctorAttr == null || doctorAttr.isEmpty())
	    {
			throw new MissingSelectionException(errorMessage);
	    }
    	return doctorAttr;
    }
    
    private String getComboBoxSelection(ComboBox<String> cb, String errorMessage) throws MissingSelectionException
    {
    	String selection = "";
    	SingleSelectionModel<String> model = cb.getSelectionModel();
    	if(model.isEmpty() == false)
    	{
    		selection = model.getSelectedItem();
    	}
    	if(selection == null || selection.isEmpty())
	    {
			throw new MissingSelectionException(errorMessage);
	    }
    	return selection;
    }
}

