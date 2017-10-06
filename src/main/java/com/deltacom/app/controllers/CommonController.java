package com.deltacom.app.controllers;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.entities.Client;
import com.deltacom.app.services.api.AccessLevelService;
import com.deltacom.app.services.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CommonController {
    @Autowired
    ClientService clientService;
    @Autowired
    AccessLevelService accessLevelService;

    /**
     * If clientName didn't set on this session, this method does this.
     * @param session Spring give it for us
     * @return clients first name + last name
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
     * Here we look if user have ADMIN privileges
     * if so, transfer all access levels to page
     */
    @RequestMapping(value = "/commons/addNewClient")
    public ModelAndView addClient() {
        ModelAndView modelAndView = new ModelAndView("commons/addNewClient");

        Collection<GrantedAuthority> authorities =  (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if(authority.getAuthority().contains("ADMIN")) {
                modelAndView.addObject("accessLevels", accessLevelService.getAll());
                break;
            }
        }
        return modelAndView;
    }

    /**
     * Here we're register new client
     */
    @RequestMapping(value = "/commons/regNewClient")
    public void regNewClient(@ModelAttribute(value = "newUser") Client client,
                             HttpServletRequest request, HttpSession session,
                             HttpServletResponse response) {
        String[] accessLevelsIds = request.getParameterValues("multiselect[]");
        List<AccessLevel> accessLevels = new ArrayList<>();

        if(accessLevelsIds == null || accessLevelsIds.length == 0) {
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setId(1);
            accessLevels = Collections.singletonList(accessLevel);
        }
        else {
            for (String accessLevelId : accessLevelsIds) {
                AccessLevel accessLevel = new AccessLevel();
                accessLevel.setId(Integer.parseInt(accessLevelId));
                accessLevels.add(accessLevel);
            }
        }

        int passwordStrength = 11;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(passwordStrength);
        client.setPassword(encoder.encode(client.getPassword()));
        client.setAccessLevels(accessLevels);

        clientService.create(client);
        session.setAttribute("successCreation", true);
        try {
            response.sendRedirect("addNewClient"); // this is for avoid url like "url?params=values..."
        } catch (IOException e) {
            e.printStackTrace();
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
