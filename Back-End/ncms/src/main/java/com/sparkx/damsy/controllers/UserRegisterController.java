package com.sparkx.damsy.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparkx.damsy.enums.Role;
import com.sparkx.damsy.models.User;
import com.sparkx.damsy.repository.UserRepository;
import com.sparkx.damsy.utils.Http;
import com.sparkx.damsy.utils.JsonFunctions;

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
        User user = (User) JsonFunctions.jsonDeserialize(jsonPayload, "user");
        
        if (user == null){
            Http.outputResponse(resp, "Bad request",  HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!user.getUserName().isEmpty() && !user.getFirstName().isEmpty() && !user.getPassword().isEmpty()) {
            if (user.getRole() == Role.USER || user.getRole() == Role.DOCTOR || user.getRole() == Role.MOH) {
                if (UserRepository.insertIntoUser(user)){
                    Http.outputResponse(resp, "User Created", HttpServletResponse.SC_CREATED);
                    return;
                }else{
                    Http.outputResponse(resp, "Data insertion failed", HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                
            } else {
                Http.outputResponse(resp, "Invalid Role Type", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }else{
            Http.outputResponse(resp, "Invalid Data", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }
}
