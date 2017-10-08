package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.services.api.AccessLevelService;
import com.deltacom.app.services.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller for processing common requests for all controllers
 */
@Controller
public class CommonController {
    @Autowired
    ClientService clientService;
    @Autowired
    AccessLevelService accessLevelService;

    /**
     * If clientName didn't set on this session, this method does this.
     * @param session current session where function will add clientName
     * @return client first name + last name
     */
    @ModelAttribute("clientName")
    public String setClientNameToSession(HttpSession session) {
        String clientName = (String)session.getAttribute("clientName");
        if (clientName == null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Client client = clientService.getClientByEmail(user.getUsername());
            clientName = client.getFirstName() + " " + client.getLastName();
            session.setAttribute("clientName", clientName);
        }
        return clientName;
    }

    /**
     * Processing request to add new client page
     * @return add new client page
     */
    @RequestMapping(value = "/commons/addNewClient")
    public ModelAndView addClient() {
        ModelAndView modelAndView = new ModelAndView("commons/addNewClient");

        Collection<GrantedAuthority> authorities =  (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        // if user have ADMIN privileges - transfer all access levels to page.
        for (GrantedAuthority authority : authorities) {
            if(authority.getAuthority().contains("ADMIN")) {
                modelAndView.addObject("accessLevels", accessLevelService.getAll());
                break;
            }
        }
        return modelAndView;
    }

    /**
     * Registers new client
     */
    @RequestMapping(value = "/commons/regNewClient")
    public void regNewClient(@ModelAttribute(value = "newUser") Client client,
                             HttpServletRequest request, HttpSession session,
                             HttpServletResponse response) throws IOException {
        String[] accessLevelsIds = request.getParameterValues("accessLevelsSelect");

        if(clientService.addNewClient(client, accessLevelsIds)) {
            session.setAttribute("successClientCreation", true);
        }

        // sendRedirect is for avoid url like "url?params=values..."
        if(accessLevelsIds != null) {                   // if user is ADMIN
            response.sendRedirect("addNewClient");
        } else {                                        // if user is MANAGER
            session.setAttribute("clientId", client.getId());
            response.sendRedirect("../manager/addNewContract");
        }
    }

    /**
     * Converts from forms date string to java.util.Date
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }}
