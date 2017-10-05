package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.services.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class CommonController {
    @Autowired
    ClientService clientService;

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
}
