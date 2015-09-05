package com.database.internarispital.views.menuBar;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.views.ViewManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarViewController implements Initializable
{
    @FXML
    private MenuBar menuBar;
    
    @FXML
    private MenuItem mainLobby;

    @FXML
    private MenuItem browsePatients;
    
    @FXML
    private MenuItem hospitalizePatients;
    
    @FXML
    private MenuItem browseDoctors;
    
    @FXML
    private MenuItem diagnosticationView;
    
    @FXML
    private MenuItem editsPatientsView;
    
    @FXML
    private MenuItem editsDoctorsView;
    
    @FXML
    private MenuItem editsFacilitiesView;
    
    private IMenuBarViewModel mViewModel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		mainLobby.setOnAction(event -> ViewManager.showMainView());
		
		browsePatients.setOnAction(event -> ViewManager.showBrowsePatientsView());
		hospitalizePatients.setOnAction(event -> ViewManager.showHospitalizePatientsView());
		
		browseDoctors.setOnAction(event -> ViewManager.showBrowseDoctorsView());
		diagnosticationView.setOnAction(event -> ViewManager.showConsultationView());
		
		editsPatientsView.setOnAction(event -> ViewManager.showEditPatientsView());
		editsDoctorsView.setOnAction(event -> ViewManager.showEditDoctorsView());
		editsFacilitiesView.setOnAction(event -> ViewManager.showEditFacilitiesView());
	}
	
	public void setViewModel(IMenuBarViewModel viewModel)
	{
		mViewModel = viewModel;
	}
	
	public MenuBar getMenuBar()
	{
		return menuBar;
	}
}
