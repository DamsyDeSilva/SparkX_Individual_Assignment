package com.sparkx.damsy.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sparkx.damsy.enums.Role;
import com.sparkx.damsy.models.User;

public class UserService {
    
    /**
     * Create User from JsonPayload
     * 
     * @param jsonPayload
     * @return
     */
    public static User createUser(String jsonPayload) {
        if (jsonPayload == null)
            return null;

        Gson gson = new Gson();
        try {
            User user = (User) gson.fromJson(jsonPayload, User.class);
            if (user.getUserName() != null && user.getFirstName() != null && user.getPassword() != null) {
                if(user.getRole() == Role.USER || user.getRole() == Role.DOCTOR || user.getRole() == Role.MOH){
                    return user;
                }
                else{
                    System.out.println("Invalid User");
                }
            }
            
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
