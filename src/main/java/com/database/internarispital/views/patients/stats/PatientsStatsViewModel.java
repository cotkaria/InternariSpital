package com.database.internarispital.views.patients.stats;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.database.internarispital.DataBase;

public class PatientsStatsViewModel implements IPatientsStatsViewModel
{
	private static final int SPRING_START_MONTH = 3;
	private static final int SPRING_START_DAY = 2;
	private static final int SPRING_END_MONTH = 6;
	private static final int SPRING_END_DAY = 1;
	
	private static final int SUMMER_START_MONTH = 6;
	private static final int SUMMER_START_DAY = 2;
	private static final int SUMMER_END_MONTH = 9;
	private static final int SUMMER_END_DAY = 1;
	
	private static final int AUTUMN_START_MONTH = 9;
	private static final int AUTUMN_START_DAY = 2;
	private static final int AUTUMN_END_MONTH = 12;
	private static final int AUTUMN_END_DAY = 1;
	
	private static final int WINTER_START_MONTH = 12;
	private static final int WINTER_START_DAY = 2;
	private static final int WINTER_END_MONTH = 3;
	private static final int WINTER_END_DAY = 1;
	
	private PatientsStatsViewController mViewController;
	private DataBase mDataBase;
	private List<TimePeriod> mTimePeriods;
	
	public PatientsStatsViewModel(PatientsStatsViewController viewController, DataBase dataBase)
	{
		mViewController = viewController;
		mDataBase = dataBase;
		initTimePeriods();
		configureViewController();
	}
	
	private void initTimePeriods()
	{
		int currentYear = LocalDateTime.now().getYear();
		TimePeriod spring = getTimePeriod("Spring", currentYear, SPRING_START_MONTH, SPRING_START_DAY, 
																SPRING_END_MONTH, SPRING_END_DAY);
		TimePeriod summer = getTimePeriod("Summer", currentYear, SUMMER_START_MONTH, SUMMER_START_DAY, 
																SUMMER_END_MONTH, SUMMER_END_DAY);
		TimePeriod autumn = getTimePeriod("Autumn", currentYear, AUTUMN_START_MONTH, AUTUMN_START_DAY, 
																AUTUMN_END_MONTH, AUTUMN_END_DAY);
		TimePeriod winter = getTimePeriod("Winter", currentYear, WINTER_START_MONTH, WINTER_START_DAY, 
																WINTER_END_MONTH, WINTER_END_DAY);
		mTimePeriods = new ArrayList<TimePeriod>();
		addPeriod(mTimePeriods, spring);
		addPeriod(mTimePeriods, summer);
		addPeriod(mTimePeriods, autumn);
		addPeriod(mTimePeriods, winter);
	}
	
	private TimePeriod getTimePeriod(String name, int year, int startMonth, int startDay,
			int endMonth, int endDay)
	{
		LocalDateTime start = getDate(year, startMonth, startDay);
		LocalDateTime end = getDate(year, endMonth, endDay);
		return new TimePeriod(name, start, end);
	}
	
	private void addPeriod(List<TimePeriod> periods, TimePeriod period)
	{
		if(period.getStart().compareTo(LocalDateTime.now()) <= 0)
		{
			periods.add(period);
		}
	}
	
	private void configureViewController()
	{
		mViewController.setViewModel(this);
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
		ObservableList<PeriodOccupancy> occupancies = FXCollections.observableArrayList();
		for(TimePeriod period: mTimePeriods)
		{
			int occupancy = mDataBase.getHospitalizedPatientsInPeriod(period.getStart(), period.getEnd());
			occupancies.add(new PeriodOccupancy(period.getName(), occupancy));
		}
		mViewController.setOccupancyOverTime(occupancies);
	}
	
	private LocalDateTime getDate(int year, int month, int day)
	{
		return LocalDateTime.parse(year + "-" + getTwoDigits(month) + "-" + getTwoDigits(day) + "T00:00:00");
	}
	private String getTwoDigits(int value)
	{
		String valueAsString = (value < 10) ? ("0"+value) : (""+value);
		return valueAsString;
	}
}
