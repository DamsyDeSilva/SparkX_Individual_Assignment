package com.sparkx.damsy.models;

public class PatientQueue {
    private int id;
    private String patientId;
    
    public PatientQueue(int id, String patientId) {
        this.id = id;
        this.patientId = patientId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
}
