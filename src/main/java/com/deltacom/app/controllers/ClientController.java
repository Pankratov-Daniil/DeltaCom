package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.ContractService;
import com.deltacom.app.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * Spring Controller for all requests to /user folder
 */
@Controller
@RequestMapping(value = "/user")
public class ClientController extends CommonController {
    @Autowired
    ClientService clientService;
    @Autowired
    ContractService contractService;
    @Autowired
    TariffService tariffService;

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        return new ModelAndView("user/index");
    }

    @RequestMapping(value = "/tariffs")
    public ModelAndView tariffs() {
        ModelAndView model = new ModelAndView("user/tariffs");
        model.addObject("tariffs", tariffService.getAll());
        return model;
    }

    @RequestMapping(value = "/contracts")
    public ModelAndView contracts(Principal principal) {
        ModelAndView model = new ModelAndView("user/contracts");
        model.addObject("clientContracts",
                contractService.getAllClientContractsById(
                        clientService.getClientByEmail(principal.getName()).getId()));
        return model;
    }

    @RequestMapping(value = "/options")
    public ModelAndView options() {
        return new ModelAndView("user/options");
    }
}
