package com.sparkx.damsy.models;

public class User {

    private String userName;    
    private String firstName;
    private String lastName;
    private String role;
    
    

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
    public User(String userName, String role) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
 
}
