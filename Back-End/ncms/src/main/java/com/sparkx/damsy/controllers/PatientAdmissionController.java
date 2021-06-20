package com.sparkx.damsy.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparkx.damsy.enums.Role;
import com.sparkx.damsy.models.User;
import com.sparkx.damsy.repository.PatientRepository;
import com.sparkx.damsy.repository.UserRepository;
import com.sparkx.damsy.utils.Http;

@WebServlet(name = "patientAdmission", value = "/admits")
public class PatientAdmissionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1> Patient Admissions</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        if (req.getParameter("username").isEmpty()) {
            Http.outputResponse(resp, "username is required", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (req.getParameter("patientId").isEmpty()) {
            Http.outputResponse(resp, "Patient Id is required", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (req.getParameter("severityLevel").isEmpty()) {
            Http.outputResponse(resp, "Severity Level is required", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        User user = UserRepository.LoadUserFromDB(req.getParameter("username"));

        if (user.getFirstName() == null) {
            Http.outputResponse(resp, "Username is Inavlid", HttpServletResponse.SC_BAD_REQUEST);
            return;
        } 

        if (user.getRole() != Role.DOCTOR) {
            Http.outputResponse(resp, "Only Doctors can admits Patients", HttpServletResponse.SC_BAD_REQUEST);
            return;
        } 

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date admitDate = null;
        try {
            admitDate = formatter.parse(formatter.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
           
        if (PatientRepository.insertAdmissionToDB(req.getParameter("patientId"), user.getUserName(), req.getParameter("severityLevel"), admitDate)) {
            Http.outputResponse(resp, "Patient Admitted", HttpServletResponse.SC_OK);
            return;
        } else{
            Http.outputResponse(resp, "Patient Admission Failed", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        
    }
    
}
