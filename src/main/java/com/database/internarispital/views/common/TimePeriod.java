package com.database.internarispital.views.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimePeriod
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
	
	private String mName;
	private LocalDateTime mStart;
	private LocalDateTime mEnd;
	
	public static final List<TimePeriod> SEASONS;
	static
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
		SEASONS = new ArrayList<TimePeriod>();
		addPeriod(SEASONS, spring);
		addPeriod(SEASONS, summer);
		addPeriod(SEASONS, autumn);
		addPeriod(SEASONS, winter);
	}
	
	public TimePeriod(String name, LocalDateTime start, LocalDateTime end)
	{
		mName = name;
		mStart = start;
		mEnd = end;
	}
	
	public String getName()
	{
		return mName;
	}
	public LocalDateTime getStart()
	{
		return mStart;
	}
	public LocalDateTime getEnd()
	{
		return mEnd;
	}
	
	private static TimePeriod getTimePeriod(String name, int year, int startMonth, int startDay,
			int endMonth, int endDay)
	{
		LocalDateTime start = getDate(year, startMonth, startDay);
		LocalDateTime end = getDate(year, endMonth, endDay);
		return new TimePeriod(name, start, end);
	}
	
	private static void addPeriod(List<TimePeriod> periods, TimePeriod period)
	{
		if(period.getStart().compareTo(LocalDateTime.now()) <= 0)
		{
			periods.add(period);
		}
	}
	
	private static LocalDateTime getDate(int year, int month, int day)
	{
		return LocalDateTime.parse(year + "-" + getTwoDigits(month) + "-" + getTwoDigits(day) + "T00:00:00");
	}
	private static String getTwoDigits(int value)
	{
		String valueAsString = (value < 10) ? ("0"+value) : (""+value);
		return valueAsString;
	}
}
