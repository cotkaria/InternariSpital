package com.database.internarispital.views.patients.hospitalize;

import com.database.internarispital.entities.Bed;
import com.database.internarispital.entities.Section;
import com.database.internarispital.entities.Ward;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;

public interface IPatientsViewModel 
{
	void createPatient();
	void hospitalizePatient(Patient patient, Bed bed);
	void dischargePatient(HospitalizedPatient hospitalizedPatient);
	void selectSection(Section section);
	void selectWard(Ward ward);
}
