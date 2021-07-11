package com.sparkx.damsy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

import com.sparkx.damsy.connections.DBConnectionPool;
import com.sparkx.damsy.enums.Role;
// import com.sparkx.damsy.connections.Database;
import com.sparkx.damsy.models.User;

import org.apache.log4j.Logger;

public class UserRepository {

    private static String SQL_INSERT_QUERY = "Insert into user(username,password,first_name,last_name,hospital_id,role) values(?,?,?,?,?,?)";
    private static String LOGIN_VALIDATE_QUERY = "SELECT Count(*) as count, role FROM user WHERE username=? and password=? ";
    private static String GET_USER_BY_USERNAME_QUERY = "SELECT * FROM user WHERE username=? LIMIT 1";

    static Logger logger = Logger.getLogger(UserRepository.class);

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
            statement.setString(2, Base64.getEncoder().encodeToString(user.getPassword().getBytes()));
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getHospitalID());
            statement.setString(6, user.getRole().toString());

            System.out.println(statement);

            result = statement.executeUpdate();

        } catch (Exception exception) {
            logger.error(exception.getMessage());
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
     * Validate user using username and password
     * @param username
     * @param password
     * @return
     */
    public static Role validateUserLogin(String username, String password) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        Role role = null;

        try {
            
            connection = DBConnectionPool.getInstance().getConnection();
           
            statement = connection.prepareStatement(LOGIN_VALIDATE_QUERY);
            statement.setString(1, username);
            statement.setString(2, Base64.getEncoder().encodeToString(password.getBytes()));

            System.out.println(statement);

            resultSet = statement.executeQuery();
            resultSet.next();
            count = resultSet.getInt("count");
            role = Role.valueOf(resultSet.getString("role")); 
            
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            System.out.println(exception);
        }
        finally
        {
            DBConnectionPool.getInstance().close(statement);
            DBConnectionPool.getInstance().close(connection);
        }

        // count = 1 when there exist only one matching pair of username and password 
        if (count == 1){
            return role;
        }else{
            return null;
        }
    }

    /**
     *  Load User data when username is given
     * @param userName
     * @return
     */
    public static User LoadUserFromDB(String userName){

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        User user = new User();
        user.setUserName(userName);

        try {
            
            connection = DBConnectionPool.getInstance().getConnection();
            
            statement = connection.prepareStatement(GET_USER_BY_USERNAME_QUERY);
            statement.setString(1, userName);

            System.out.println(statement);

            resultSet = statement.executeQuery();
            // resultSet.next();
            while (resultSet.next()) {
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setHospitalID(resultSet.getString("hospital_id"));
                user.setRole((Role.valueOf(resultSet.getString("role"))));
            }

            return user;
            
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            System.out.println(exception);
        }
        finally
        {
            DBConnectionPool.getInstance().close(statement);
            DBConnectionPool.getInstance().close(connection);
        }

        return null;  
    }
}
