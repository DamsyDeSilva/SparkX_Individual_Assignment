package com.sparkx.damsy.models;

import com.sparkx.damsy.enums.Role;

public class Moh extends User{
    
    /**
     * 
     * @param username
     */
    public Moh(String username) {
        super(username, Role.MOH);
    }
    
}
