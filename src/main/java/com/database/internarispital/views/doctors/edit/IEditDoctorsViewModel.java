package com.database.internarispital.views.doctors.edit;

import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.doctors.DoctorData;

public interface IEditDoctorsViewModel 
{
	void addDoctor(DoctorData data);
	void removeDoctor(Doctor doctor);
}
