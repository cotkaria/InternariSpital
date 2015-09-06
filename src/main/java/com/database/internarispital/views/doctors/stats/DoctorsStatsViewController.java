package com.database.internarispital.views.doctors.stats;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.database.internarispital.entities.Diagnostic;
import com.database.internarispital.entities.doctors.Doctor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class DoctorsStatsViewController implements Initializable
{
	@FXML
    private Label mostActiveDoctor;

    @FXML
    private Label mostCommonDiagnostic;

    @FXML
    private Label activeDoctors;

    @FXML
    private Label consultationsMade;

    @FXML
    private PieChart pieChart;
	
	private IDoctorsStatsViewModel mViewModel;
    
    public void setViewModel(IDoctorsStatsViewModel viewModel)
    {
    	mViewModel = viewModel;
    	assert(mViewModel != null);
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
	}
	
	public void setActiveDoctorsCount(int count)
	{
		activeDoctors.setText("Active doctors: " + count);
	}
	
	public void setConsultationsMadeCount(int count)
	{
		consultationsMade.setText("Consultations made: " + count);
	}
	
	public void setMostActiveDoctor(Doctor doctor)
	{
		mostActiveDoctor.setText("Most active doctor: " + doctor.getName());
	}
	
	public void setMostCommonDiagnostic(Diagnostic diagnostic)
	{
		mostCommonDiagnostic.setText("Most common diagnostic: " + diagnostic.toString());
	}
	public void setConsultationsCountMadeByEachDoctor(Map<Doctor, Integer> doctorConsults)
	{
		ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
		for(Map.Entry<Doctor, Integer> doctorConsult: doctorConsults.entrySet())
		{
			int value = doctorConsult.getValue();
			String label = doctorConsult.getKey().getName() + " [" + value + "]"; 
			data.add(new PieChart.Data(label, value));
		}
		pieChart.setData(data);
	}
}
