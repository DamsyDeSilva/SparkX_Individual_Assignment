package com.sparkx.damsy.models;

public class Doctor extends User{

    private String hospitalId;

    /**
     * 
     * @param username
     * @param hospitalId
     */
    public Doctor(String username, String hospitalId) {
        super(username, "Doctor");  
        this.hospitalId = hospitalId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
 
}
