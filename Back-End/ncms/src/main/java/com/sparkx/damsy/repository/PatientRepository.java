package com.sparkx.damsy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sparkx.damsy.connections.DBConnectionPool;
import com.sparkx.damsy.models.Patient;

public class PatientRepository {

    private static String INITIAL_INSERT_PATIENT_QUERY = "INSERT INTO patient(id, first_name, last_name, district, location_x, location_y, gender, contact, email, age, hospital_id, bed_no) values(?,?,?,?,?,?,?,?,?,?,?,?);";
    private static String UPDATE_HOSPITAL_BED_DETAILS_QUERY = "UPDATE patient SET hospital_id = ?, bed_no = ?  WHERE id = ?;";
    private static String DECREMENT_OF_AVAIL_BEDS_QUERY = "UPDATE hospital SET avail_beds = avail_beds-1 WHERE id = ? AND avail_beds > 0;";

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

            patientStatement = connection.prepareStatement(INITIAL_INSERT_PATIENT_QUERY);
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
}
