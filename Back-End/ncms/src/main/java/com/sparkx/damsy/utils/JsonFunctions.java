package com.sparkx.damsy.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sparkx.damsy.models.Hospital;
import com.sparkx.damsy.models.Patient;
import com.sparkx.damsy.models.User;

public class JsonFunctions {

    /**
     * Create json string from any object
     * @param hospital
     * @return
     */
    public static String jsonSerialize(Object object) {
        if (object == null) return null;
        Gson gson = new Gson();
        String jsonString = null;
        try{
            jsonString = gson.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    /**
     * Create the corresponding model object from jsonPayload
     * @param jsonPayload
     * @param modelType
     * @return
     */
    public static Object jsonDeserialize(String jsonPayload, String modelType) {
        if (jsonPayload == null)
            return null;

        Gson gson = new Gson();
        
        try {
            switch (modelType) {
                case "user":
                    User user = (User) gson.fromJson(jsonPayload, User.class);
                    return user;
        
                case "hospital":
                    Hospital hospital = (Hospital) gson.fromJson(jsonPayload, Hospital.class);
                    return hospital;

                case "patient":
                    Patient patient = (Patient) gson.fromJson(jsonPayload, Patient.class);
                    return patient;
                    
                default:
                    break;
            }
            
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
