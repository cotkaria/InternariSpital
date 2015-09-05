package com.database.internarispital.views.doctors.create;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.entities.doctors.DoctorData;
import com.database.internarispital.exceptions.MissingInputException;
import com.database.internarispital.exceptions.MissingSelectionException;
import com.database.internarispital.util.DialogHelper;
import com.database.internarispital.views.common.CommonHelper;
import com.database.internarispital.views.doctors.common.DoctorsHelper;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CreateDoctorViewController implements Initializable
{
    @FXML
    private TextField doctorSurnameTF;
    @FXML
    private TextField doctorNameTF;
    @FXML
    private ComboBox<String> specialtyCB;
    @FXML
    private ComboBox<String> gradeCB;
    @FXML
    private Button addDoctorBT;
	
	ICreateDoctorViewModel mViewModel;
	
	public void setViewModel(ICreateDoctorViewModel viewModel)
	{
		mViewModel = viewModel;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
    	specialtyCB.setItems(DoctorsHelper.DOCTOR_SPECIALITIES);
    	gradeCB.setItems(DoctorsHelper.DOCTOR_GRADES);
    	addDoctorBT.setOnAction(event -> onAddDoctor());
	}
	
	public void onAddDoctor()
    {   
    	try
    	{
    	   	String name = CommonHelper.getInputText(doctorNameTF, "Please provide a name for the doctor");
    	   	String surname= CommonHelper.getInputText(doctorSurnameTF, "Please provide a surname for the doctor");
    	   	String speciality = CommonHelper.getComboBoxSelection(specialtyCB, "Please select a medical speciality");
    	   	String grade = CommonHelper.getComboBoxSelection(gradeCB, "Please select a medical grade");
    	   	DoctorData data = new DoctorData(name, surname, grade, speciality);
    	   	mViewModel.createDoctor(data);
    	}
    	catch(MissingInputException|MissingSelectionException e)
    	{
    		DialogHelper.showErrorPopup(e.getMessage());
    	}
    }
}
