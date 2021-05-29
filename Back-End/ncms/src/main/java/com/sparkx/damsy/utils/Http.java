package com.sparkx.damsy.utils;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class Http {
    /**
     * Output response for http
     * 
     * @param response
     * @param payload
     * @param status
     */
    public static void outputResponse(HttpServletResponse response, String payload, int status) {

        response.setHeader("Content-Type", "application/json");
        try {
            response.setStatus(status);
            if (payload != null) {
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(payload.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Create json string from hospital object
     * @param hospital
     * @return
     */
    public static String jsonSerialize(Object object) {
        if (object == null) return null;
        Gson gson = new Gson();
        String json = null;
        try{
            json = gson.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
