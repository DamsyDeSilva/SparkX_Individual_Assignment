package com.sparkx.damsy.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparkx.damsy.service.AuthService;
import com.sparkx.damsy.utils.Http;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/hospital", "/doctor" })
public class AuthFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String header = request.getHeader("Authorization");


        //check the token validity
        if (!AuthService.checkValidity(header)) {

            // PrintWriter out = response.getWriter();
            // response.setContentType("application/json");
            // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // out.println("{\"code\":401,\"message\":\"error\",\"data\":null}");
            // out.flush();

            Http.outputResponse(response, "UnAuthorized User", HttpServletResponse.SC_BAD_REQUEST);
            return;

        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
    
}
