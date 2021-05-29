package com.sparkx.damsy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sparkx.damsy.connections.DBConnectionPool;
// import com.sparkx.damsy.connections.Database;
import com.sparkx.damsy.models.User;

public class UserRepository {

    private static String SQL_INSERT_QUERY = "Insert into user(username,password,first_name,last_name,hospital_id,role) values(?,?,?,?,?,?)";
    
    /**
     * Register User in Database
     * @param user
     */
    public static boolean insertIntoUser(User user) {

        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {
            // Connection connection = Database.openConnection();
            connection = DBConnectionPool.getInstance().getConnection();
            // PreparedStatement statement;
            statement = connection.prepareStatement(SQL_INSERT_QUERY);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getHospitalID());
            statement.setString(6, user.getRole().toString());

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

        // if unsert into is successful return true
        if (result > 0){
            return true;
        }else{
            return false;
        }
    }
}
