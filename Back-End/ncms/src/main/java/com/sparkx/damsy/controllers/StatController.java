package com.sparkx.damsy.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        if (req.getParameter("type") == null) {
            Http.outputResponse(resp, "Stat-Type is required", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String statType = req.getParameter("type");

        if (statType.equals("district")) {
            Map<String, Integer> districtStatMap = StatRepository.getDistrictStatsFromDB();
            ArrayList<JsonObject> ditrictJsonDataList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : districtStatMap.entrySet()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("district", entry.getKey());
                jsonObject.addProperty("count", entry.getValue());
                ditrictJsonDataList.add(jsonObject);
            }

            Http.outputResponse(resp, JsonFunctions.jsonSerialize(ditrictJsonDataList), HttpServletResponse.SC_OK);
            return;

        } else if (statType.equals("country")) {
            int countlyLevelCount = StatRepository.getAllPatientCountFromDB();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("count", String.valueOf(countlyLevelCount));

            Http.outputResponse(resp, JsonFunctions.jsonSerialize(jsonObject), HttpServletResponse.SC_OK);
            return;

        } else if (statType.equals("daily")) {
            int dailyCount = 0;

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                dailyCount = StatRepository.getDailyPatientCountFromDB(formatter.parse(formatter.format(new Date())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("count", String.valueOf(dailyCount));

            Http.outputResponse(resp, JsonFunctions.jsonSerialize(jsonObject), HttpServletResponse.SC_OK);
            return;

        }

        Http.outputResponse(resp, "Invalid Stat-Type ", HttpServletResponse.SC_BAD_REQUEST);
        return;

    }

}
