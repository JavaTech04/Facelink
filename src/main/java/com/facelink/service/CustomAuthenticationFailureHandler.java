package com.facelink.service;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "";
        if (exception.getMessage().equalsIgnoreCase("Bad credentials")) {
            errorMessage = "Invalid username or password";
        } else if (exception.getMessage().equalsIgnoreCase("User account is locked")) {
            errorMessage = "User account is locked";
        } else if (exception.getMessage().equalsIgnoreCase("User credentials have expired")) {
            errorMessage = "User account has expired";
        }
        request.getSession().setAttribute("error", errorMessage);
        request.getSession().setAttribute("username", request.getParameter("username"));
        request.getSession().setAttribute("password", request.getParameter("password"));
        response.sendRedirect("/login?error");
    }
}
