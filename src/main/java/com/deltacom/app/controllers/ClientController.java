package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class ClientController {
    @Autowired
    ClientService clientService;
    @Autowired
    ContractService contractService;

    @RequestMapping(value = "/user/index")
    public ModelAndView index(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("user/index");
        String clientName = (String)session.getAttribute("clientName");

        if (clientName == null) {
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Client client = clientService.getClientByEmail(user.getUsername());
            clientName = client.getFirstName() + " " + client.getLastName();
            session.setAttribute("clientName", clientName);
        }
        modelAndView.addObject("clientName", clientName);
        return modelAndView;
    }

    @RequestMapping(value = "/user/tariffs")
    public ModelAndView tariffs() {
        return new ModelAndView("user/tariffs");
    }

    @RequestMapping(value = "/user/contracts")
    public ModelAndView contracts(Principal principal) {
        ModelAndView model = new ModelAndView("user/contracts");
        model.addObject("clientContracts", contractService.getAllClientContractsById(clientService.getClientByEmail(principal.getName()).getId()));
        return model;
    }
}
