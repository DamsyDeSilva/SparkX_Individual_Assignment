package com.sparkx.damsy.models;

import com.sparkx.damsy.enums.Role;

public class Doctor extends User{

    private String hospitalId;

    /**
     * 
     * @param username
     * @param hospitalId
     */
    public Doctor(String username, String hospitalId) {
        super(username, Role.DOCTOR);  
        this.hospitalId = hospitalId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
 
}
