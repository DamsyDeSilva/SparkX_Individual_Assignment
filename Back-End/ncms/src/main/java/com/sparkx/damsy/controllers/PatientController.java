package com.sparkx.damsy.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.sparkx.damsy.models.Patient;
import com.sparkx.damsy.repository.HospitalRepository;
import com.sparkx.damsy.repository.PatientRepository;
import com.sparkx.damsy.repository.QueueRepository;
import com.sparkx.damsy.service.PatientService;
import com.sparkx.damsy.utils.Http;
import com.sparkx.damsy.utils.JsonFunctions;

import com.sparkx.damsy.models.PatientQueue;

@WebServlet(name = "Patient", value = "/patient")
public class PatientController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1> Patient Servlet</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonPayload = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(jsonPayload);
        Patient patient = (Patient) JsonFunctions.jsonDeserialize(jsonPayload, "patient");

        if (patient == null) {
            Http.outputResponse(resp, "Bad request", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UUID uuid = UUID.randomUUID();
        patient.setId(uuid.toString());
    
        if (!patient.getFirstName().isEmpty() && patient.getLocationX() != 0 && patient.getLocationY() != 0) {
            
            // search for a hospital
            String hospital_id = PatientService.getAvailbleHospital(patient);
            
            if(hospital_id.equals("NO BEDS ARE AVAILABLE")){
                // --> add to queue 
                int nextQueueId = QueueRepository.getCountQueue() + 1;
                PatientQueue queue = new PatientQueue(nextQueueId, patient.getId());
                if(QueueRepository.insertIntoQueue(queue, patient)){
                    // data to be send to patient
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("Status", "WATING_IN_QUEUE");
                    jsonObject.addProperty("SerialId", patient.getId());
                    jsonObject.addProperty("QueueNumber", queue.getId());

                    Http.outputResponse(resp, JsonFunctions.jsonSerialize(jsonObject), HttpServletResponse.SC_CREATED);
                    return;
                }

                Http.outputResponse(resp, "Queue Insertion failed, no hospital allocated", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            // search for the bed no
            int allocatedBedNo = HospitalRepository.getAvailabelBedNo(hospital_id);
            if(allocatedBedNo > 0){
                patient.setHospitalId(hospital_id);
                patient.setBedNo(allocatedBedNo);
                System.out.println("hospital Id : "+ hospital_id);
            }else{
                Http.outputResponse(resp, "Something bad happened with allocating beds", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (PatientRepository.insertIntoPatientandUpdateHospital(patient)) {
                            
                // data to be send to patient
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("status", "BED_ALLOCATED");
                jsonObject.addProperty("patientName", (patient.getFirstName()+" "+patient.getLastName()));
                jsonObject.addProperty("SerialId", patient.getId());
                jsonObject.addProperty("hospital", patient.getHospitalId());
                jsonObject.addProperty("bedNumber", patient.getBedNo()); 

                Http.outputResponse(resp, JsonFunctions.jsonSerialize(jsonObject), HttpServletResponse.SC_CREATED);
                return;
                            
            }else{
                Http.outputResponse(resp, "Data insertion failed ", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }
        else{
            Http.outputResponse(resp,"Invalid Data",HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }
}