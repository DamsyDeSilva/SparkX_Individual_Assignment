package com.sparkx.damsy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sparkx.damsy.connections.DBConnectionPool;
import com.sparkx.damsy.models.Hospital;

public class HospitalRepository {

    private static String GET_HOSPITAL_BY_ID_QUERY = "SELECT * FROM hospital WHERE id = ? LIMIT 1";
    private static String INSERT_HOSPITAL_QUERY = "Insert into hospital(id, name, district, location_x, location_y, build_date, avail_beds) values(?,?,?,?,?,?,?)";
    private static String COUNT_ALL_HOSPITAL_QUERY = "SELECT COUNT(*) FROM hospital";

    /**
     * load hospital data fro database according to hospital id
     * @param hospital
     * @return
     */
    public static Hospital loadHospitalFromDB(String id) {

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement statement = null;
        Hospital hospital = new Hospital(id);

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_HOSPITAL_BY_ID_QUERY);
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                hospital.setName(resultSet.getString("name"));
                hospital.setDistrict(resultSet.getString("district"));
                hospital.setLocationX(resultSet.getInt("location_x"));
                hospital.setLocationY(resultSet.getInt("location_y"));
                hospital.setBuildDate(resultSet.getDate("build_date"));
                hospital.setAvailBeds(resultSet.getInt("avail_beds"));
            }

            return hospital;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnectionPool.getInstance().close(resultSet);
            DBConnectionPool.getInstance().close(connection);
            DBConnectionPool.getInstance().close(connection);
        }

        return null;
    }

    /**
     * Insert hospital to a database
     * @param user
     */
    public static boolean insertIntoHospital(Hospital hospital) {

        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {

            connection = DBConnectionPool.getInstance().getConnection();

            statement = connection.prepareStatement(INSERT_HOSPITAL_QUERY);
            statement.setString(1, hospital.getId());
            statement.setString(2, hospital.getName());
            statement.setString(3, hospital.getDistrict());
            statement.setInt(4, hospital.getLocationX());
            statement.setInt(5, hospital.getLocationY());
            statement.setObject(6, hospital.getBuildDate());
            statement.setInt(7, hospital.getAvailBeds());

            System.out.println(statement);

            result = statement.executeUpdate();
            
        } catch (Exception exception) {
            System.out.println(exception);
        }
        finally
        {
            DBConnectionPool.getInstance().close(statement);
            DBConnectionPool.getInstance().close(connection);
        }

        // if insert into is successful return true
        if (result > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Return number of Hospitals in the Database
     * @return
     */
    public static int getCountHospital(){
        
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement statement = null;

        int count = 0;
        
        try {
            connection = DBConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(COUNT_ALL_HOSPITAL_QUERY);
            // result = statement.executeUpdate();
            resultSet = statement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);

        } catch (Exception exception) {
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
