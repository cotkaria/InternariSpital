package com.database.internarispital.views.patients.stats;

import java.time.LocalDateTime;

public class TimePeriod
{
	private String mName;
	private LocalDateTime mStart;
	private LocalDateTime mEnd;
	
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
}
