package com.database.internarispital.views.doctors.history;

import java.awt.event.ActionListener;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.database.internarispital.entities.Consultation;
import com.database.internarispital.entities.Diagnostic;
import com.database.internarispital.entities.patients.Patient;
import com.database.internarispital.util.DisabledDateRange;
import com.database.internarispital.views.doctors.common.DoctorsHelper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class DoctorHistoryViewController  implements Initializable
{
	@FXML
    private TableView<Consultation> consultationTable;
	
	@FXML
    private TableColumn<Consultation, String> patientNameColumn;
	
    @FXML
    private TableColumn<Consultation, String> consultationDate;

    @FXML
    private TableColumn<Consultation, String> diagnosticColumn;
    
    @FXML
    private RadioButton selectIntervalRb;

    @FXML
    private RadioButton allConsultationsRb;

    @FXML
    private ToggleGroup radioGroup;
	
    @FXML
    private Label labelTip;
    
    @FXML
    private DatePicker startPeriod;
    
    @FXML
    private DatePicker endPeriod;
    
    @FXML
    private CheckBox currentlyAdmittedCkB;
    
    Callback<DatePicker, DateCell> mDayCellFactory;
	private IDoctorHistoryViewModel mViewModel;
	private boolean showAllHistory;
	
	public void setViewModel(IDoctorHistoryViewModel viewModel)
	{
		mViewModel = viewModel;
		assert(mViewModel != null);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		configureHistoryTable();
		
		DoctorsHelper.showPatientsRecordOnDoubleClick(consultationTable);
		
		startPeriod.setValue(LocalDate.now());
		endPeriod.setValue(LocalDate.now());
		setDisabledRange(startPeriod, LocalDate.of(1900,01,01), LocalDate.now(), mDayCellFactory);
		setDisabledRange(endPeriod, LocalDate.of(1900,01,01), LocalDate.now(), mDayCellFactory);
		
		startPeriod.setOnAction(event -> onHistoryIntervalChanged());
		endPeriod.setOnAction(event -> onHistoryIntervalChanged());
		currentlyAdmittedCkB.setOnAction(event -> onCurrentlyAdmittedChanged());
		
		radioGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> 
		{
			 if (radioGroup.getSelectedToggle() != null) 
			 {
				 showAllHistory = (selectIntervalRb.isSelected() == false);
				 startPeriod.setDisable(showAllHistory);
				 endPeriod.setDisable(showAllHistory);
				 startPeriod.setOnAction(event -> { setDisabledRange(endPeriod, startPeriod.getValue(),LocalDate.now(), mDayCellFactory);
				 onHistoryIntervalChanged();});	 				
				 endPeriod.setOnAction(event -> { setDisabledRange(startPeriod, LocalDate.of(1900,01,01), endPeriod.getValue(), mDayCellFactory);
				 onHistoryIntervalChanged();});	
				 onHistoryIntervalChanged();
			 }
		});
	}
	private void setDisabledRange(DatePicker datePicker, LocalDate startDate, LocalDate endDate, Callback<DatePicker, DateCell> dayCellFactory)
	{
		final ObservableList<DisabledDateRange> rangesToDisable = 
			    FXCollections.observableArrayList(
			    		new DisabledDateRange(startDate, endDate));
		mDayCellFactory = dayCellFactory;
		dayCellFactory = dp -> new DateCell()
		{
			@Override
			public void updateItem(LocalDate item, boolean empty) {
			    super.updateItem(item, empty);
	
			    boolean disable = rangesToDisable.stream()
			            .filter(r->r.getInitialDate().minusDays(1).isBefore(item))
			            .filter(r->r.getEndDate().plusDays(1).isAfter(item))
			            .findAny()
			            .isPresent();
	
			    if (!disable) 
			    {
			            setDisable(true);
			            setStyle("-fx-background-color: #ffc0cb;");
			    }
			}
		};
		datePicker.setDayCellFactory(dayCellFactory);
	}
	
	private void configureHistoryTable()
    {
    	patientNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Consultation, String>, ObservableValue<String>>()
		{
			public ObservableValue<String> call (CellDataFeatures<Consultation, String> consultationCellData)
			{
				Patient patient = consultationCellData.getValue().getPatient();
				return new SimpleStringProperty(patient.getName());
			}
		});
    	consultationDate.setCellValueFactory(new PropertyValueFactory<Consultation, String>("consultationDate"));
    	diagnosticColumn.setCellValueFactory(new Callback<CellDataFeatures<Consultation, String>, ObservableValue<String>>()
		{
			public ObservableValue<String> call (CellDataFeatures<Consultation, String> consultationCellData)
			{
				Diagnostic diagnostic = consultationCellData.getValue().getDiagnostic();
				
				return new SimpleStringProperty(diagnostic.diagNameProperty().getValue());
			}
		});
    }

	public void setDoctorHistory(ObservableList<Consultation> consultations)
	{
		consultationTable.setItems(consultations);
	}
	
	private void onCurrentlyAdmittedChanged()
	{
		showAllHistory = (selectIntervalRb.isSelected() == false);
		onHistoryIntervalChanged();
	}
	
	private void onHistoryIntervalChanged()
	{
		mViewModel.showDoctorHistory(startPeriod.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE), 
    								 endPeriod.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE), 
    								 currentlyAdmittedCkB.selectedProperty().getValue(),
    								 showAllHistory);
	}
}
