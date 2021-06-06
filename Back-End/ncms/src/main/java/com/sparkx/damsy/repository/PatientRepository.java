package com.sparkx.damsy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sparkx.damsy.connections.DBConnectionPool;
import com.sparkx.damsy.models.Patient;

public class PatientRepository {

    private static String INITIAL_INSERT_PATIENT_QUERY = "INSERT INTO patient(id, first_name, last_name, district, location_x, location_y, gender, contact, email, age) values(?,?,?,?,?,?,?.?,?,?)";
    private static String UPDATE_HOSPITAL_BED_DETAILS_QUERY = "UPDATE patient SET hospital_id = ?, bed_no = ?  WHERE id = ?";
    
    /**
     * Inital insertio of Patient data without hospital bed or queue deatils
     * @param patient
     * @return
     */
    public static boolean initalInsertIntoPatient(Patient patient) {

        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {

            connection = DBConnectionPool.getInstance().getConnection();

            statement = connection.prepareStatement(INITIAL_INSERT_PATIENT_QUERY);
            statement.setString(1, patient.getId());
            statement.setString(2, patient.getFirstName());
            statement.setString(3, patient.getLastName());
            statement.setString(4, patient.getDistrict());
            statement.setInt(5, patient.getLocationX());
            statement.setInt(6, patient.getLocationY());
            statement.setString(7, patient.getGender());
            statement.setString(8, patient.getContact());
            statement.setString(9, patient.getEmail());
            statement.setInt(10, patient.getAge());

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
