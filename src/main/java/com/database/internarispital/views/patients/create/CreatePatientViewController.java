package com.database.internarispital.views.patients.create;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.database.internarispital.entities.patients.PatientData;
import com.database.internarispital.exceptions.MissingInputException;
import com.database.internarispital.util.DialogHelper;
import com.database.internarispital.views.common.CommonHelper;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CreatePatientViewController implements Initializable
{
	@FXML
	private TextField firstNameTF;
    
	@FXML
    private TextField lastNameTF;
    
	@FXML
    private DatePicker birthDatePicker;
	
	@FXML
    private Button createPatientButton;
	
	private ICreatePatientViewModel mViewModel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
    {
    	birthDatePicker.setValue(LocalDate.now());
    	createPatientButton.setOnAction(event -> createPatient());
    }
	
	public void setViewModel(ICreatePatientViewModel viewModel)
	{
		mViewModel = viewModel;
	}
	
	private void createPatient()
	{
		try
		{
			String firstName = getFirstName();
			String lastName = getLastName();
			String birthDate = getBirthDate();
			mViewModel.createPatient(new PatientData(firstName, lastName, birthDate));
		}
		catch(MissingInputException exception)
		{
			DialogHelper.showErrorPopup(exception.getMessage());
		}
	}
	
	private String getFirstName() throws MissingInputException
	{
		return CommonHelper.getTextOrException(firstNameTF.getText(), "Please provide a name for the patient.");
	}
	
	private String getLastName() throws MissingInputException
	{
		return CommonHelper.getTextOrException(lastNameTF.getText(), "Please provide a surname for the patient.");
	}
	
	private String getBirthDate() throws MissingInputException
	{
		return CommonHelper.getTextOrException(birthDatePicker.getValue().toString(), "Please provide a birth date for the patient.");
	}
}
