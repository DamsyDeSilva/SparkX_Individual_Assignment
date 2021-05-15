package com.sparkx.damsy.models;

import com.sparkx.damsy.enums.Role;

public class Doctor extends User{

    /**
     * 
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     * @param hospitalID
     */
    public Doctor(String userName, String password, String firstName, String lastName, String hospitalID) {
        super(userName, password, firstName, lastName, hospitalID, Role.DOCTOR);
    } 

    /**
     * 
     * @param username
     */
    public Doctor(String username) {
        super(username, Role.DOCTOR);
    }
}
