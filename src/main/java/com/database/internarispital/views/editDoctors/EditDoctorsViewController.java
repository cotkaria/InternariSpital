package com.database.internarispital.views.editDoctors;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.entities.Doctor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class EditDoctorsViewController implements Initializable
{
    @FXML
    private TableView<Doctor> doctorsTable;
    @FXML
    private TextField surnameTF;
    @FXML
    private TextField nameTF;
    @FXML
    private ComboBox<String> specialtyCB;
    @FXML
    private Group addPatientGroup;
    @FXML
    private Button removeButton;
    @FXML
    private Button addButton;
    @FXML
    private Button exitButton;
    
    private IEditDoctorsViewModel mViewModel;
    private Callback<Void, Void> mExitCallback;
    
    @Override
	public void initialize(URL location, ResourceBundle resources)
	{
    	System.out.println("EditDoctorsViewController::initialize()");
    	
    	exitButton.setOnAction(event -> onExitView());
	}
    
    public void setViewModel(IEditDoctorsViewModel viewModel)
    {
    	mViewModel = viewModel;
    	assert(mViewModel != null);
    }
    
    public void setOnExit(Callback<Void, Void> exitCallback)
    {
    	mExitCallback = exitCallback;
    }
    
    private void onExitView()
    {
    	if(mExitCallback != null)
    	{
    		mExitCallback.call(null);
    	}
    }
}

