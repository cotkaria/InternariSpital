package com.database.internarispital.views.patients;

import com.database.internarispital.entities.Bed;
import com.database.internarispital.entities.Section;
import com.database.internarispital.entities.Ward;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.PatientData;

public interface IPatientsViewModel 
{

	void hospitalizePatient(PatientData patientData, Bed bed);
	void dischargePatient(HospitalizedPatient hospitalizedPatient);
	void selectSection(Section section);
	void selectWard(Ward ward);
}
