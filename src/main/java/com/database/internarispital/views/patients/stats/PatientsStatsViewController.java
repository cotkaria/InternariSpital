package com.database.internarispital.views.patients.stats;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.views.common.CommonHelper;
import com.database.internarispital.views.common.PeriodOccupancy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class PatientsStatsViewController implements Initializable
{
	@FXML
    private Label patientsWithMultipleStaysLabel;

    @FXML
    private Label averageOccupancyRateLabel;

    @FXML
    private Label patientsTreatedLabel;

    @FXML
    private Label patientsStillAdmittedLabel;

    @FXML
    private Label currentOccupancyRateLabel;

    @FXML
    private ProgressBar currentOccupancyRateBar;

    @FXML
    private ProgressBar averageOccupancyRateBar;
    
    @FXML
    private LineChart<String, Integer> occupancyRateChart;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
	}
	
	public void setPatientsTreatedCount(int count)
	{
		patientsTreatedLabel.setText("Patients treated: " + count);
	}
	public void setPatientsCurrentlyAddmited(int count)
	{
		patientsStillAdmittedLabel.setText("Patients currently admitted: " + count);
	}
	public void setRecurringPatientsCount(int count)
	{		
		patientsWithMultipleStaysLabel.setText("Patients that have had more than one stay: " + count);
	}
	public void setCurrentOccupancyRate(float rate)
	{
		currentOccupancyRateLabel.setText("Current occupancy rate: " + CommonHelper.convertToPercentage(rate) + "%");
		currentOccupancyRateBar.setProgress(rate);
	}
	public void setAverageOccupancyRate(double rate)
	{
		averageOccupancyRateLabel.setText("Average occupancy rate: " + CommonHelper.convertToPercentage(rate)+ "%");
		averageOccupancyRateBar.setProgress(rate);
	}
	
	public void setOccupancyOverTime(ObservableList<PeriodOccupancy<Integer>> occupancies)
	{
		ObservableList<XYChart.Data<String,Integer>> chartValues = FXCollections.observableArrayList();
		for(PeriodOccupancy<Integer> occupancy: occupancies)
		{
			chartValues.add(new XYChart.Data<String,Integer>(occupancy.getPeriodName(), occupancy.getOccupancy()));
		}
		ObservableList<XYChart.Series<String,Integer>> chartData = FXCollections.observableArrayList();
		chartData.add(new LineChart.Series<String,Integer>("Patients count", chartValues));
		
		occupancyRateChart.setData(chartData);
	}
}
