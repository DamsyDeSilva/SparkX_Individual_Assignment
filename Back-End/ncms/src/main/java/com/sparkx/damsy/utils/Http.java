package com.sparkx.damsy.utils;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;


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

}
