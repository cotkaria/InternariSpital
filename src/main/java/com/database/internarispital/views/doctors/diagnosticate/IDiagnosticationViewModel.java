package com.database.internarispital.views.doctors.diagnosticate;

import com.database.internarispital.entities.DiagCategory;
import com.database.internarispital.entities.Diagnostic;
import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.patients.HospitalizedPatient;

public interface IDiagnosticationViewModel 
{
	void selectDiagCategory(DiagCategory diagCategory);
	void createConsultation(HospitalizedPatient patient, Doctor doctor, Diagnostic diagnostic, String diagnosticDate);
}
