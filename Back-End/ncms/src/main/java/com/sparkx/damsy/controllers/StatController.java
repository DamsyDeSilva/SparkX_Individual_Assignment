package com.sparkx.damsy.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.sparkx.damsy.repository.StatRepository;
import com.sparkx.damsy.utils.Http;
import com.sparkx.damsy.utils.JsonFunctions;

@WebServlet(name = "StatController", value = "/stats")
public class StatController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        Map<String, Integer> districtStatMap  = StatRepository.getDistrictStatsFromDB();
        int countlyLevelCount = StatRepository.getAllPatientCountFromDB();
        int dailyCount = 0;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dailyCount = StatRepository.getDailyPatientCountFromDB(formatter.parse(formatter.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String districtJsonString = JsonFunctions.jsonSerialize(districtStatMap);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("DistrictLevel", districtJsonString);
        jsonObject.addProperty("CountryLevel", String.valueOf(countlyLevelCount));
        jsonObject.addProperty("DailyCount", String.valueOf(dailyCount));
        

        Http.outputResponse(resp, JsonFunctions.jsonSerialize(jsonObject), HttpServletResponse.SC_OK);
        return;

    }

}
