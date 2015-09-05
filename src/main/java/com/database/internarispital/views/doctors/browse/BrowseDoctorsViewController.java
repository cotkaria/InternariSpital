package com.database.internarispital.views.doctors.browse;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.views.doctors.common.DoctorsHelper;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BrowseDoctorsViewController implements Initializable
{
	@FXML
	private TableView<Doctor> tableView;
	
	@FXML
	private TableColumn<Doctor, String> nameColumn;
	
	@FXML
	private TableColumn<Doctor, String> gradeColumn;
	
	@FXML
	private TableColumn<Doctor, String> specialityColumn;
	
	@FXML
	private TableColumn<Doctor, String> surnameColumn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		configureDoctorsTable();
		DoctorsHelper.showDoctorHistoryOnDoubleClick(tableView);
	}
    
    private void configureDoctorsTable()
    {
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("doctorName"));
    	surnameColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("doctorSurname"));
    	gradeColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("grade"));
    	specialityColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("speciality"));
	}
	
	public void setDoctors(ObservableList<Doctor> doctors)
	{
		tableView.setItems(doctors);
	}
}
