package com.sparkx.damsy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sparkx.damsy.models.Patient;
import com.sparkx.damsy.models.PatientQueue;

import org.apache.log4j.Logger;

import com.sparkx.damsy.connections.DBConnectionPool;

public class QueueRepository {

    private static String COUNT_ALL_QUEUE_QUERY = "SELECT COUNT(*) FROM patient_queue";
    private static String INSERT_PATIENT_QUERY = "INSERT INTO patient(id, first_name, last_name, district, location_x, location_y, gender, contact, email, age) values(?,?,?,?,?,?,?,?,?,?);";
    private static String INSERT_QUEUE_QUERY = "INSERT INTO patient_queue(id, patient_id) values(?,?)";
    
    static Logger logger = Logger.getLogger(QueueRepository.class);
    
    public static int getCountQueue(){
        
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement statement = null;

        int count = 0;
        
        try {
            connection = DBConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(COUNT_ALL_QUEUE_QUERY);
            resultSet = statement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);

        } catch (Exception exception) {
            logger.error(exception.getMessage());
            System.out.println(exception);
        }
        finally
        {
            DBConnectionPool.getInstance().close(statement);
            DBConnectionPool.getInstance().close(connection);
        }

        return count; 
    } 

    public static boolean insertIntoQueue(PatientQueue queue, Patient patient) {

        Connection connection = null;
        PreparedStatement queueStatement = null;
        PreparedStatement patientStatement = null;
        int patientResult = 0;
        int queueResult = 0;

        try {

            connection = DBConnectionPool.getInstance().getConnection();

            patientStatement = connection.prepareStatement(INSERT_PATIENT_QUERY);
            queueStatement = connection.prepareStatement(INSERT_QUEUE_QUERY);

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

            queueStatement.setInt(1, queue.getId());
            queueStatement.setString(2, queue.getPatientId());
          

            System.out.println(queueStatement);

            patientResult = patientStatement.executeUpdate();
            queueResult = queueStatement.executeUpdate();
            
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            System.out.println(exception);
        }
        finally
        {
            DBConnectionPool.getInstance().close(patientStatement);
            DBConnectionPool.getInstance().close(queueStatement);
            DBConnectionPool.getInstance().close(connection);
        }

        // if insert into is successful return true
        if (patientResult > 0 && queueResult > 0){
            return true;
        }else{
            return false;
        }
    }
}
