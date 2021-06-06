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
import com.sparkx.damsy.models.Hospital;
import com.sparkx.damsy.models.Patient;
import com.sparkx.damsy.repository.HospitalRepository;
import com.sparkx.damsy.repository.PatientRepository;
import com.sparkx.damsy.service.PatientService;
import com.sparkx.damsy.utils.Http;
import com.sparkx.damsy.utils.JsonFunctions;

@WebServlet(name="Patient", value="/patient")
public class PatientController extends HttpServlet{

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
        Patient patient = (Patient) JsonFunctions.jsonDeserialize(jsonPayload, "hospital");

        if (patient == null) {
            Http.outputResponse(resp, "Bad request", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UUID uuid = UUID.randomUUID();
        patient.setId(uuid.toString());

        if (!patient.getFirstName().isEmpty() && patient.getLocationX() == 0 && patient.getLocationY() == 0) {
            
            if (PatientRepository.initalInsertIntoPatient(patient)) {
                // search for a hospital --> for a bed
                Object hospital_status = PatientService.getAvailbleHospital(patient);
                if(hospital_status != "NO BEDS ARE AVAILABLE"){
                    Hospital hospital = (Hospital)hospital_status;
                    int allocatedBedNo = HospitalRepository.getAvailabelBedNo(hospital);
                    if(allocatedBedNo > 0){
                        // update patient table --> hospital id, bed no 
                        patient.setHospitalId(hospital.getId());
                        patient.setBedNo(allocatedBedNo);
                        if(PatientRepository.insertHospitalBed(patient)){
                            hospital.setAvailBeds(hospital.getAvailBeds()-1);
                            if(HospitalRepository.updateAvailBeds(hospital)){
                                // data to be send to patient
                                JsonObject jsonObject = new JsonObject();
                                jsonObject.addProperty("SerialId", patient.getId());
                                jsonObject.addProperty("hospital", patient.getHospitalId());
                                jsonObject.addProperty("bedNumber", patient.getBedNo()); 
                                Http.outputResponse(resp, JsonFunctions.jsonSerialize(jsonObject), HttpServletResponse.SC_CREATED);
                                return;
                            }
                            
                        }else{
                            Http.outputResponse(resp, "Initial Data Saved, hospital & bed insertion failed ", HttpServletResponse.SC_CREATED);
                            return;
                        }
                    }
                }else{
                    // else --> add to queue
                }
                Http.outputResponse(resp, "Patient Inital Data Added", HttpServletResponse.SC_CREATED);
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
