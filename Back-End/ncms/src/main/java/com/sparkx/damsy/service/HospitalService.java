package com.sparkx.damsy.service;

import com.sparkx.damsy.models.Hospital;
import com.sparkx.damsy.models.Patient;

public class HospitalService {

    /**
     * Method to get distant to a patient from a hospital
     * 
     * @param hospital
     * @param patient
     * @return
     */
    public static double getDistanceToPatient(Hospital hospital, Patient patient) {

        int xDiff = Math.abs(hospital.getLocationX() - patient.getLocationX());
        int yDiff = Math.abs(hospital.getLocationY() - patient.getLocationY());

        double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));

        return distance;
    }
    
}
