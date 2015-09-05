package com.database.internarispital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.database.internarispital.entities.Consultation;
import com.database.internarispital.entities.DiagCategory;
import com.database.internarispital.entities.Diagnostic;
import com.database.internarispital.entities.HospitalizationPeriod;
import com.database.internarispital.entities.accounts.Account;
import com.database.internarispital.entities.accounts.AccountTypes;
import com.database.internarispital.entities.doctors.Doctor;
import com.database.internarispital.entities.doctors.DoctorData;
import com.database.internarispital.entities.facilities.Bed;
import com.database.internarispital.entities.facilities.Section;
import com.database.internarispital.entities.facilities.Ward;
import com.database.internarispital.entities.patients.HospitalizedPatient;
import com.database.internarispital.entities.patients.Patient;
import com.database.internarispital.entities.patients.PatientData;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class DataBase 
{
	//Database credentials
	private static final String SERVER_NAME = "localhost";
	private static final String DATABASE_NAME = "InternariSpital";
	private static final int PORT_NUMBER = 1433;
	private static final String USER = "client";
	private static final String PASSWORD = "conexiune";
	
	private static final String INSERT_ACCOUNT			= "INSERT INTO ACCOUNTS 		VALUES (?, ?, ?)";
	private static final String INSERT_DOCTOR 			= "INSERT INTO Doctors 			VALUES (?, ?, ?, ?, ?, ?)";
	private static final String INSERT_PATIENT 			= "INSERT INTO Patients 		VALUES (?, ?, ?, ?)";
	private static final String INSERT_CONSULTATION 	= "INSERT INTO Consultations 	VALUES( ?, ?, ?, ?)";
	private static final String INSERT_SECTION 			= "INSERT INTO Sections 		VALUES (?)";
	private static final String INSERT_WARD 			= "INSERT INTO WARDS 			VALUES (?, ?)";
	private static final String INSERT_BED				= "INSERT INTO BEDS 			VALUES (?, ?, ?)";
	
	private static final int INVALID_ID = -1;
	
	private SQLServerDataSource mDataSource;
	private Connection mConnection;
	private Statement mStatement; 
	private PreparedStatement mInsertAccountStatement;
	private PreparedStatement mInsertDoctorStatement;
	private PreparedStatement mInsertPatientStatement;
	private PreparedStatement mInsertConsultationStatement;
	private PreparedStatement mInsertSectionStatement;
	private PreparedStatement mInsertWardStatement;
	private PreparedStatement mInsertBedStatement;
	private List<Statement> mStatementsToClose;
	
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
    		initStatements();
		}
		catch(SQLException e1)
		{
			finalize();
        	throw e1;
		}
	}
	
	private void initStatements() throws SQLException
	{
		mStatementsToClose = new ArrayList<Statement>();
		
		mStatement = mConnection.createStatement();
		mStatementsToClose.add(mStatement);
		
		mInsertAccountStatement 		= initStatement(INSERT_ACCOUNT);
		mInsertDoctorStatement 			= initStatement(INSERT_DOCTOR);
		mInsertPatientStatement 		= initStatement(INSERT_PATIENT);
		mInsertConsultationStatement 	= initStatement(INSERT_CONSULTATION);
		mInsertSectionStatement 		= initStatement(INSERT_SECTION);
		mInsertWardStatement 			= initStatement(INSERT_WARD);
		mInsertBedStatement 			= initStatement(INSERT_BED);
	}
	
	private PreparedStatement initStatement(String sqlQuery) throws SQLException
	{
		PreparedStatement statement = mConnection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
		mStatementsToClose.add(statement);
		return statement;
	}
	
	@Override
	public void finalize()
	{
		closeStatements();
		closeConnection();	
	}
	
	private void closeStatements()
	{
		for(Statement st: mStatementsToClose)
		{
			closeStatement(st);
		}
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

	public Account login(String userName, String password)
	{
		Account account = null;
		
		try
        {   
        	String sqlQuery = "SELECT * FROM "
        			+ "Accounts A INNER JOIN AccountTypes AT "
        			+ "ON A.account_type = AT.type_id "
        			+ "WHERE A.account_name = '" + userName + "' "
        			+ "AND "
        			+ "A.account_password = '" + password + "'";
        	ResultSet resultSet = mStatement.executeQuery(sqlQuery);
        	if(resultSet.next())
        	{
        		account = parseResultSetForAccount(resultSet);
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();        	
        }
		
		return account;
	}
	
	private Account parseResultSetForAccount(ResultSet resultSet) throws SQLException
	{
		int id = resultSet.getInt("account_Id");
		String name = resultSet.getString("account_name");
		String password = resultSet.getString("account_password");
		String accountType = resultSet.getString("type_name");
		
		return new Account(id, name, password, accountType);
	}
	
	public Account insertAccount(String userName, String password, AccountTypes accountType)
	{
		Account account = null;
		try 
		{
			mInsertAccountStatement.setString(1, userName);
			mInsertAccountStatement.setString(2, password);
			mInsertAccountStatement.setInt(3, getAccountTypeIndex(accountType));

			int accountId = executeInsertAndGetKey(mInsertAccountStatement);
			account = new Account(accountId, userName, password, accountType);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return account;
	}
	
	private int getAccountTypeIndex(AccountTypes accountType)
	{
		int accountTypeIndex = INVALID_ID;
		try
        {   
        	String sqlQuery = "SELECT type_Id FROM AccountTypes "
        			+ "WHERE type_name = '" + accountType.name() + "' ";
        	ResultSet resultSet = mStatement.executeQuery(sqlQuery);
        	if(resultSet.next())
        	{
        		accountTypeIndex = resultSet.getInt("type_Id");
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();        	
        }
		
		return accountTypeIndex;
	}
	
	public Doctor insertDoctor(DoctorData doctorData)
	{
		Doctor doctor = null;
		try 
		{
			mInsertDoctorStatement.setString(1, doctorData.nameProperty().getValue());
			mInsertDoctorStatement.setString(2, doctorData.surnameProperty().getValue());
			mInsertDoctorStatement.setString(3, doctorData.gradeProperty().getValue());
			mInsertDoctorStatement.setString(4, doctorData.specialityProperty().getValue());
			mInsertDoctorStatement.setBoolean(5, true);
			mInsertDoctorStatement.setInt(6, doctorData.getAccountId());

			int doctorId = executeInsertAndGetKey(mInsertDoctorStatement);
			doctor = new Doctor(doctorId, doctorData);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return doctor;
	}
	
	public Doctor updateDoctor(Doctor doctor, DoctorData newData)
	{
		String update = "UPDATE Doctors "
				+ "SET doctor_name = '" + newData.nameProperty().getValue() + "' "
				+ ", doctor_surname = '" + newData.surnameProperty().getValue() + "' "
				+ ", doctor_grade = '" + newData.gradeProperty().getValue() + "' "
				+ ", speciality_name = '" + newData.specialityProperty().getValue() + "' "
				+ "WHERE doctor_Id = " + doctor.doctorIdProperty().getValue();
		executeUpdate(update);
		
		return getDoctor(doctor.doctorIdProperty().getValue());
	}
	
	public ObservableList<Patient> getNotHospitalizedPatients()
	{
		ObservableList<Patient> patients = FXCollections.observableArrayList();
        try
        {   
        	String sqlQuery = "SELECT * FROM Patients "
        			+ "WHERE patient_Id NOT IN (SELECT patient_Id from Hospitalizations)";
        	ResultSet resultSet = mStatement.executeQuery(sqlQuery);
        	while(resultSet.next())
        	{
        		patients.add(parseResultSetForPatient(resultSet));
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();        	
        }
        return patients;
	}
	
	public ObservableList<HospitalizedPatient> getHospitalizedPatients()
	{
		ObservableList<HospitalizedPatient> hospitalizedPatients = FXCollections.observableArrayList();
        
        try
        {   
        	String sqlQuery = "SELECT P.patient_Id, P.patient_name, P.patient_Surname, P.birth_date, B.bed_number, W.ward_number, S.section_name "
        					+ "FROM Patients AS P " 
        					+ "INNER JOIN Hospitalizations AS H ON P.patient_Id = H.patient_Id "
        					+ "INNER JOIN Beds AS B ON B.bed_Id = H.bed_Id "
    						+ "INNER JOIN Wards AS W ON W.ward_Id = B.ward_Id "
        					+ "INNER JOIN Sections AS S ON S.section_Id = W.section_Id";
        	ResultSet resultSet = mStatement.executeQuery(sqlQuery);
        	
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
		String section = resultSet.getString("section_Name");
		int wardNumber = resultSet.getInt("ward_number");
		int bedNumber = resultSet.getInt("bed_number");
		
		return new HospitalizedPatient(parseResultSetForPatient(resultSet), section, wardNumber, bedNumber);
	}
	
	private Patient parseResultSetForPatient(ResultSet resultSet) throws SQLException
	{
		int patientId = resultSet.getInt("patient_Id");
		String patientName = resultSet.getString("patient_name");
		String patientSurname = resultSet.getString("patient_surname");
		String birthDate = resultSet.getString("birth_date");
		int accountId = resultSet.getInt("account_Id");
		PatientData patientData = new PatientData(patientName, patientSurname, birthDate, accountId);
		return new Patient(patientId, patientData);
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
	public Patient insertPatient(PatientData patientData)
	{
		Patient patient = null;
		try 
		{
			mInsertPatientStatement.setString(1, patientData.firstNameProperty().getValue());
			mInsertPatientStatement.setString(2, patientData.lastNameProperty().getValue());
			mInsertPatientStatement.setString(3, patientData.birthDateProperty().getValue());
			mInsertPatientStatement.setInt(4, patientData.getAccountId());

			int patientId = executeInsertAndGetKey(mInsertPatientStatement);
			patient = new Patient(patientId, patientData);
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
		
		executeUpdate(sqlHospitalize);
		setBedOccupancy(bedId, true);
			
		hospitalizedPatient = getHospitalizedPatient(patient);
		
		return hospitalizedPatient;
	}
	
	private void createHospitalizationRecord(Patient patient)
	{
		String sqlAdmit = "INSERT INTO Hospitalization_Records VALUES("
						+ patient.getPatientId() + ", '" + getCurrentDate() + "', NULL)";
		executeUpdate(sqlAdmit);
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
				
				String sqlUpdateRecords = "UPDATE Hospitalization_Records "
						+ "SET discharge_date = '" + getCurrentDate() + "' "
						+ "WHERE patient_Id = " + patientId;
				mStatement.executeUpdate(sqlUpdateRecords);
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
		executeUpdate(sqlUpdate);
	}
	public ObservableList<Bed> getVacantBeds(int wardId)
	{
		String sqlQuery = "SELECT * FROM Beds WHERE ward_Id = " + wardId + " AND occupancy = 'FALSE'";
		ObservableList<Bed> bedsList = getBedsInternal(wardId, sqlQuery);
		return bedsList;
	}
	
	public ObservableList<Bed> getBeds(int wardId)
	{
		String sqlQuery = "SELECT * FROM Beds WHERE ward_Id = " + wardId;
		ObservableList<Bed> bedsList = getBedsInternal(wardId, sqlQuery);
		return bedsList;
	}
	
	private ObservableList<Bed> getBedsInternal(int wardId, String sqlQuery)
	{
		ObservableList<Bed> bedsList = FXCollections.observableArrayList();
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
				int wardNumber = resultSet.getInt("ward_number");
				wardsList.add(new Ward(wardId, sectionId, wardNumber));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return wardsList;
	}
	
	public Doctor getDoctor(int doctorId)
	{
		Doctor doctor = null;
		String sqlQuery = "SELECT * FROM Doctors WHERE doctor_Id = " + doctorId;
		try
		{
			ResultSet resultSet = mStatement.executeQuery(sqlQuery);
			if(resultSet.next())
			{
				doctor = parseResultSetForDoctor(resultSet);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return doctor;
	}
	
	public ObservableList<Doctor> getDoctors()
	{
		ObservableList<Doctor> doctorsList = FXCollections.observableArrayList();
		String sqlQuery = "SELECT * FROM Doctors WHERE active = 1";
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
		String grade = resultSet.getString("doctor_grade");
		String speciality = resultSet.getString("speciality_name");
		int accountId = resultSet.getInt("account_Id");
		return new Doctor(doctorId, new DoctorData(doctorName, doctorSurname, grade, speciality, accountId));
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

			int consultationId = executeInsertAndGetKey(mInsertConsultationStatement);
			consultation = new Consultation(consultationId, patient, doctor, diagnostic, consultationDate);
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

	public ObservableList<Consultation> getConsulationsForPeriod(HospitalizedPatient patient, HospitalizationPeriod period) 
	{
		ObservableList<Consultation> consultationList = FXCollections.observableArrayList();
		String sqlQuery = "SELECT * FROM Consultations C "  
		  		+ "INNER JOIN Doctors Dr on C.doctor_Id = Dr.doctor_Id " 
		  		+ "INNER JOIN Diagnostics D ON C.diag_Id = D.diag_Id "
		  		+ "INNER JOIN Diag_Category DC ON D.category_Id = DC.category_Id "
		  		+ "WHERE C.patient_Id = " + patient.getPatientId() + " " 
		  		+ "AND C.consultation_date BETWEEN '" + period.getAdmittanceDateAsString() + "' AND '" + period.getDischargeDateAsString() + "' "
		  		+ "ORDER BY C.consultation_date DESC";
		try 
		{
			ResultSet resultSet = mStatement.executeQuery(sqlQuery);
			if(resultSet.next())
			{
				consultationList.add(parseResultSetForConsultation(resultSet, patient));
			}
			else
			{
				consultationList.add(new Consultation(patient));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return consultationList;
	}

	
	private Consultation parseResultSetForConsultation(ResultSet resultSet, HospitalizedPatient hospitalizedPatient) throws SQLException
	{
		int consultationId = resultSet.getInt("consultation_Id");
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
		Date admittanceDate = resultSet.getTimestamp("admittance_date");
		Date dischargeDate = resultSet.getTimestamp("discharge_date");
		return new HospitalizationPeriod(hospitalizationId, patientId, admittanceDate, dischargeDate);
	}

	public void setDoctorInactive(Doctor doctor) 
	{
		String sqlUpdate = "UPDATE Doctors " + 
					  	   "SET active = 0 " +
						   "WHERE doctor_Id = " + doctor.doctorIdProperty().get();
		try
		{
			mStatement.executeUpdate(sqlUpdate);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public ObservableList<Consultation> getDoctorHistory(Doctor doctor)
	{
		return getDoctorHistory(doctor, "", "", false, true);
	}
	
	public ObservableList<Consultation> getDoctorHistory(Doctor doctor, String beginDate, String endDate, boolean onlyCurrentlyAdmitted, boolean showAllHistory)
	{
		ObservableList<Consultation> consultationList = FXCollections.observableArrayList();
		
		String hospitalizedPatientsQuery = onlyCurrentlyAdmitted ? "INNER JOIN Hospitalizations AS H ON P.patient_Id = H.patient_Id " : "";
		String historyPeriodQuery = showAllHistory ? "" : "AND C.consultation_date BETWEEN '" + beginDate + "' AND '" + endDate + "' ";
		String sqlQuery = "SELECT * FROM Patients P " +  hospitalizedPatientsQuery
				+ "INNER JOIN Consultations C on P.patient_Id = C.patient_Id " 
				+ "INNER JOIN Doctors Dr on C.doctor_Id = Dr.doctor_Id "
				+ "INNER JOIN Diagnostics D ON C.diag_Id = D.diag_Id "
				+ "INNER JOIN Diag_Category DC ON D.category_Id = DC.category_Id " 
				+ "WHERE Dr.doctor_Id = " + doctor.doctorIdProperty().getValue() + " " 
				+ "AND consultation_Id = (SELECT TOP 1 Cons.consultation_Id " 
										+ "FROM Consultations Cons "
										+ "WHERE C.patient_Id = Cons.patient_Id "
										+ "ORDER BY Cons.consultation_date DESC) "
				+ historyPeriodQuery 
				+ "ORDER BY C.consultation_date DESC";
		try 
		{
			ResultSet resultSet = mStatement.executeQuery(sqlQuery);
			while(resultSet.next())
			{
				consultationList.add(parseResultSetForDoctorHistory(resultSet, doctor));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return consultationList;
	}

	private Consultation parseResultSetForDoctorHistory(ResultSet resultSet, Doctor doctor) throws SQLException
	{
		int consultationId = resultSet.getInt("consultation_Id");
		Diagnostic diagnostic = parseResultSetForDiagnostic(resultSet);
		String consultationDate = resultSet.getString("consultation_date");
		HospitalizedPatient hospitalizedPatient = new HospitalizedPatient(parseResultSetForPatient(resultSet)); 
		return new Consultation(consultationId, hospitalizedPatient, doctor, diagnostic, consultationDate);
	}
	
	public Section insertSection(String sectionName)
	{
		Section section = null;
		try 
		{
			mInsertSectionStatement.setString(1, sectionName);

			int sectionId = executeInsertAndGetKey(mInsertSectionStatement);
			section = new Section(sectionId, sectionName);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return section;
	}

	public Ward insertWard(Section section, int wardNumber)
	{
		Ward ward = null;
		try 
		{
			mInsertWardStatement.setInt(1, wardNumber);
			mInsertWardStatement.setInt(2, section.sectionIdProperty().getValue());

			int wardId = executeInsertAndGetKey(mInsertWardStatement);
			ward = new Ward(wardId, section.sectionIdProperty().getValue(), wardNumber);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return ward;
	}
	
	public Bed insertBed(Ward ward, int bedNumber)
	{
		Bed bed = null;
		try 
		{
			mInsertBedStatement.setInt(1, ward.wardIdProperty().getValue());
			mInsertBedStatement.setInt(2, bedNumber);
			mInsertBedStatement.setBoolean(3, false);

			int bedId = executeInsertAndGetKey(mInsertBedStatement);
			bed = new Bed(bedId, bedNumber, ward.wardIdProperty().getValue(), false);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return bed;
	}
	
	public void removeSection(Section section)
	{
		String sqlUpdate = "DELETE FROM Sections WHERE section_Id = " + section.sectionIdProperty().getValue();
		executeUpdate(sqlUpdate);
	}
	
	public void removeWard(Ward ward)
	{
		String sqlUpdate = "DELETE FROM Wards WHERE ward_Id = " + ward.wardIdProperty().getValue();
		executeUpdate(sqlUpdate);
	}
	
	public void removeBed(Bed bed)
	{
		String sqlUpdate = "DELETE FROM Beds WHERE bed_Id = " + bed.wardIdProperty().getValue();
		executeUpdate(sqlUpdate);
	}
	
	private void executeUpdate(String update)
	{
		try
		{
			mStatement.executeUpdate(update);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private int executeInsertAndGetKey(PreparedStatement statement) throws SQLException
	{
		int keyId = INVALID_ID;
		int rowsModified = statement.executeUpdate();
		if (rowsModified == 1)
		{
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if(generatedKeys.next())
			{
				keyId = generatedKeys.getInt(1);
			}
		}
		else
		{
			throw new SQLException("No rows modified in insert.");
		}
		return keyId;
	}
}
