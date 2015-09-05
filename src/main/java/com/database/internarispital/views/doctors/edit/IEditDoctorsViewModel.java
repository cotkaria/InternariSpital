package com.database.internarispital.views.doctors.edit;

import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.doctors.DoctorData;

public interface IEditDoctorsViewModel 
{
	void showCreateDoctorView();
	void removeDoctor(Doctor doctor);
	
	void updateDoctor(Doctor doctor, DoctorData newData);
}
