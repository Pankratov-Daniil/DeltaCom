package com.deltacom.app.controllers;

import com.deltacom.app.services.api.ContractService;
import com.deltacom.app.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Controller for processing requests from/to user pages
 */
@Controller
@RequestMapping(value = "/user")
public class ClientController extends CommonController {
    @Autowired
    ContractService contractService;
    @Autowired
    TariffService tariffService;

    /**
     * Processing request to client index page
     * @return client index page
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        return new ModelAndView("user/index");
    }

    /**
     * Processing request to client tariffs page
     * Transferring client contracts to the model.
     * @return client tariffs page
     */
    @RequestMapping(value = "/tariffs")
    public ModelAndView tariffs() {
        ModelAndView model = new ModelAndView("user/tariffs");
        model.addObject("tariffs", tariffService.getAll());
        return model;
    }

    /**
     * Processing request to client contract page.
     * Transferring client contracts to the model.
     * @param principal security principal to get client email
     * @return client contracts page
     */
    @RequestMapping(value = "/contracts")
    public ModelAndView contracts(Principal principal) {
        ModelAndView model = new ModelAndView("user/contracts");
        model.addObject("clientContracts",
                contractService.getAllClientContractsByEmail(principal.getName()));
        return model;
    }

    @RequestMapping(value = "/options")
    public ModelAndView options() {
        return new ModelAndView("user/options");
    }
}
