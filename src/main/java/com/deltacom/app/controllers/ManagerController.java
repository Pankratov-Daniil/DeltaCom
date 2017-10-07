package com.deltacom.app.controllers;

import com.deltacom.app.entities.Contract;
import com.deltacom.app.entities.Option;
import com.deltacom.app.entities.Tariff;
import com.deltacom.app.repository.api.NumbersPoolRepository;
import com.deltacom.app.repository.api.OptionRepository;
import com.deltacom.app.repository.api.TariffRepository;
import com.deltacom.app.repository.implementation.TariffRepositoryImpl;
import com.deltacom.app.services.api.*;
import com.deltacom.app.services.implementation.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        return new ModelAndView("manager/index");
    }

    @RequestMapping(value = "/browseAllClients")
    public ModelAndView browseAllClients() {
        return new ModelAndView("manager/browseAllClients");
    }

    @RequestMapping(value = "/searchClient")
    public ModelAndView searchClient() {
        return new ModelAndView("manager/searchClient");
    }

    @RequestMapping(value = "/tariffsActions")
    public ModelAndView tariffsActions() {
        return new ModelAndView("manager/tariffsActions");
    }

    @RequestMapping(value = "/optionsActions")
    public ModelAndView optionsActions() {
        return new ModelAndView("manager/optionsActions");
    }

    @RequestMapping(value = "/optionsCompatibility")
    public ModelAndView optionsCompatibility() {
        return new ModelAndView("manager/optionsCompatibility");
    }

    @RequestMapping(value = "/blockActions")
    public ModelAndView blockActions() {
        return new ModelAndView("manager/blockActions");
    }

    @RequestMapping(value = "/addNewContract")
    public ModelAndView addNewContract() {
        ModelAndView modelAndView = new ModelAndView("manager/addNewContract");

        modelAndView.addObject("unusedNumbers", numbersPoolService.getAllUnusedNumbers());
        modelAndView.addObject("availableTariffs", tariffService.getAll());

        return modelAndView;
    }

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

    @ResponseBody
    @RequestMapping(value = "/getOptionsForContract", produces="application/json")
    public List<Option> getOptionsForContract(@RequestParam("selectTariff") int selectedTariffId) {
        return optionService.getAllOptionsForTariff(selectedTariffId);
    }
}
