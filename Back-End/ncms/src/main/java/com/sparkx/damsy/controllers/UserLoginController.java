package com.sparkx.damsy.controllers;

import java.io.IOException;
import java.util.stream.Collectors;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.sparkx.damsy.enums.Role;
import com.sparkx.damsy.models.User;
import com.sparkx.damsy.repository.UserRepository;
import com.sparkx.damsy.service.AuthService;
import com.sparkx.damsy.utils.Http;
import com.sparkx.damsy.utils.JsonFunctions;


@WebServlet(name = "UserLogin", value = "/login")
public class UserLoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // if (req.getParameter("username").isEmpty()) {
        //     Http.outputResponse(resp, "username is required", HttpServletResponse.SC_FORBIDDEN);
        //     return;
        // }
        // if (req.getParameter("password").isEmpty()) {
        //     Http.outputResponse(resp, "password is required", HttpServletResponse.SC_FORBIDDEN);
        //     return;
        // }
        String jsonPayload = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(jsonPayload);
        User user = (User) JsonFunctions.jsonDeserialize(jsonPayload, "user");
        
        if (user == null) {
            Http.outputResponse(resp, "password is required", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Role userRole = UserRepository.validateUserLogin(user.getUserName(), user.getPassword()) ;
        if (userRole != null){
            // User user = new User(req.getParameter("username"), userRole);
            user.setRole(userRole);
            String token = AuthService.createToken(user);
            
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("token", token);
            jsonObject.addProperty("username", user.getUserName());
            jsonObject.addProperty("role", userRole.toString());

            Http.outputResponse(resp, JsonFunctions.jsonSerialize(jsonObject), HttpServletResponse.SC_CREATED);
            return;
            // Http.outputResponse(resp, token, HttpServletResponse.SC_OK);
            // return;
        }else{
            Http.outputResponse(resp, "Invalid login", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

    }
    
    
}
