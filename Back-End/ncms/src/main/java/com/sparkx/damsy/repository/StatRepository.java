package com.sparkx.damsy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import com.sparkx.damsy.connections.DBConnectionPool;

import org.apache.log4j.Logger;

public class StatRepository {
    private static String GET_DISTRICT_LEVEL_COUNTS_QUERY = "SELECT district, COUNT(*) as count FROM patient WHERE discharge_date is null GROUP BY district;";
    private static String GET_ALL_PATIENT_COUNTS_QUERY = "SELECT COUNT(*) as count FROM patient;";
    private static String GET_DAILY_PATIENT_COUNTS_QUERY = "SELECT COUNT(*) as count FROM patient WHERE admit_date = ?;";

    static Logger logger = Logger.getLogger(StatRepository.class);

    /**
     * District level patient counts
     * @return
     */
    public static Map<String,Integer>  getDistrictStatsFromDB(){
        
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement statement = null;

        Map<String, Integer> districtResultMap = new HashMap<>();
    
        try {
            connection = DBConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_DISTRICT_LEVEL_COUNTS_QUERY);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                districtResultMap.put(resultSet.getString("district"), resultSet.getInt("count"));
            }

            resultSet = statement.executeQuery();

        } catch (Exception exception) {
            logger.error(exception.getMessage());
            System.out.println(exception);
        }
        finally
        {
            DBConnectionPool.getInstance().close(statement);
            DBConnectionPool.getInstance().close(connection);
        }

        return districtResultMap; 
    }
    
    /**
     * COuntry level overall patient count
     * @return
     */
    public static int getAllPatientCountFromDB(){
        
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement statement = null;

        int count = 0;
        
        try {
            connection = DBConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ALL_PATIENT_COUNTS_QUERY);
            
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


    
    /**
     * Patients admits on a specific day
     * @return
     */
    public static int getDailyPatientCountFromDB(Date requiredDate){

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement statement = null;

        int count = 0;
        
        try {
            connection = DBConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_DAILY_PATIENT_COUNTS_QUERY);
            statement.setObject(1, requiredDate);

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


    
}
