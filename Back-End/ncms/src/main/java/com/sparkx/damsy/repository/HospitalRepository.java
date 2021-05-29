package com.sparkx.damsy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sparkx.damsy.connections.DBConnectionPool;
import com.sparkx.damsy.models.Hospital;

public class HospitalRepository {

    private static String GET_HOSPITAL_BY_ID_QUERY = "SELECT * FROM hospital WHERE id = ? LIMIT 1";

    /**
     * load hospital data fro database according to hospital id
     * @param hospital
     * @return
     */
    public static Hospital loadModelFromDB(String id) {

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
                hospital.setLocationY(resultSet.getInt("location_x"));
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
}
