package com.sparkx.damsy.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparkx.damsy.models.PatientQueue;
import com.sparkx.damsy.repository.QueueRepository;
import com.sparkx.damsy.utils.Http;
import com.sparkx.damsy.utils.JsonFunctions;

@WebServlet(name = "queueCOntroller", value = "/moh/queue")
public class QueueController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArrayList<PatientQueue> patientsInQueue = QueueRepository.getPatientsInQueue();
        if (patientsInQueue != null) {
            Http.outputResponse(resp, JsonFunctions.jsonSerialize(patientsInQueue), HttpServletResponse.SC_OK);
            return;
        } else {
            Http.outputResponse(resp, "Server Error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
    }

}
