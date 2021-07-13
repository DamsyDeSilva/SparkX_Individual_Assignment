package com.sparkx.damsy.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparkx.damsy.enums.Role;
import com.sparkx.damsy.models.Patient;
import com.sparkx.damsy.models.User;
import com.sparkx.damsy.repository.PatientRepository;
import com.sparkx.damsy.repository.UserRepository;
import com.sparkx.damsy.utils.Http;
import com.sparkx.damsy.utils.JsonFunctions;

@WebServlet(name = "patientDischarge", value = "/doctor/discharge")
public class PatientDischargeController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        ArrayList<Patient> patientsListToBeDischarged = PatientRepository.getPatientsToBeDischarged();
        if (patientsListToBeDischarged != null) {
            Http.outputResponse(resp, JsonFunctions.jsonSerialize(patientsListToBeDischarged), HttpServletResponse.SC_OK);
            return;
        } else {
            Http.outputResponse(resp, "Server Error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
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

        User user = UserRepository.LoadUserFromDB(req.getParameter("username"));

        if (user.getFirstName() == null) {
            Http.outputResponse(resp, "Username is Inavlid", HttpServletResponse.SC_BAD_REQUEST);
            return;
        } 

        if (user.getRole() != Role.DOCTOR) {
            Http.outputResponse(resp, "Only Doctors can discharge patients", HttpServletResponse.SC_BAD_REQUEST);
            return;
        } 

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dischargeDate = null;
        try {
            dischargeDate = formatter.parse(formatter.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
           
        if (PatientRepository.insertDischargeToDB(req.getParameter("patientId"), user.getUserName(), dischargeDate)) {
            Http.outputResponse(resp, "Patient Discharged", HttpServletResponse.SC_OK);
            return;
        } else{
            Http.outputResponse(resp, "Patient discharge process Failed", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
    }
    
}
