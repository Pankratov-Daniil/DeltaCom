package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.ClientLocation;
import com.deltacom.app.services.api.ClientLocationService;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.LoginService;
import com.deltacom.app.services.api.MessageSenderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

/**
 * Controller for processing requests to login page
 */
@Controller
public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/")
    public ModelAndView rootProcessing(HttpServletRequest request, Principal principal) {
        return processLogin(request, principal);
    }

    @RequestMapping(value = "/process-login")
    public ModelAndView processLogin(HttpServletRequest request, Principal principal){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            String header = request.getHeader("X-Forwarded-For");
            String ip;
            if(header == null || header.isEmpty()) {
                ip = request.getRemoteAddr();
            } else {
                int pos = header.indexOf(',');
                if (pos < 0) {
                    ip = header;
                } else {
                    ip = header.substring(0,pos);
                }
            }
            loginService.saveClientLocation(ip, principal.getName());
        } catch (RuntimeException ex) { }

        // if user has already logged in - redirect him to index page
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            for(GrantedAuthority authority : auth.getAuthorities()) {
                logger.info("User with email: " + auth.getName() + " and authorities: " + auth.getAuthorities() + " logged in.");
                if (authority.getAuthority().contains("ADMIN"))
                    return new ModelAndView("redirect:/admin/index");
                else if (authority.getAuthority().contains("MANAGER"))
                    return new ModelAndView("redirect:/manager/index");
                else if (authority.getAuthority().contains("USER"))
                    return new ModelAndView("redirect:/user/index");
            }
        }
        return new ModelAndView("loginPage");
    }
}
