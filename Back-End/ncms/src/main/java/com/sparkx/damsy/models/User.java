package com.sparkx.damsy.models;

import com.sparkx.damsy.enums.Role;

public class User {

    private String userName;    
    private String firstName;
    private String lastName;
    private Role role;
    

    public User() {
    }

    /**
     * 
     * @param userName
     */
    public User(String userName) {
        this.userName = userName;
    }

    /**
     * 
     * @param userName
     * @param role
     */
    public User(String userName, Role role) {
        this.userName = userName;
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
 
}
