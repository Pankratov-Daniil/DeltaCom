package com.deltacom.app.controllers;

import com.deltacom.app.entities.Option;
import com.deltacom.app.services.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Controller for processing requests from/to manager pages
 */
@Controller
@RequestMapping(value = "/manager")
public class ManagerController extends CommonController {
    @Autowired
    NumbersPoolService numbersPoolService;
    @Autowired
    TariffService tariffService;
    @Autowired
    OptionService optionService;
    @Autowired
    ContractService contractService;

    /**
     * Processing request to manager 'index' page
     * @return index page
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        return new ModelAndView("manager/index");
    }

    /**
     * Processing request to manager 'browse all clients' page
     * @return browse all clients page
     */
    @RequestMapping(value = "/browseAllClients")
    public ModelAndView browseAllClients() {
        return new ModelAndView("manager/browseAllClients");
    }

    /**
     * Processing request to manager 'search client' page
     * @return search client page
     */
    @RequestMapping(value = "/searchClient")
    public ModelAndView searchClient() {
        return new ModelAndView("manager/searchClient");
    }

    /**
     * Processing request to manager 'tariffs actions' page
     * @return tariffs actions page
     */
    @RequestMapping(value = "/tariffsActions")
    public ModelAndView tariffsActions() {
        return new ModelAndView("manager/tariffsActions");
    }

    /**
     * Processing request to manager 'options actions' page
     * @return options actions page
     */
    @RequestMapping(value = "/optionsActions")
    public ModelAndView optionsActions() {
        return new ModelAndView("manager/optionsActions");
    }

    /**
     * Processing request to managers 'options compatibility' page
     * @return options compatibility page
     */
    @RequestMapping(value = "/optionsCompatibility")
    public ModelAndView optionsCompatibility() {
        return new ModelAndView("manager/optionsCompatibility");
    }

    /**
     * Processing request to managers 'client blocking actions' page
     * @return client blocking actions page
     */
    @RequestMapping(value = "/blockActions")
    public ModelAndView blockActions() {
        return new ModelAndView("manager/blockActions");
    }

    /**
     * Processing request to managers 'add new contract' page
     * @param session current session
     * @param response http response
     * @return 'add new contract' page or 'index' page if there is no client in session
     */
    @RequestMapping(value = "/addNewContract")
    public ModelAndView addNewContract(HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("clientId") == null) {
            response.sendRedirect("index");
            return null;
        }
        ModelAndView modelAndView = new ModelAndView("manager/addNewContract");

        // pass to model unused numbers and available tariffs
        modelAndView.addObject("unusedNumbers", numbersPoolService.getAllUnusedNumbers());
        modelAndView.addObject("availableTariffs", tariffService.getAll());

        return modelAndView;
    }

    /**
     * Processing request to 'registrate new contract' and register new client
     * @param request http request
     * @param session current session
     * @param response http response
     */
    @RequestMapping(value = "/regNewContract")
    public void regNewContract(HttpServletRequest request,
                                       HttpSession session, HttpServletResponse response) throws IOException{
        String selectedNumber = request.getParameterValues("selectNumber")[0];
        String selectedTariff = request.getParameterValues("selectTariff")[0];
        String[] selectedOptions = request.getParameterValues("selectOptions");
        int clientId = (int)session.getAttribute("clientId");

        if(contractService.createNewContract(clientId, selectedNumber, Integer.parseInt(selectedTariff), selectedOptions)) {
            session.setAttribute("successContractCreation", true);
        }
        response.sendRedirect("index");
    }

    /**
     * Processing ajax request from 'add new contract' page.
     * @param selectedTariffId id of selected tariff
     * @return list of options available for selected tariff
     */
    @ResponseBody
    @RequestMapping(value = "/getOptionsForContract", produces="application/json")
    public List<Option> getOptionsForContract(@RequestParam("selectTariff") int selectedTariffId) {
        return optionService.getAllOptionsForTariff(selectedTariffId);
    }
}
