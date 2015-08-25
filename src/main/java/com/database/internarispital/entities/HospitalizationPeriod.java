package com.database.internarispital.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HospitalizationPeriod 
{
	private int mHospitalizationId;
	private int mPatientId;
	private SimpleDateFormat mDateFormat;
	private Date mAdmittanceDate;
	private Date mDischargeDate;
	private String mAdmittanceDateAsString;
	private String mDischargeDateAsString;
	
	public HospitalizationPeriod(int hospitalizationId, int patientId, Date admittanceDate, Date dischargeDate)
	{
		mDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		mHospitalizationId = hospitalizationId;
		mPatientId = patientId;
		mAdmittanceDate = getDateOrDefault(admittanceDate);
		mDischargeDate = getDateOrDefault(dischargeDate);
		mAdmittanceDateAsString = formatDate(admittanceDate, "Never admitted");
		mDischargeDateAsString = formatDate(dischargeDate, "Still admitted");
	}
	
	private Date getDateOrDefault(Date date)
	{
		return (date != null) ? date : Calendar.getInstance().getTime(); 
	}
	
	private String formatDate(Date date, String defaultValue)
	{
		String format = defaultValue;
		if(date != null)
		{
			format = mDateFormat.format(date);
		}
		return format;
	}
	
	public int getHospitalizationId()
	{
		return mHospitalizationId;
	}
	
	public int getPatientId()
	{
		return  mPatientId;
	}
	
	public Date getAdmittanceDate()
	{
		return mAdmittanceDate;
	}
	
	public Date getDischargeDate()
	{
		return mDischargeDate;
	}
	
	public String getAdmittanceDateAsString()
	{
		return mDateFormat.format(mAdmittanceDate);
	}
	
	public String getDischargeDateAsString()
	{
		return mDateFormat.format(mDischargeDate);
	}
	
	@Override
	public String toString()
	{
		return mAdmittanceDateAsString + " - " + mDischargeDateAsString;
	}
	
}
