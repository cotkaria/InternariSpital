package com.database.internarispital.views.doctorHistory;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.entities.Consultation;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DoctorHistoryViewController  implements Initializable
{
	@FXML
    private TableView<Consultation> tableView;
	
    @FXML
    private TableColumn<Consultation, String> patientNameColumn;
	
    @FXML
    private TableColumn<Consultation, String> consultationDate;

    @FXML
    private TableColumn<Consultation, String> diagnosticColumn;
	
	private IDoctorHistoryViewModel mViewModel;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
	}
	
	public void setViewModel(IDoctorHistoryViewModel viewModel)
	{
		mViewModel = viewModel;
	}
	
	public void setDoctorHistory(ObservableList<Consultation> consultations)
	{
		tableView.setItems(consultations);
	}
}
