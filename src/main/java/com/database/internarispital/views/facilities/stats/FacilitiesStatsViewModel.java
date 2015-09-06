package com.database.internarispital.views.facilities.stats;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.facilities.Section;
import com.database.internarispital.views.common.PeriodOccupancy;
import com.database.internarispital.views.common.TimePeriod;

public class FacilitiesStatsViewModel implements IFacilitiesStatsViewModel
{
	private FacilitiesStatsViewController mViewController;
	private DataBase mDataBase;
	
	public FacilitiesStatsViewModel(FacilitiesStatsViewController viewController, DataBase dataBase)
	{
		mViewController = viewController;
		mDataBase = dataBase;
		configureViewController();
	}
	
	private void configureViewController()
	{
		mViewController.setViewModel(this);
		mViewController.setSectionsCount(mDataBase.getSectionsCount());
		mViewController.setWardsCount(mDataBase.getWardsCount());
		mViewController.setBedsCount(mDataBase.getBedsCount());
		mViewController.setFreeBedsCount(mDataBase.getFreeBedsCount());
		setOccupancyRates();
	}
	
	private void setOccupancyRates()
	{
		Map<Section, ObservableList<PeriodOccupancy<Double>>> occupancyRates = new 
				HashMap<Section, ObservableList<PeriodOccupancy<Double>>>();
		
		ObservableList<Section> sections = mDataBase.getSections();
		for(Section section: sections)
		{
			ObservableList<PeriodOccupancy<Double>> occupancies = getPeriodOccupancies(section);
			occupancyRates.put(section, occupancies);
		}
		mViewController.setOccupancyRates(occupancyRates);
	}
	
	private ObservableList<PeriodOccupancy<Double>> getPeriodOccupancies(Section section)
	{
		ObservableList<PeriodOccupancy<Double>> occupancies = FXCollections.observableArrayList();
		for(TimePeriod period: TimePeriod.SEASONS)
		{
			double occupancy = mDataBase.getAverageOccupancyRateForSection(section, period.getStart(), period.getEnd());
			occupancies.add(new PeriodOccupancy<Double>(period.getName(), occupancy));
		}
		return occupancies;
	}
}
