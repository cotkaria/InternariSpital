package com.database.internarispital.views.mainlobby;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.views.ViewManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MainLobbyViewController implements Initializable
{
	@FXML
    private Button goToPatientsLobbyButton;

    @FXML
    private Button goToDoctorsLobbyButton;

    @FXML
    private Button goToAdministrationLobbyButton;
	
	private IMainLobbyViewModel mViewModel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		goToPatientsLobbyButton.setOnAction(event -> ViewManager.showHospitalizePatientsView());
		goToDoctorsLobbyButton.setOnAction(event -> ViewManager.showConsultationView());
		goToAdministrationLobbyButton.setOnAction(event -> ViewManager.showEditFacilitiesView());
	}
	
	public void setViewModel(IMainLobbyViewModel viewModel)
	{
		mViewModel = viewModel;
	}
}
