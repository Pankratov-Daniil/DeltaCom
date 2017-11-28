package com.deltacom.app.controllers;

import com.deltacom.app.exceptions.ClientLocationException;
import com.deltacom.app.services.api.LoginService;
import com.deltacom.dto.CredentialsDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

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
        // if user has already logged in - redirect him to index page
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRE_AUTH"))) {
                logger.info("User with email: " + auth.getName() + " and authorities: " + auth.getAuthorities() + " logged in.");
                try {
                    saveClientLocation(request, principal.getName());
                } catch (RuntimeException ex) {
                    logger.error("Can't save client location:" + ex.getMessage());
                }
            } else {
                return new ModelAndView("redirect:/preAuth");
            }
            for(GrantedAuthority authority : auth.getAuthorities()) {
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

    @RequestMapping(value = "/preAuth")
    public ModelAndView preAuth() {
        return new ModelAndView("preAuth");
    }

    @ResponseBody
    @RequestMapping(value = "/sendPreAuthCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String sendPreAuthCode(Principal principal) {
        return loginService.sendPreAuthCode(principal.getName());
    }

    @RequestMapping(value = "/checkPreAuthCode", method = RequestMethod.POST)
    public ModelAndView checkPreAuthCode(@RequestParam String code,
                                         HttpServletRequest request,
                                         Principal principal) {
        if(loginService.isEnteredPreAuthCodeValid(principal.getName(), code)) {
            loginService.grantAuthorities(principal.getName());
        }
        return processLogin(request, principal);
    }

    @ResponseBody
    @RequestMapping(value = "/remoteLogin", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> remoteLogin(CredentialsDTO credentialsDTO) {
        return loginService.remoteLogin(credentialsDTO);
    }

    /**
     * Saves client geolocation
     * @param request http request (for ip address)
     * @param email client email
     */
    private void saveClientLocation(HttpServletRequest request, String email) {
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
            loginService.saveClientLocation(ip, email);
        } catch (RuntimeException ex) {
            throw new ClientLocationException("Can't save client location: ", ex);
        }
    }
}
