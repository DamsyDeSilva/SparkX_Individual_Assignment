package com.sparkx.damsy.controllers;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparkx.damsy.repository.UserRepository;
import com.sparkx.damsy.utils.Http;


@WebServlet(name = "UserLogin", value = "/login")
public class UserLoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        if (req.getParameter("username").isEmpty()) {
            Http.outputResponse(resp, "username is required", HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        if (req.getParameter("password").isEmpty()) {
            Http.outputResponse(resp, "password is required", HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (UserRepository.validateUserLogin(req.getParameter("username"), req.getParameter("password"))){
            Http.outputResponse(resp, "Login Success", HttpServletResponse.SC_OK);
            return;
        }else{
            Http.outputResponse(resp, "Invalid login", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

    }
    
    
}
