package com.sparkx.damsy.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sparkx.damsy.enums.Role;
import com.sparkx.damsy.models.User;

public class UserRegisterController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(reqBody);
        int resCode;
        User user = createUser(reqBody);
        
        if (user != null){
            user.registerUser();
            resCode = HttpServletResponse.SC_OK;
            this.outputResponse(resp, null, resCode);
            return;
        }
        resCode = HttpServletResponse.SC_BAD_REQUEST;
        this.outputResponse(resp, null, resCode);
    }

    /**
     * Create User from JsonPayload
     * 
     * @param jsonPayload
     * @return
     */
    public User createUser(String jsonPayload) {
        if (jsonPayload == null)
            return null;

        Gson gson = new Gson();
        try {
            User user = (User) gson.fromJson(jsonPayload, User.class);
            if (user.getUserName() != null || user.getFirstName() != null || user.getPassword() != null) {
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

    /**
     * Output response for http
     * 
     * @param response
     * @param payload
     * @param status
     */
    private void outputResponse(HttpServletResponse response, String payload, int status) {

        response.setHeader("Content-Type", "application/json");
        try {
            response.setStatus(status);
            if (payload != null) {
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(payload.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
