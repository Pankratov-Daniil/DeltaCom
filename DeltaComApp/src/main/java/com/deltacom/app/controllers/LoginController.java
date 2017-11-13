package com.deltacom.app.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for processing requests to login page
 */
@Controller
public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @RequestMapping(value = "/process-login")
    public ModelAndView processLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // if user has already logged in - redirect him to index page
        if (!(auth instanceof AnonymousAuthenticationToken)) {
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
        return new ModelAndView("login");
    }
}
