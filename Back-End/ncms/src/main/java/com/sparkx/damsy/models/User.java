package com.sparkx.damsy.models;

import com.sparkx.damsy.enums.Role;

public class User {

    private String username;   
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
    public User(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    /**
     * Create user without a role
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     */
    public User(String username, String password, String firstName, String lastName, String hospitalID) {
        this.username = username;
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
    public User(String username, String password, String firstName, String lastName, String hospitalID, Role role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hospitalID = hospitalID;
        this.role = role;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
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
}
