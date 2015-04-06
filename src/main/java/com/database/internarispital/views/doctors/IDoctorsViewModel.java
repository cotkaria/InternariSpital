package com.database.internarispital.views.doctors;

import com.database.internarispital.entities.DiagCategory;
import com.database.internarispital.entities.Diagnostic;
import com.database.internarispital.entities.Doctor;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;

public interface IDoctorsViewModel 
{
	void selectDiagCategory(DiagCategory diagCategory);
	void createConsultation(HospitalizedPatient patient, Doctor doctor, Diagnostic diagnostic, String diagnosticDate);
	

}
