package com.database.internarispital;

import java.security.PrivilegedActionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.database.internarispital.entities.Bed;
import com.database.internarispital.entities.Consultation;
import com.database.internarispital.entities.DiagCategory;
import com.database.internarispital.entities.Diagnostic;
import com.database.internarispital.entities.Doctor;
import com.database.internarispital.entities.HospitalizationPeriod;
import com.database.internarispital.entities.Section;
import com.database.internarispital.entities.Ward;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;
import com.database.internarispital.entities.patients.PatientData;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.sun.corba.se.spi.orbutil.fsm.State;

public class DataBase 
{
	//Database credentials
	private static final String SERVER_NAME = "localhost";
	private static final String DATABASE_NAME = "InternariSpital";
	private static final int PORT_NUMBER = 1433;
	private static final String USER = "client";
	private static final String PASSWORD = "conexiune";
	
	private SQLServerDataSource mDataSource;
	private Connection mConnection;
	private Statement mStatement; 
	private PreparedStatement mInsertPatientStatement;
	private PreparedStatement mInsertConsultationStatement;
	public DataBase() throws SQLException
	{
		initializeDataSource();
		initializeConnection();
	}
	
	private void initializeDataSource()
	{
		mDataSource = new SQLServerDataSource();
    	mDataSource.setServerName(SERVER_NAME);
    	mDataSource.setPortNumber(PORT_NUMBER);
    	mDataSource.setDatabaseName(DATABASE_NAME);
    	mDataSource.setUser(USER);
    	mDataSource.setPassword(PASSWORD);
	}
	
	private void initializeConnection() throws SQLException
	{
		try
		{
			mConnection = mDataSource.getConnection();
    		mStatement = mConnection.createStatement();
    		initInsertPatientStatement();
    		initInsertConsultationStatement();
		}
		catch(SQLException e1)
		{
			finalize();
        	throw e1;
		}
	}
	
	private void initInsertPatientStatement() throws SQLException
	{
		String sqlQuery = "INSERT INTO Patients VALUES (?, ?, ?)";
		mInsertPatientStatement = mConnection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
	}
	private void initInsertConsultationStatement() throws SQLException
	{
		String sqlQuery = "INSERT INTO Consultations VALUES(?, ?, ?, ?)";
		mInsertConsultationStatement = mConnection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
	}
	
	@Override
	public void finalize()
	{
		closeStatement(mStatement);
		closeStatement(mInsertPatientStatement);
		closeStatement(mInsertConsultationStatement);
		closeConnection();	
	}
	
	private void closeStatement(Statement statement)
	{
		try
    	{
    		if(statement != null)
    		{
    			statement.close();
    			statement = null;
    		}        		
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}	
	}
	
	private void closeConnection()
	{
		try
    	{
    		if(mConnection != null)
    		{
    			mConnection.close();
    			mConnection = null;
    		}        		
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
	}
	
	public ObservableList<HospitalizedPatient> getHospitalizedPatients()
	{
		ObservableList<HospitalizedPatient> hospitalizedPatients = FXCollections.observableArrayList();
        
        try
        {   
        	 //STEP 4: Execute a query
        	String sqlQuery = "SELECT P.patient_Id, P.patient_name, P.patient_Surname, P.birth_date, B.bed_number, W.ward_number, S.section_name "
        					+ "FROM Patients AS P " 
        					+ "INNER JOIN Hospitalizations AS H ON P.patient_Id = H.patient_Id "
        					+ "INNER JOIN Beds AS B ON B.bed_Id = H.bed_Id "
    						+ "INNER JOIN Wards AS W ON W.ward_Id = B.ward_Id "
        					+ "INNER JOIN Sections AS S ON S.section_Id = W.section_Id";
        	ResultSet resultSet = mStatement.executeQuery(sqlQuery);
        	
        	//STEP 5: Extract data from ResultSet
        	while(resultSet.next())
        	{
        		hospitalizedPatients.add(parseResultSetForHospitalizedPatient(resultSet));
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();        	
        }
        return hospitalizedPatients;
	}
	private HospitalizedPatient parseResultSetForHospitalizedPatient(ResultSet resultSet) throws SQLException
	{
		int patientId = resultSet.getInt("patient_Id");
		String patientName = resultSet.getString("patient_name");
		String patientSurname = resultSet.getString("patient_surname");
		String birthDate = resultSet.getString("birth_date");
		String section = resultSet.getString("section_Name");
		int wardNumber = resultSet.getInt("ward_number");
		int bedNumber = resultSet.getInt("bed_number");
		
		return new HospitalizedPatient(new Patient(patientId, new PatientData(patientName, patientSurname, birthDate)), section, wardNumber, bedNumber);
		
	}
	
	public HospitalizedPatient getHospitalizedPatient(Patient patient)
	{
		HospitalizedPatient hospitalizedPatient = null;
		String sqlQuery = "SELECT P.patient_Id, B.bed_number, W.ward_number, S.section_name "
				+ "FROM Patients AS P " 
				+ "INNER JOIN Hospitalizations AS H ON P.patient_Id = H.patient_Id "
				+ "INNER JOIN Beds AS B ON B.bed_Id = H.bed_Id "
				+ "INNER JOIN Wards AS W ON W.ward_Id = B.ward_Id "
				+ "INNER JOIN Sections AS S ON S.section_Id = W.section_Id "
				+ "WHERE P.patient_Id = " + patient.getPatientId();
		try 
		{
			ResultSet resultSet = mStatement.executeQuery(sqlQuery);
			if(resultSet.next())
			{
				String section = resultSet.getString("section_name");
				int wardNumber = resultSet.getInt("ward_number");
				int bedNumber = resultSet.getInt("bed_number");
				hospitalizedPatient = new HospitalizedPatient(patient, section, wardNumber, bedNumber);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return hospitalizedPatient;
	}
	public Patient insertNewPatient(PatientData patientData)
	{
		Patient patient = null;
		try 
		{
			mInsertPatientStatement.setString(1, patientData.firstNameProperty().getValue());
			mInsertPatientStatement.setString(2, patientData.lastNameProperty().getValue());
			mInsertPatientStatement.setString(3, patientData.birthDateProperty().getValue());

			int rowsModified = mInsertPatientStatement.executeUpdate();
			
			System.out.println("insertNewPatient() rowsModified = " + rowsModified);
			
			int patientId = -1;
			if (rowsModified == 1)
			{
				ResultSet generatedKeys = mInsertPatientStatement.getGeneratedKeys();
				if(generatedKeys.next())
				{
					patientId = generatedKeys.getInt(1);
					patient = new Patient(patientId, patientData);
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return patient;
	}
	
	public HospitalizedPatient hospitalizePatient(Patient patient, int bedId)
	{
		createHospitalizationRecord(patient);
		HospitalizedPatient hospitalizedPatient = null;
		String sqlHospitalize = "INSERT INTO Hospitalizations VALUES ("
							+ patient.getPatientId() + "," + bedId + ")";
		try
		{
			mStatement.executeUpdate(sqlHospitalize);
			setBedOccupancy(bedId, true);
			
			hospitalizedPatient = getHospitalizedPatient(patient);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return hospitalizedPatient;
	}
	
	private void createHospitalizationRecord(Patient patient)
	{
		String sqlAdmit = "INSERT INTO Hospitalization_Records VALUES("
						+ patient.getPatientId() + ", '" + getCurrentDate() + "', NULL)";
		try
		{
			mStatement.executeUpdate(sqlAdmit);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	private String getCurrentDate()
	{
    	return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
	public void dischargePatient(int patientId)
	{
		String sqlRetrieveBedId = "SELECT bed_Id FROM Hospitalizations WHERE patient_Id = " + patientId;
		try
		{
			ResultSet resultSet = mStatement.executeQuery(sqlRetrieveBedId);
			
			if(resultSet.next())
			{
				int bedId = resultSet.getInt("bed_Id");
				setBedOccupancy(bedId, false);
				String sqlDischarge = "DELETE FROM Hospitalizations WHERE bed_Id = " + bedId;
				mStatement.executeUpdate(sqlDischarge);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public Bed getPatientBed(int patientId)
	{
		String sqlRetrievedBed = "SELECT * FROM Beds WHERE bed_Id  = ( SELECT bed_Id FROM Hospitalizations WHERE patient_Id = " + patientId + ")";
		Bed bed = null;
		try
		{
			ResultSet resultSet = mStatement.executeQuery(sqlRetrievedBed);
			if(resultSet.next())
			{
				int bedId = resultSet.getInt("bed_Id");
				int wardId = resultSet.getInt("ward_Id");
				int bedNumber = resultSet.getInt("bed_number");
				boolean occupancyStatus = resultSet.getBoolean("occupancy");
				bed = new Bed(bedId, bedNumber, wardId, occupancyStatus);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return bed;
	}
	private void setBedOccupancy(int bedId, boolean occupancyStatus)
	{
		String sqlUpdate = "UPDATE Beds SET occupancy = '" + occupancyStatus + "' WHERE bed_Id = " + bedId;
		try
		{
			mStatement.executeUpdate(sqlUpdate);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public ObservableList<Bed> getVacantBeds(int wardId)
	{
		ObservableList<Bed> bedsList = FXCollections.observableArrayList();
		String sqlQuery = "SELECT * FROM Beds WHERE ward_Id = " + wardId + " AND occupancy = 'FALSE'";
		try
		{
			ResultSet resultSet = mStatement.executeQuery(sqlQuery);
			while(resultSet.next())
			{
				int bedId = resultSet.getInt("bed_Id");
				int bedNumber = resultSet.getInt("bed_number");
				bedsList.add(new Bed(bedId, bedNumber, wardId, false));				
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return bedsList;
	}
	
	public ObservableList<Ward> getWards(int sectionId)
	{
		ObservableList<Ward> wardsList = FXCollections.observableArrayList();
		String sqlQuery = "SELECT * FROM Wards WHERE section_Id = " + sectionId;
		try
		{
			ResultSet resultSet = mStatement.executeQuery(sqlQuery);
			while(resultSet.next())
			{
				int wardId = resultSet.getInt("ward_Id");
				String section  = resultSet.getString("section_Id");
				int wardNumber = resultSet.getInt("ward_number");
				wardsList.add(new Ward(wardId, section, wardNumber));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return wardsList;
	}
	
	public ObservableList<Doctor> getDoctors()
	{
		ObservableList<Doctor> doctorsList = FXCollections.observableArrayList();
		String sqlQuery = "SELECT * FROM Doctors";
		try
		{
			ResultSet resultSet = mStatement.executeQuery(sqlQuery);
			while(resultSet.next())
			{
				doctorsList.add(parseResultSetForDoctor(resultSet));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return doctorsList;
	}
	
	private Doctor parseResultSetForDoctor(ResultSet resultSet) throws SQLException
	{
		int doctorId = resultSet.getInt("doctor_Id");
		String doctorName = resultSet.getString("doctor_name");
		String doctorSurname = resultSet.getString("doctor_surname");
		String speciality = resultSet.getString("speciality");
		return new Doctor(doctorId, doctorName, doctorSurname, speciality);
	}
	public ObservableList<Section> getSections()
	{
		ObservableList<Section> sectionsList = FXCollections.observableArrayList();
		String sqlQuery = "SELECT * FROM Sections";
		try
		{
			ResultSet resultSet = mStatement.executeQuery(sqlQuery);
			while(resultSet.next())
			{
				int sectionId = resultSet.getInt("section_Id");
				String sectionName = resultSet.getString("section_name");
				sectionsList.add(new Section(sectionId, sectionName));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return sectionsList;
	}
	public ObservableList<DiagCategory> getDiagCategories()
	{
		ObservableList<DiagCategory> diagCategoriesList = FXCollections.observableArrayList();
		String sqlQueryL = "SELECT * FROM Diag_Category";
		try
		{
			ResultSet resultSet = mStatement.executeQuery(sqlQueryL);
			while(resultSet.next())
			{
				int diagCategoryId = resultSet.getInt("category_Id");
				String diagCategoryName = resultSet.getString("category_name");
				String diagCategoryDescription = resultSet.getString("category_description");
				diagCategoriesList.add(new DiagCategory(diagCategoryId, diagCategoryName, diagCategoryDescription));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return diagCategoriesList;
	}
	public ObservableList<Diagnostic> getSpecificCategoryDiagnostics(int diagCategoryId)
	{
		ObservableList<Diagnostic> diagnosticsList = FXCollections.observableArrayList();
		String sqlQuery = "SELECT * FROM Diagnostics D INNER JOIN Diag_Category DC ON D.category_id = DC.category_id "
						+ "where DC.category_id = " + diagCategoryId;
		try
		{
			ResultSet resultSet = mStatement.executeQuery(sqlQuery);
			while(resultSet.next())
			{
				diagnosticsList.add(parseResultSetForDiagnostic(resultSet));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return diagnosticsList;
	}
	
	private Diagnostic parseResultSetForDiagnostic(ResultSet resultSet) throws SQLException
	{
		int diagId = resultSet.getInt("diag_Id");
		String diagName = resultSet.getString("diag_name");
		String diagDescription = resultSet.getString("diag_description");
		String diagCategoryName = resultSet.getString("category_name");
		return new Diagnostic(diagId, diagName, diagCategoryName, diagDescription);
	}
	
	public Consultation createConsultation(HospitalizedPatient patient, Doctor doctor, Diagnostic diagnostic, String consultationDate)
	{
		Consultation consultation = null;
		try 
		{
			mInsertConsultationStatement.setInt(1, patient.getPatientId());
			mInsertConsultationStatement.setInt(2, doctor.doctorIdProperty().getValue());
			mInsertConsultationStatement.setInt(3, diagnostic.diagIdProperty().getValue());
			mInsertConsultationStatement.setString(4, consultationDate);
			int rowsModified = mInsertConsultationStatement.executeUpdate();
			if(rowsModified == 1)
			{
				ResultSet resultSet = mInsertConsultationStatement.getGeneratedKeys();
				if(resultSet.next())
				{
					int consultationId = resultSet.getInt(1);
					consultation = new Consultation(consultationId, patient, doctor, diagnostic, consultationDate);
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return consultation;
	}
	
	public ObservableList<Consultation> getConsultations()
	{
		ObservableList<Consultation> consultationList = FXCollections.observableArrayList();
		ObservableList<HospitalizedPatient> hospitalizedPatients = getHospitalizedPatients();
		for (HospitalizedPatient hospitalizedPatient:hospitalizedPatients)
		{
			String sqlQuery = "SELECT TOP 1 * FROM Consultations C "  
				  		+ "INNER JOIN Doctors Dr on C.doctor_Id = Dr.doctor_Id " 
				  		+ "INNER JOIN Diagnostics D ON C.diag_Id = D.diag_Id "
				  		+ "INNER JOIN Diag_Category DC ON D.category_Id = DC.category_Id "
				  		+ "WHERE C.patient_Id = " + hospitalizedPatient.getPatientId() + " "
				  		+ "ORDER BY C.consultation_date DESC";
			try 
			{
				ResultSet resultSet = mStatement.executeQuery(sqlQuery);
				if(resultSet.next())
				{
					consultationList.add(parseResultSetForConsultation(resultSet, hospitalizedPatient));
				}
				else
				{
					consultationList.add(new Consultation(hospitalizedPatient));
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return consultationList;
	}
	
	private Consultation parseResultSetForConsultation(ResultSet resultSet, HospitalizedPatient hospitalizedPatient) throws SQLException
	{
		int consultationId = resultSet.getInt("consultation_Id");
		//HospitalizedPatient hospitalizedPatient = parseResultSetForHospitalizedPatient(resultSet);
		Doctor doctor = parseResultSetForDoctor(resultSet);
		Diagnostic diagnostic = parseResultSetForDiagnostic(resultSet);
		String consultationDate = resultSet.getString("consultation_date");
		return new Consultation(consultationId, hospitalizedPatient, doctor, diagnostic, consultationDate);
	}
	
	public ObservableList<HospitalizationPeriod> getHospitalizationPeriods(Patient patient)
	{
		ObservableList<HospitalizationPeriod> hospitalizationPeriods = FXCollections.observableArrayList();
		
		String sqlQuery = "SELECT * FROM Hospitalization_Records "
						+ "WHERE patient_Id = " + patient.getPatientId();
		try
		{
			ResultSet resultSet = mStatement.executeQuery(sqlQuery);
			while(resultSet.next())
			{
				hospitalizationPeriods.add(parseResultSetForHospitalizationPeriod(resultSet));
			}
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return hospitalizationPeriods;
	}
	private HospitalizationPeriod parseResultSetForHospitalizationPeriod(ResultSet resultSet) throws SQLException
	{
		int hospitalizationId = resultSet.getInt("hospitalization_Id");
		int patientId = resultSet.getInt("patient_Id");
		String admittanceDate = resultSet.getString("admittance_date");
		String dischargeDate = resultSet.getString("discharge_date");		
		return new HospitalizationPeriod(hospitalizationId, patientId, admittanceDate, dischargeDate);
	}
}
