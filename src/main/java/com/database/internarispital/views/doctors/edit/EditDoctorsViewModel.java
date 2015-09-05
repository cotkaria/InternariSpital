package com.database.internarispital.views.doctors.edit;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.doctors.DoctorData;
import com.database.internarispital.views.ViewManager;

public class EditDoctorsViewModel implements IEditDoctorsViewModel
{
	private EditDoctorsViewController mViewController;
	private DataBase mDataBase;
	
	public EditDoctorsViewModel(EditDoctorsViewController controller, DataBase dataBase)
	{
		mViewController = controller;
		mDataBase = dataBase;
		if(mViewController != null)
		{
			configController();
		}
	}
	
	private void configController()
	{
		mViewController.setViewModel(this);
		mViewController.setDoctors(mDataBase.getDoctors());		
	}
	
	@Override
	public void showCreateDoctorView()
	{
		ViewManager.showCreateDoctorView(doctor -> 
		{
			mViewController.addDoctor(doctor);
			return null;
		});
	}
	
	@Override
	public void removeDoctor(Doctor doctor)
	{
		mDataBase.setDoctorInactive(doctor);
	}

	@Override
	public void updateDoctor(Doctor doctor, DoctorData newData)
	{
		Doctor updatedDoctor = mDataBase.updateDoctor(doctor, newData);
		doctor.nameProperty().set(updatedDoctor.nameProperty().getValue());
		doctor.surnameProperty().set(updatedDoctor.surnameProperty().getValue());
		doctor.specialityProperty().set(updatedDoctor.specialityProperty().getValue());
		doctor.gradeProperty().set(updatedDoctor.gradeProperty().getValue());
	}
}
