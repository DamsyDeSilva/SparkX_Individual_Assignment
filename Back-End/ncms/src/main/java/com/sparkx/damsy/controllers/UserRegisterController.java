package com.sparkx.damsy.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparkx.damsy.models.User;
import com.sparkx.damsy.repository.UserRepository;
import com.sparkx.damsy.service.UserService;
import com.sparkx.damsy.utils.Http;

public class UserRegisterController extends HttpServlet {
    
    String message = "";
    public void init() {
        message = "This is the User Register Servlet";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(reqBody);
        int resCode;
        User user = UserService.createUser(reqBody);
        
        if (user != null){
            UserRepository.insertIntoUser(user);
            resCode = HttpServletResponse.SC_OK;
            Http.outputResponse(resp, null, resCode);
            return;
        }
        resCode = HttpServletResponse.SC_BAD_REQUEST;
        Http.outputResponse(resp, null, resCode);
    }
}
