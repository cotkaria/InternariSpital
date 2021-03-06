package com.database.internarispital.views;

import java.io.IOException;
import java.io.InputStream;

import com.database.internarispital.DataBase;
import com.database.internarispital.Main;
import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;
import com.database.internarispital.util.ControllerRootPair;
import com.database.internarispital.views.doctors.browse.BrowseDoctorsViewController;
import com.database.internarispital.views.doctors.browse.BrowseDoctorsViewModel;
import com.database.internarispital.views.doctors.create.CreateDoctorViewController;
import com.database.internarispital.views.doctors.create.CreateDoctorViewModel;
import com.database.internarispital.views.doctors.diagnosticate.DiagnosticationViewController;
import com.database.internarispital.views.doctors.diagnosticate.DiagnosticationViewModel;
import com.database.internarispital.views.doctors.edit.EditDoctorsViewController;
import com.database.internarispital.views.doctors.edit.EditDoctorsViewModel;
import com.database.internarispital.views.doctors.history.DoctorHistoryViewController;
import com.database.internarispital.views.doctors.history.DoctorHistoryViewModel;
import com.database.internarispital.views.doctors.stats.DoctorsStatsViewController;
import com.database.internarispital.views.doctors.stats.DoctorsStatsViewModel;
import com.database.internarispital.views.facilities.edit.EditFacilitiesViewController;
import com.database.internarispital.views.facilities.edit.EditFacilitiesViewModel;
import com.database.internarispital.views.facilities.stats.FacilitiesStatsViewController;
import com.database.internarispital.views.facilities.stats.FacilitiesStatsViewModel;
import com.database.internarispital.views.login.LoginViewController;
import com.database.internarispital.views.login.LoginViewModel;
import com.database.internarispital.views.mainlobby.MainLobbyViewController;
import com.database.internarispital.views.mainlobby.MainLobbyViewModel;
import com.database.internarispital.views.menuBar.MenuBarViewController;
import com.database.internarispital.views.menuBar.MenuBarViewModel;
import com.database.internarispital.views.patients.browse.BrowsePatientsViewController;
import com.database.internarispital.views.patients.browse.BrowsePatientsViewModel;
import com.database.internarispital.views.patients.create.CreatePatientViewController;
import com.database.internarispital.views.patients.create.CreatePatientViewModel;
import com.database.internarispital.views.patients.hospitalize.HospitalizationViewController;
import com.database.internarispital.views.patients.hospitalize.HospitalizationViewModel;
import com.database.internarispital.views.patients.records.RecordsViewController;
import com.database.internarispital.views.patients.records.RecordsViewModel;
import com.database.internarispital.views.patients.stats.PatientsStatsViewController;
import com.database.internarispital.views.patients.stats.PatientsStatsViewModel;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ViewManager
{
	private static final String LAYOUTS_PATH 					= "layouts/";
	
	private static final String EMPTY_VIEW 						= LAYOUTS_PATH + "MenuBarView.fxml";
	private static final String MAIN_VIEW_PATH 					= LAYOUTS_PATH + "MainView.fxml";

	private static final String PATIENTS_PATH 					= LAYOUTS_PATH + "patients/";
	private static final String HOSPITALIZE_PATIENTS_VIEW_PATH 	= PATIENTS_PATH + "HospitalizePatientsView.fxml";
	private static final String BROWSE_PATIENTS_VIEW_PATH 		= PATIENTS_PATH + "BrowsePatientsView.fxml";
	private static final String CREATE_PATIENT_VIEW_PATH 		= PATIENTS_PATH + "CreatePatientView.fxml";
	private static final String RECORDS_VIEW_PATH 				= PATIENTS_PATH + "RecordsView.fxml";

	private static final String DOCTORS_PATH 					= LAYOUTS_PATH + "doctors/";
	private static final String CREATE_DOCTOR_VIEW_PATH 		= DOCTORS_PATH + "CreateDoctorView.fxml";
	private static final String DIAGNOSTICS_VIEW_PATH 			= DOCTORS_PATH + "DiagnosticsView.fxml";
	private static final String BROWSE_DOCTORS_VIEW_PATH 		= DOCTORS_PATH + "BrowseDoctorsView.fxml";
	private static final String DOCTOR_HISTORY_VIEW_PATH 		= DOCTORS_PATH + "DoctorHistoryView.fxml";
	
	private static final String ADMIN_PATH 						= LAYOUTS_PATH + "admin/";
	//private static final String EDIT_PATIENTS_VIEW_PATH 		= ADMIN_PATH + "EditPatientsView.fxml";
	private static final String EDIT_DOCTORS_VIEW_PATH 			= ADMIN_PATH + "EditDoctorsView.fxml";
	private static final String EDIT_FACILITIES_VIEW_PATH 		= ADMIN_PATH + "EditFacilitiesView.fxml";
	
	private static final String LOGIN_PATH 						= LAYOUTS_PATH + "login/";
	private static final String LOGIN_VIEW_PATH 				= LOGIN_PATH + "LoginView.fxml";
	
	private static final String STATS_PATH 						= LAYOUTS_PATH + "stats/";
	private static final String PATIENTS_STATS_VIEW_PATH 		= STATS_PATH + "PatientsStatsView.fxml";
	private static final String DOCTORS_STATS_VIEW_PATH 		= STATS_PATH + "DoctorsStatsView.fxml";
	private static final String FACILITIES_STATS_VIEW_PATH 		= STATS_PATH + "FacilitiesStatsView.fxml";
	
	private static Stage mStage;
	private static Scene mScene;
	private static Group mRootGroup;
	private static DataBase mDataBase;
	
	private static double mViewLayoutY = 0.0;
	
	public static void init(Stage stage, DataBase database)
	{
		mStage = stage;
		mDataBase = database;
		loadEmptyView();
	}
	
	private static void loadEmptyView()
	{
		ControllerRootPair controllerRoot = loadLayout(EMPTY_VIEW);
		Parent root = controllerRoot.getRoot();
		if(root != null)
		{
			mRootGroup = new Group(root);
			mScene = new Scene(mRootGroup);
		}
		MenuBarViewController viewController = (MenuBarViewController)controllerRoot.getController();
		new MenuBarViewModel(viewController);
		
		configureMenuBar(viewController);
	}
	
	private static void configureMenuBar(MenuBarViewController viewController)
	{
		MenuBar menuBar = viewController.getMenuBar();
		menuBar.prefWidthProperty().bind(mStage.widthProperty());
		mViewLayoutY = menuBar.getPrefHeight();
	}

	public static void closeAllWindows()
	{
		Platform.exit();
	}
	
	public static void showLoginView()
	{
		setTitle("Login view");
		LoginViewController viewController = (LoginViewController)loadView(LOGIN_VIEW_PATH);
		new LoginViewModel(viewController, mDataBase);
	}
	
	public static void showMainView()
	{
		setTitle("Main Lobby");
		MainLobbyViewController viewController = (MainLobbyViewController)loadView(MAIN_VIEW_PATH);
		new MainLobbyViewModel(viewController);
	}
	
	public static void showHospitalizePatientsView()
	{
		setTitle("Hospitalize Patients View");
		HospitalizationViewController patientsViewController = (HospitalizationViewController)loadView(HOSPITALIZE_PATIENTS_VIEW_PATH);
		new HospitalizationViewModel(patientsViewController, mDataBase);
	}
	
	public static void showBrowsePatientsView()
	{
		setTitle("Browse Patients View");
		BrowsePatientsViewController patientsViewController = (BrowsePatientsViewController)loadView(BROWSE_PATIENTS_VIEW_PATH);
		new BrowsePatientsViewModel(patientsViewController, mDataBase);
	}
	
	public static void showBrowseDoctorsView()
	{
		setTitle("Browse Doctors View");
		BrowseDoctorsViewController viewController = (BrowseDoctorsViewController)loadView(BROWSE_DOCTORS_VIEW_PATH);
		new BrowseDoctorsViewModel(viewController, mDataBase);
	}
	
	public static void showDiagnosticsView()
	{		
		setTitle("Diagnostics View");
		DiagnosticationViewController doctorsViewController = (DiagnosticationViewController)loadView(DIAGNOSTICS_VIEW_PATH);
		new DiagnosticationViewModel(doctorsViewController, mDataBase);
	}
	
	public static void showEditPatientsView()
	{
		/*setTitle("Edit Doctors View");
		EditPatientsViewController controller = (EditPatientsViewController)loadView(EDIT_PATIENTS_VIEW_PATH);
		new EditPatientsViewModel(controller, mDataBase);*/
	}
	
	public static void showEditDoctorsView()
	{
		setTitle("Edit Doctors View");
		EditDoctorsViewController controller = (EditDoctorsViewController)loadView(EDIT_DOCTORS_VIEW_PATH);
		new EditDoctorsViewModel(controller, mDataBase);
	}
	
	public static void showEditFacilitiesView()
	{
		setTitle("Edit Facilities View");
		EditFacilitiesViewController controller = (EditFacilitiesViewController)loadView(EDIT_FACILITIES_VIEW_PATH);
		new EditFacilitiesViewModel(controller, mDataBase);
	}
	
	private static Object loadView(String scenePath)
	{
		ControllerRootPair controllerRoot = loadLayout(scenePath);
		Parent root = controllerRoot.getRoot();
		if(root != null)
		{
			ObservableList<Node> children = mRootGroup.getChildren(); 
			if(children.size() > 1)
			{
				children.remove(0);
			}
			children.add(0, root);
			root.layoutYProperty().set(mViewLayoutY);
			mStage.setScene(mScene);
			mStage.show();
		}
		return controllerRoot.getController();
	}
	
	public static void showCreateDoctorView(Callback<Doctor, Void> onDoctorCreatedCB)
	{
		final Stage stage = new Stage();
		stage.setTitle("Create a new doctor");
		stage.setResizable(false);
		CreateDoctorViewController viewController = (CreateDoctorViewController)loadScene(CREATE_DOCTOR_VIEW_PATH, stage);
		new CreateDoctorViewModel(viewController, mDataBase, stage, onDoctorCreatedCB);
	}
	
	public static void showDoctorHistory(Doctor doctor)
	{
		final Stage stage = new Stage();
		stage.setTitle("Treated Patients by  " + doctor.getName());
		stage.setResizable(false);
		DoctorHistoryViewController doctorHistoryViewController = (DoctorHistoryViewController)loadScene(DOCTOR_HISTORY_VIEW_PATH, stage);
		new DoctorHistoryViewModel(doctorHistoryViewController, mDataBase, doctor);
	}
	
	public static void showPatientsRecord(HospitalizedPatient patient)
	{
		final Stage stage = new Stage();
		stage.setTitle("Medical Record for " + patient.getName());
		stage.setResizable(false);
		RecordsViewController recordsViewController = (RecordsViewController)loadScene(RECORDS_VIEW_PATH, stage);
		new RecordsViewModel(recordsViewController, mDataBase, patient);
	}
	
	public static void showCreatePatientView(Callback<Patient, Void> onPatientCreatedCB)
	{
		final Stage stage = new Stage();
		stage.setTitle("Create a new patient");
		stage.setResizable(false);
		CreatePatientViewController viewController = (CreatePatientViewController)loadScene(CREATE_PATIENT_VIEW_PATH, stage);
		new CreatePatientViewModel(viewController, mDataBase, stage, onPatientCreatedCB);
	}
	
	public static void showPatientsStats()
	{
		final Stage stage = new Stage();
		stage.setTitle("Patient Stats");
		stage.setResizable(false);
		PatientsStatsViewController viewController = (PatientsStatsViewController)loadScene(PATIENTS_STATS_VIEW_PATH, stage);
		new PatientsStatsViewModel(viewController, mDataBase);
	}
	
	public static void showDoctorsStats()
	{
		final Stage stage = new Stage();
		stage.setTitle("Doctors Stats");
		stage.setResizable(false);
		DoctorsStatsViewController viewController = (DoctorsStatsViewController)loadScene(DOCTORS_STATS_VIEW_PATH, stage);
		new DoctorsStatsViewModel(viewController, mDataBase);
	}
	
	public static void showFacilitiesStats()
	{
		final Stage stage = new Stage();
		stage.setTitle("Facilities Stats");
		stage.setResizable(false);
		FacilitiesStatsViewController viewController = (FacilitiesStatsViewController)loadScene(FACILITIES_STATS_VIEW_PATH, stage);
		new FacilitiesStatsViewModel(viewController, mDataBase);
	}
	
	private static Object loadScene(String scenePath, Stage stage)
	{	
		ControllerRootPair controllerRoot = loadLayout(scenePath);
		Parent root = controllerRoot.getRoot();
		if(root != null)
		{
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		return controllerRoot.getController();
	}
	
	private static ControllerRootPair loadLayout(String scenePath)
	{
		Parent root = null;
		FXMLLoader loader = new FXMLLoader();
		loader.setBuilderFactory(new JavaFXBuilderFactory());
		loader.setLocation(Main.class.getResource(scenePath));
		InputStream inputStream = Main.class.getResourceAsStream(scenePath);
		if(inputStream != null)
		{
			try
			{
				root = (Parent)loader.load(inputStream);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		Object controller = loader.getController();
		
		assertIfNull(root, "Could not find file: " + scenePath);
		assertIfNull(controller, "Controller is null for: " + scenePath);
		
		return new ControllerRootPair(controller, root);
	}
	
	private static void setTitle(String name)
	{
		mStage.setTitle(name + " - " + Main.APP_NAME + " v" + Main.VERSION);
	}
	
	private static void assertIfNull(Object obj, String message)
	{
		if(obj == null)
		{
			System.err.println("ViewManager::assert() " + message);
			assert(false);
		}
	}
}
