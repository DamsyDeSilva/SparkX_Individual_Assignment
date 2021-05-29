package com.sparkx.damsy.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparkx.damsy.models.Hospital;
import com.sparkx.damsy.repository.HospitalRepository;
import com.sparkx.damsy.utils.Http;

@WebServlet(name = "HospitalController")
public class HospitalController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        if (req.getParameter("id").isEmpty()) {
            Http.outputResponse(resp, "Hospital Id is required", HttpServletResponse.SC_FORBIDDEN);
            return;
        }
    
        Hospital hospital = HospitalRepository.loadModelFromDB(req.getParameter("id"));
        if (hospital.getName() != null){
            Http.outputResponse(resp, Http.jsonSerialize(hospital), HttpServletResponse.SC_OK);
            return;
        }else{
            Http.outputResponse(resp, "Hospital Id is invalid", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }       
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doDelete(req, resp);
    }
    
}
