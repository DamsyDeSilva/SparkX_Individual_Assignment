package com.sparkx.damsy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sparkx.damsy.models.Hospital;
import com.sparkx.damsy.models.Patient;
import com.sparkx.damsy.repository.HospitalRepository;

public class PatientService {

    /**
     * Get the Hospital object if there's a avilable hospital
     * or return the String "NO BEDS ARE AVAILABLE" 
     * @param patient
     * @return
     */
    public static Object getAvailbleHospital(Patient patient) {

        HashMap<Hospital, Double> hospitalDistHashMap = new HashMap<Hospital, Double>();
        ArrayList<Hospital> hospitalList = HospitalRepository.getHospitalList();

        for (int i = 0; i < hospitalList.size(); i++) {
            Hospital hospital = hospitalList.get(i);

            if (hospital.getAvailBeds() > 0) {
                double distance = HospitalService.getDistanceToPatient(hospital, patient);
                hospitalDistHashMap.put(hospital, distance);
            }
        }

        if(hospitalDistHashMap.size() == 0){
            return "NO BEDS ARE AVAILABLE";
        }

        Map.Entry<Hospital, Double> minDist = null;
        for (Map.Entry<Hospital, Double> entry : hospitalDistHashMap.entrySet()) {
            if (minDist.getValue() > entry.getValue()|| minDist == null) {
                minDist = entry;
            }
        }
        // return the hospital object
        return minDist.getKey();
    }
  
    
}
