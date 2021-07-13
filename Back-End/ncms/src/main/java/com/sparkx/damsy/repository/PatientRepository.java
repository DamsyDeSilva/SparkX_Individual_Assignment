package com.sparkx.damsy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import com.sparkx.damsy.connections.DBConnectionPool;
import com.sparkx.damsy.models.Patient;

import org.apache.log4j.Logger;

public class PatientRepository {

    private static String INSERT_PATIENT_WITH_HOSP_BED_QUERY = "INSERT INTO patient(id, first_name, last_name, district, location_x, location_y, gender, contact, email, age, hospital_id, bed_no) values(?,?,?,?,?,?,?,?,?,?,?,?);";
    private static String UPDATE_HOSPITAL_BED_DETAILS_QUERY = "UPDATE patient SET hospital_id = ?, bed_no = ?  WHERE id = ?;";
    private static String DECREMENT_OF_AVAIL_BEDS_QUERY = "UPDATE hospital SET avail_beds = avail_beds-1 WHERE id = ? AND avail_beds > 0;";
    private static String INSERT_PATIENT_ADDMISSION = "UPDATE patient SET severity_level = ?, admit_date = ?, admitted_by = ? WHERE id = ?;";
    private static String INSERT_PATIENT_DISCHARGE = "UPDATE patient SET discharge_date = ?, discharged_by = ? WHERE id = ?;";
    private static String GET_PATIENTS_TOBE_ADMITTED = "SELECT * FROM patient  WHERE admit_date is null;";

    static Logger logger = Logger.getLogger(PatientRepository.class);

    /**
     * Inital insertio of Patient data without hospital bed or queue deatils
     * @param patient
     * @return
     */
    public static boolean insertIntoPatientandUpdateHospital(Patient patient) {

        Connection connection = null;
        PreparedStatement patientStatement = null;
        PreparedStatement hospitalStatement = null;
        int patietResult = 0;
        int hospitalResult = 0;

        try {

            connection = DBConnectionPool.getInstance().getConnection();

            patientStatement = connection.prepareStatement(INSERT_PATIENT_WITH_HOSP_BED_QUERY);
            hospitalStatement = connection.prepareStatement(DECREMENT_OF_AVAIL_BEDS_QUERY);

            patientStatement.setString(1, patient.getId());
            patientStatement.setString(2, patient.getFirstName());
            patientStatement.setString(3, patient.getLastName());
            patientStatement.setString(4, patient.getDistrict());
            patientStatement.setInt(5, patient.getLocationX());
            patientStatement.setInt(6, patient.getLocationY());
            patientStatement.setString(7, patient.getGender());
            patientStatement.setString(8, patient.getContact());
            patientStatement.setString(9, patient.getEmail());
            patientStatement.setInt(10, patient.getAge());
            patientStatement.setString(11, patient.getHospitalId());
            patientStatement.setInt(12, patient.getBedNo());


            hospitalStatement.setString(1, patient.getHospitalId());

            System.out.println(patientStatement);
            patietResult = patientStatement.executeUpdate();

            System.out.println(hospitalStatement);
            hospitalResult = hospitalStatement.executeUpdate();

        } catch (Exception exception) {
            logger.error(exception.getMessage());
            System.out.println(exception);
        } finally {
            DBConnectionPool.getInstance().close(patientStatement);
            DBConnectionPool.getInstance().close(hospitalStatement);
            DBConnectionPool.getInstance().close(connection);
        }

        // if insert into is successful return true
        if (hospitalResult > 0 && patietResult > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update Patient deatils with hospital and bed number
     * @param patient
     * @return
     */
    public static boolean insertHospitalBed(Patient patient) {

        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {

            connection = DBConnectionPool.getInstance().getConnection();

            statement = connection.prepareStatement(UPDATE_HOSPITAL_BED_DETAILS_QUERY);
            statement.setString(1, patient.getHospitalId());
            statement.setInt(2, patient.getBedNo());
            statement.setString(3, patient.getId());
            
            System.out.println(statement);
            result = statement.executeUpdate();

        } catch (Exception exception) {
            logger.error(exception.getMessage());
            System.out.println(exception);
        } finally {
            DBConnectionPool.getInstance().close(statement);
            DBConnectionPool.getInstance().close(connection);
        }

        // if insert into is successful return true
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Insert patient admissions to Database
     * @param patientId
     * @param doctorUserName
     * @param severityLevel
     * @param admitDate
     * @return
     */
    public static boolean insertAdmissionToDB(String patientId, String doctorUserName, String severityLevel, Date admitDate) {

        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {

            connection = DBConnectionPool.getInstance().getConnection();

            statement = connection.prepareStatement(INSERT_PATIENT_ADDMISSION);
            statement.setString(1, severityLevel);
            statement.setObject(2, admitDate);
            statement.setString(3, doctorUserName);
            statement.setString(4, patientId);
            
            System.out.println(statement);
            result = statement.executeUpdate();

        } catch (Exception exception) {
            logger.error(exception.getMessage());
            System.out.println(exception);
        } finally {
            DBConnectionPool.getInstance().close(statement);
            DBConnectionPool.getInstance().close(connection);
        }

        // if insert into is successful return true
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    
    /**
     * Insert patient discharge details to Database
     * @param patientId
     * @param doctorUserName
     * @param dischargeDate
     * @return
     */
    public static boolean insertDischargeToDB(String patientId, String doctorUserName, Date dischargeDate) {

        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {

            connection = DBConnectionPool.getInstance().getConnection();

            statement = connection.prepareStatement(INSERT_PATIENT_DISCHARGE);
            statement.setObject(1, dischargeDate);
            statement.setString(2, doctorUserName);
            statement.setString(3, patientId);
            
            System.out.println(statement);
            result = statement.executeUpdate();

        } catch (Exception exception) {
            logger.error(exception.getMessage());
            System.out.println(exception);
        } finally {
            DBConnectionPool.getInstance().close(statement);
            DBConnectionPool.getInstance().close(connection);
        }

        // if insert into is successful return true
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 
     * @return
     */
    public static ArrayList<Patient> getPatientsToBeAdmitted(){
        
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement statement = null;

        ArrayList<Patient> patientAdmitList = new ArrayList<Patient>();

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_PATIENTS_TOBE_ADMITTED);
            resultSet = statement.executeQuery();

            Patient patient;
            while (resultSet.next()) {
                patient = new Patient(resultSet.getString("id"));
                patient.setFirstName(resultSet.getString("first_name"));
                patient.setLastName(resultSet.getString("last_name"));
                patient.setDistrict(resultSet.getString("district"));
                patient.setLocationX(resultSet.getInt("location_x"));
                patient.setLocationY(resultSet.getInt("location_y"));
                patient.setGender(resultSet.getString("gender"));
                patient.setHospitalId(resultSet.getString("hospital_id"));
                patient.setBedNo(resultSet.getInt("bed_no"));
                patientAdmitList.add(patient);
            }

        } catch (Exception exception) {
            logger.error(exception.getMessage());
            System.out.println(exception);
        }
        finally
        {
            DBConnectionPool.getInstance().close(statement);
            DBConnectionPool.getInstance().close(connection);
        }

        return patientAdmitList;
    } 
}
