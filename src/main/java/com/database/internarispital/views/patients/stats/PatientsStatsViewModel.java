package com.database.internarispital.views.patients.stats;

import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.database.internarispital.DataBase;
import com.database.internarispital.views.common.PeriodOccupancy;
import com.database.internarispital.views.common.TimePeriod;

public class PatientsStatsViewModel implements IPatientsStatsViewModel
{
	private PatientsStatsViewController mViewController;
	private DataBase mDataBase;
	
	public PatientsStatsViewModel(PatientsStatsViewController viewController, DataBase dataBase)
	{
		mViewController = viewController;
		mDataBase = dataBase;
		configureViewController();
	}
	
	private void configureViewController()
	{
		mViewController.setPatientsTreatedCount(mDataBase.getPatientsTreatedCount());
		mViewController.setPatientsCurrentlyAddmited(mDataBase.getPatientsCurrentlyAddmited());
		mViewController.setRecurringPatientsCount(mDataBase.getRecurringPatientsCount());
		mViewController.setCurrentOccupancyRate(mDataBase.getCurrentOccupancyRate());
		setAverageOccupancyRate();
		setOccupancyOverTime();
	}
	
	private void setAverageOccupancyRate()
	{
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime startOfCurrentYear = LocalDateTime.parse(today.getYear() + "-01-01T00:00:00");
		double avgRate = mDataBase.getAverageOccupancyRate(startOfCurrentYear, today);
		mViewController.setAverageOccupancyRate(avgRate);
	}
	
	private void setOccupancyOverTime()
	{
		ObservableList<PeriodOccupancy<Integer>> occupancies = FXCollections.observableArrayList();
		for(TimePeriod period: TimePeriod.SEASONS)
		{
			int occupancy = mDataBase.getHospitalizedPatientsInPeriod(period.getStart(), period.getEnd());
			occupancies.add(new PeriodOccupancy<Integer>(period.getName(), occupancy));
		}
		mViewController.setOccupancyOverTime(occupancies);
	}
}
