package com.sparkx.damsy.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparkx.damsy.models.Hospital;
import com.sparkx.damsy.repository.HospitalRepository;
import com.sparkx.damsy.utils.Http;
import com.sparkx.damsy.utils.JsonFunctions;

@WebServlet(name = "HospitalController")
public class HospitalController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArrayList<Hospital> hospitalList = HospitalRepository.getHospitalList();
        if (hospitalList != null) {
            Http.outputResponse(resp, JsonFunctions.jsonSerialize(hospitalList), HttpServletResponse.SC_OK);
            return;
        } else {
            Http.outputResponse(resp, "Server Error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String jsonPayload = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(jsonPayload);
        Hospital hospital = (Hospital) JsonFunctions.jsonDeserialize(jsonPayload, "hospital");

        if (hospital == null) {
            Http.outputResponse(resp, "Bad request", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!hospital.getName().isEmpty() && !hospital.getDistrict().isEmpty()) {
            int hospital_id = HospitalRepository.getCountHospital() + 1;
            hospital.setId(String.valueOf(hospital_id));
            hospital.setAvailBeds(10);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                hospital.setBuildDate(formatter.parse(formatter.format(new Date())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (HospitalRepository.insertIntoHospital(hospital)) {
                Http.outputResponse(resp, "Hospital Created", HttpServletResponse.SC_CREATED);
                return;
            } else {
                Http.outputResponse(resp, "Data insertion failed", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

        } else {
            Http.outputResponse(resp, "Invalid Data", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

}
