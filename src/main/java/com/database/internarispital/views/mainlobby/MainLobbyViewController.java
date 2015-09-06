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
    private Button showPatientsStats;

    @FXML
    private Button showDoctorsStats;

    @FXML
    private Button showFacilitiesStats;
	
	private IMainLobbyViewModel mViewModel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		showPatientsStats.setOnAction(event -> ViewManager.showPatientsStats());
		showDoctorsStats.setOnAction(event -> ViewManager.showDoctorsStats());
		showFacilitiesStats.setOnAction(event -> ViewManager.showFacilitiesStats());
	}
	
	public void setViewModel(IMainLobbyViewModel viewModel)
	{
		mViewModel = viewModel;
	}
}
