package com.database.internarispital.views.editDoctors;

import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.doctors.DoctorData;

public interface IEditDoctorsViewModel 
{
	void addDoctor(DoctorData data);
	void removeDoctor(Doctor doctor);
}
