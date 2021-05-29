package com.sparkx.damsy.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparkx.damsy.models.User;
import com.sparkx.damsy.repository.UserRepository;
import com.sparkx.damsy.service.UserService;
import com.sparkx.damsy.utils.Http;

@WebServlet(name = "UserRegisterController")
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

        String jsonPayload = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(jsonPayload);
        User user = UserService.createUserFromJson(jsonPayload);
        
        if (user != null){
            UserRepository.insertIntoUser(user);
            Http.outputResponse(resp, null, HttpServletResponse.SC_OK);
            return;
        }
        Http.outputResponse(resp, null,  HttpServletResponse.SC_BAD_REQUEST);
    }
}
