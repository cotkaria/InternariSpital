package com.database.internarispital.views.facilities.stats;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.database.internarispital.entities.facilities.Section;
import com.database.internarispital.views.common.CommonHelper;
import com.database.internarispital.views.common.PeriodOccupancy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class FacilitiesStatsViewController implements Initializable
{
    @FXML
    private Label sectionsCount;

	@FXML
    private Label wardsCount;

    @FXML
    private Label freeBedsCount;

    @FXML
    private Label bedsCount;

    @FXML
    private LineChart<String, Double> lineChart;
    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
	}
	
	public void setSectionsCount(int count)
	{
		sectionsCount.setText("Sections count: " + count);
	}
	public void setWardsCount(int count)
	{
		wardsCount.setText("Wards count: " + count);
	}
	public void setBedsCount(int count)
	{
		bedsCount.setText("Beds count: " + count);
	}
	public void setFreeBedsCount(int count)
	{
		freeBedsCount.setText("Free beds count: " + count);
	}
	public void setOccupancyRates(Map<Section, ObservableList<PeriodOccupancy<Double>>> occupancyRates)
	{
		for(Map.Entry<Section, ObservableList<PeriodOccupancy<Double>>> occupancyRate: occupancyRates.entrySet())
		{
			addOccupancyRateToChart(occupancyRate.getKey(), occupancyRate.getValue());
		}
	}
	private void addOccupancyRateToChart(Section section, ObservableList<PeriodOccupancy<Double>> values)
	{
		ObservableList<XYChart.Data<String,Double>> chartValues = FXCollections.observableArrayList();
		for(PeriodOccupancy<Double> occupancy: values)
		{
			chartValues.add(new XYChart.Data<String,Double>(occupancy.getPeriodName(), 
					CommonHelper.convertToPercentage(occupancy.getOccupancy())));
		}
		LineChart.Series<String,Double> series = new LineChart.Series<String,Double>(
				section.sectionNameProperty().getValue(), chartValues);
		
		lineChart.getData().add(series);
	}
}
