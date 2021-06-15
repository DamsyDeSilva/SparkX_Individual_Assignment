package com.sparkx.damsy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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
    public static String getAvailbleHospital(Patient patient) {

        HashMap<String, Double> hospitalDistHashMap = new HashMap<String, Double>();
        ArrayList<Hospital> hospitalList = HospitalRepository.getHospitalList();
        
        for (int i = 0; i < hospitalList.size(); i++) {
            Hospital hospital = hospitalList.get(i);

            if (hospital.getAvailBeds() > 0) {
                double distance = HospitalService.getDistanceToPatient(hospital, patient);
                hospitalDistHashMap.put(hospital.getId(), distance);
            }
        }

        if(hospitalDistHashMap.size() == 0){
            return "NO BEDS ARE AVAILABLE";
        }

        String returnHospitalId;

        Entry<String, Double> min = null;
        for (Entry<String, Double> entry : hospitalDistHashMap.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }
        returnHospitalId = min.getKey();
  
        return returnHospitalId;
    }
}
