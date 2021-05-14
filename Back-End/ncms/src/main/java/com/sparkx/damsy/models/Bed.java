package com.sparkx.damsy.models;

public class Bed {
    
    private int id;
    private String hospitalId;
    private String patientId;

    /**
     * 
     * @param id
     * @param hospitalId
     */
    public Bed(int id, String hospitalId) {
        this.id = id;
        this.hospitalId = hospitalId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

}
