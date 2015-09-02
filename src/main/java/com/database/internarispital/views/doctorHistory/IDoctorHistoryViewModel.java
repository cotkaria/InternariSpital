package com.database.internarispital.views.doctorHistory;

import com.database.internarispital.entities.doctors.Doctor;

public interface IDoctorHistoryViewModel 
{
	void showDoctorHistory(String beginDate, String endDate, boolean currentlyAdmittedCkB, boolean showAllHistory);
}
