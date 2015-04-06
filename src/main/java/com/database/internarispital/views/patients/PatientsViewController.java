package com.database.internarispital.views.patients;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import com.database.internarispital.entities.Bed;
import com.database.internarispital.entities.Section;
import com.database.internarispital.entities.Ward;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.PatientData;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.stage.Window;


public class PatientsViewController implements Initializable
{
	@FXML
	private Pane root;

	@FXML
	private TableView<HospitalizedPatient> patientsTable;

	@FXML
    private TableColumn<HospitalizedPatient, String> firstNameColumn;

	@FXML
	private TableColumn<HospitalizedPatient, String> lastNameColumn;

	@FXML
	private TableColumn<HospitalizedPatient, String> birthDateColumn;
	
	@FXML
	private TableColumn<HospitalizedPatient, String> sectionNameColumn;
	
	@FXML
	private TableColumn<HospitalizedPatient, Integer> wardNumberColumn;
	
	@FXML
	private TableColumn<HospitalizedPatient, Integer> bedNumberColumn;

	@FXML
	private TextField firstNameTF;
    
	@FXML
    private TextField lastNameTF;
    
	@FXML
    private DatePicker birthDatePicker;
    
	@FXML
	private Button doctorsViewButton;
		
    @FXML
    private Button insertButton;
    
    @FXML 
    private Button dischargeButton;
    
    @FXML
    private ComboBox<Section> sectionsList;
    
    @FXML
    private ComboBox<Ward> wardsList;
    
    @FXML
    private ComboBox<Bed> bedsList;
    
   
  
    private Window mWindow;
    private IPatientsViewModel mPatientsViewModel; 
    private Callback<Void, Void> mShowDoctorsViewCb;
  
    @Override
	public void initialize(URL location, ResourceBundle resources) 
    {
    	configurePatientsTable();
    	/*insertButton.setOnAction(new EventHandler<ActionEvent>()
    		{
    		@Override	
    		public void handle(ActionEvent event)
    			{
    				onInsertPatient();
    			}
    		});
    		*/
    	doctorsViewButton.setOnAction(event->onShowDoctorsView());
    	birthDatePicker.setValue(LocalDate.now());
    	sectionsList.setOnAction(event->onSectionSelected());
    	wardsList.setOnAction(event->onWardSelected());
    	insertButton.setOnAction(event->onHospitalizePatient());
    	dischargeButton.setOnAction(event->onDischargePatient());
	}
    
    public void setViewModel(IPatientsViewModel patientsViewModel)
    {
    	mPatientsViewModel = patientsViewModel;
    	assert(mPatientsViewModel != null);
    }
    public void setOnShowDoctorsView(Callback<Void, Void> showDoctorsView)
    {
    	mShowDoctorsViewCb = showDoctorsView;
    }
    private void onShowDoctorsView()
    {
    	if(mShowDoctorsViewCb != null)
    	{
    		mShowDoctorsViewCb.call(null);
    	}
    }
    private void configurePatientsTable()
    {
    	firstNameColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, String>("firstName"));
    	lastNameColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, String>("lastName"));
    	birthDateColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, String>("birthDate"));
    	sectionNameColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, String>("sectionName"));
    	wardNumberColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, Integer>("wardNumber"));
    	bedNumberColumn.setCellValueFactory(new PropertyValueFactory<HospitalizedPatient, Integer>("bedNumber"));
    	
    	
    }
    
   public void setPatients(ObservableList<HospitalizedPatient> hospitalizedPatients)
   {
	   patientsTable.setItems(hospitalizedPatients);
   }
   
   public void addPatient(HospitalizedPatient hospitalizedPatient)
   {
	   patientsTable.getItems().add(hospitalizedPatient);
   }
   public void removePatient(HospitalizedPatient hospitalizePatient)
   {
	   patientsTable.getItems().remove(hospitalizePatient);
   }
   
   private void onHospitalizePatient()
   {
  	   String firstName = firstNameTF.getText();
	   String lastName = lastNameTF.getText();
	   String birthDate = birthDatePicker.getValue().toString();
	   
	   if(firstName == null || firstName.isEmpty())
	   {
		   showErrorPopUp("Please provide a name for the patient!");
	   }
	   else if (lastName == null || lastName.isEmpty())
	   {
		   showErrorPopUp("Please provide a surname for the patient!");
	   }
	   else if (birthDate == null || birthDate.isEmpty())
	   {
		   showErrorPopUp("Please provide a birth date for the patient!");
	   }
	   else
	   {
		   PatientData patientData = new PatientData(firstName, lastName, birthDate);
		   SingleSelectionModel<Section> sectionSelectionModel = sectionsList.getSelectionModel();
		   if(sectionSelectionModel.isEmpty())
		   {
			   showErrorPopUp("Please select a section!");
		   }
		   else
		   {
			   SingleSelectionModel<Ward> wardSelectionModel = wardsList.getSelectionModel();
			   if(wardSelectionModel.isEmpty())
			   {
				   showErrorPopUp("Please select ward");
			   }
			   else
			   {
				   SingleSelectionModel<Bed> bedSelectionModel = bedsList.getSelectionModel();
				   if (bedSelectionModel.isEmpty())
				   {
					   showErrorPopUp("Select bed for patient");
				   }
				   else 
				   {
					   mPatientsViewModel.hospitalizePatient(patientData, bedSelectionModel.getSelectedItem());
				   }
			   }
		   }
	   }
   }
   
   public void onDischargePatient()
   {
	   
	  SelectionModel<HospitalizedPatient> selectionModel = patientsTable.getSelectionModel();
	  if(!selectionModel.isEmpty())
	  {
		   mPatientsViewModel.dischargePatient(selectionModel.getSelectedItem());
	  }
   }
   public void setSection(ObservableList<Section> sections)
   {
	   sectionsList.setItems(sections);
   }
   private void onSectionSelected()
   {
	   mPatientsViewModel.selectSection(sectionsList.getSelectionModel().getSelectedItem());
   }
   public void setWards(ObservableList<Ward> wards)
   {
	   wardsList.setItems(wards);
   }
   
   public int getSelectedWardId()
   {
	   SelectionModel<Ward> selectionModel = wardsList.getSelectionModel();
	   if(!selectionModel.isEmpty())
	   {
		   return selectionModel.getSelectedItem().wardIdProperty().getValue();
	   }
	   return -1;
   }
   public void resetInsertedValues()
   {
	   firstNameTF.clear();
	   lastNameTF.clear();
	   birthDatePicker.setValue(LocalDate.now());
	   wardsList.getSelectionModel().clearSelection();
   }
  
   private void onWardSelected()
   {
   
	   SingleSelectionModel<Ward> selectionModel = wardsList.getSelectionModel();
	   if(!selectionModel.isEmpty())
	   {
		   mPatientsViewModel.selectWard(selectionModel.getSelectedItem());
	   }
	   else
	   {
		   bedsList.getItems().clear();
	   }   
   }

   public void setBeds(ObservableList<Bed> beds)
   {
	   bedsList.setItems(beds);
   }
	private void showErrorPopUp(String message)
	{
		Dialogs.create().owner(mWindow).title("Error").message(message).showError();
	}
	
}
