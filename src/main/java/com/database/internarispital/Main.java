package com.database.internarispital;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;
import com.database.internarispital.views.doctors.DoctorsViewController;
import com.database.internarispital.views.doctors.DoctorsViewModel;
import com.database.internarispital.views.editDoctors.EditDoctorsViewController;
import com.database.internarispital.views.editDoctors.EditDoctorsViewModel;
import com.database.internarispital.views.patients.PatientsViewController;
import com.database.internarispital.views.patients.PatientsViewModel;
import com.database.internarispital.views.records.RecordsVewController;
import com.database.internarispital.views.records.RecordsViewModel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application
{
	private static final String PATIENTS_VIEW_PATH = "PatientsView.fxml";
	private static final String DOCTORS_VIEW_PATH = "DoctorsView.fxml";
	private static final String RECORDS_VIEW_PATH = "RecordsView.fxml";
	private static final String EDIT_DOCTORS_VIEW_PATH = "EditDoctorsView.fxml";
	
	private static final String VERSION = "1.0.0";
	private static final String APP_NAME = "Patient Management";
	private Stage mStage;
	private DataBase mDataBase;
	
	public static void main( String[] args )
    {
		Main.launch(args);
    }
	
	@Override
	public void start(Stage stage) throws Exception
	{
		mStage = stage;
		mStage.setResizable(false);
		try
		{
			mDataBase = new DataBase();
			showPatientsView();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			Platform.exit();//Replace this with a pop-up error message
		}
	}
	
	@Override
	public void stop()
	{	
		//TODO
	}
	
	public void showMainView()
	{
		
	}
	
	public void showPatientsView()
	{
		setTitle("Patient View");
		PatientsViewController patientsViewController = (PatientsViewController)loadScene(PATIENTS_VIEW_PATH, mStage);
		
		Callback<Void, Void> showDoctorsViewCb = (noParam ->
		{
			showDoctorsView();
			return null;
		});
		Callback<Void, Void> showAdminViewCb = (noParam ->
		{
			showAdministrationView();
			return null;
		});
		
		new PatientsViewModel(patientsViewController, mDataBase, showDoctorsViewCb, showAdminViewCb);
	}
	
	public void showDoctorsView()
	{		
		setTitle("Patient Consultation");
		DoctorsViewController doctorsViewController = (DoctorsViewController)loadScene(DOCTORS_VIEW_PATH, mStage);
		
		Callback<Void, Void> showPatientsViewCb = (noParam ->
		{
			showPatientsView();
			return null;
		});
		Callback<HospitalizedPatient, Void> showPatientsRecordCb = (patient ->
		{
			showPatientsRecord(patient);
			return null;
		});
		
		new DoctorsViewModel(doctorsViewController, mDataBase, showPatientsViewCb, showPatientsRecordCb);
		
	}
	
	public void showPatientsRecord(HospitalizedPatient patient)
	{
		final Stage recordStage = new Stage();
		recordStage.setTitle("Medical Record for " + patient.getName());
		recordStage.setResizable(false);
		RecordsVewController recordsViewController = (RecordsVewController) loadScene(RECORDS_VIEW_PATH, recordStage);
		new RecordsViewModel(recordsViewController, mDataBase, patient);
	}
	
	public void showAdministrationView()
	{
		setTitle("Administration View");
		EditDoctorsViewController controller = (EditDoctorsViewController) loadScene(EDIT_DOCTORS_VIEW_PATH, mStage);
		
		Callback<Void, Void> returnCb = (noParam ->
		{
			showPatientsView();
			return null;
		});
		
		new EditDoctorsViewModel(controller, mDataBase, returnCb);
	}
	
	private Object loadScene(String scenePath, Stage stage)
	{
		Parent root = null;
		FXMLLoader loader;
		try
		{
			loader = new FXMLLoader();
			loader.setBuilderFactory(new JavaFXBuilderFactory());
			loader.setLocation(getClass().getResource(scenePath));
			InputStream inputStream = getClass().getResourceAsStream(scenePath);
		
			if(inputStream != null)
			{
				root = (Parent)loader.load(inputStream);
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
			return loader.getController();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private void setTitle(String name)
	{
		mStage.setTitle(APP_NAME + " - " + name + " v" + VERSION);
	}
}
