package com.sparkx.damsy.models;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sparkx.damsy.connections.Database;
import com.sparkx.damsy.enums.Role;

public class User {

    private String userName;   
    private String password; 
    private String firstName;
    private String lastName;
    private String hospitalID;
    private Role role;
    

    public User() {
    }


    /**
     * Create User only with username and role
     * @param userName
     * @param role
     */
    public User(String userName, Role role) {
        this.userName = userName;
        this.role = role;
    }

    /**
     * Create user without a role
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     */
    public User(String userName, String password, String firstName, String lastName, String hospitalID) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hospitalID = hospitalID;
    }

    /**
     * Create user with all atrributes
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     * @param role
     */
    public User(String userName, String password, String firstName, String lastName, String hospitalID, Role role) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hospitalID = hospitalID;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }
    
 
    /**
     * Register User in Database
     * @param user
     */
    public void registerUser() {

        String SQL_INSERT_QUERY = "Insert into user(username,password,first_name,last_name,hospital_id,role) values(?,?,?,?,?,?)";

        try {
            Connection connection = Database.openConnection();
            PreparedStatement statement;
            statement = connection.prepareStatement(SQL_INSERT_QUERY);
            statement.setString(1, this.userName);
            statement.setString(2, this.password);
            statement.setString(3, this.firstName);
            statement.setString(4, this.lastName);
            statement.setString(5, this.hospitalID);
            statement.setString(6, this.role.toString());

            System.out.println(statement);

            statement.executeUpdate();
            connection.close();

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
    
}
