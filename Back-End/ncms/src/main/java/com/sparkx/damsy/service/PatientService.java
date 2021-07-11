package com.sparkx.damsy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    public static Map<String,String> getAvailbleHospital(Patient patient) {
        // Pair<Integer, String>
        HashMap<Hospital, Double> hospitalDistHashMap = new HashMap<Hospital, Double>();
        ArrayList<Hospital> hospitalList = HospitalRepository.getHospitalList();
        
        for (int i = 0; i < hospitalList.size(); i++) {
            Hospital hospital = hospitalList.get(i);

            if (hospital.getAvailBeds() > 0) {
                double distance = HospitalService.getDistanceToPatient(hospital, patient);
                // System.out.println(hospital.getId()+": "+distance + " ---" + hospital.getLocationX() + " ," + hospital.getLocationY());
                hospitalDistHashMap.put(hospital, distance);
            }
        }

        Map<String, String> result = new HashMap<>();

        if(hospitalDistHashMap.size() == 0){
            result.put("status","NO BEDS ARE AVAILABLE");
            // return "NO BEDS ARE AVAILABLE";
        }else{
            result.put("status","BED_AVAILABLE");
            // String returnHospitalId;

            Entry<Hospital, Double> min = null;
            for (Entry<Hospital, Double> entry : hospitalDistHashMap.entrySet()) {
                if (min == null || min.getValue() > entry.getValue()) {
                    min = entry;
                }
            }
            // returnHospitalId = min.getKey().getId();
            result.put("hospitalId",min.getKey().getId());
            result.put("hospitalName",min.getKey().getName());
        }

        return result;
        // return returnHospitalId;
    }
}
