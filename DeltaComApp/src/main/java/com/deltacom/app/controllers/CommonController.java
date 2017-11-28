package com.deltacom.app.controllers;

import com.deltacom.app.entities.*;
import com.deltacom.app.utils.DTOConverter;
import com.deltacom.dto.*;
import com.deltacom.app.services.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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
    @Autowired
    OptionService optionService;
    @Autowired
    TariffService tariffService;
    @Autowired
    ContractService contractService;

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
                modelAndView.addObject("accessLevels", accessLevelService.getAllAccessLevels());
                break;
            }
        }
        return modelAndView;
    }

    /**
     * Processing ajax request from 'browse all clients' page when open change tariff modal page.
     * @param number number of contract
     * @return list of options for contract
     */
    @ResponseBody
    @RequestMapping(value = "/commons/getContractByNumber", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public Contract getContractByNumber(@RequestParam String number) {
        return contractService.getContractByNumber(number);
    }

    /**
     * Registers new client
     */
    @ResponseBody
    @RequestMapping(value = "/commons/regNewClient", method = RequestMethod.POST)
    public void regNewClient(@RequestBody ClientDTO clientDTO) {
        clientService.addNewClient(DTOConverter.ClientDTOToClient(clientDTO), clientDTO.getAccessLevels());
    }

    /**
     * Changes contract
     */
    @ResponseBody
    @RequestMapping(value = "/commons/changeContract", method = RequestMethod.POST)
    public void changeContract(@RequestBody ContractDTO contractDTO) {
        contractService.updateContract(contractDTO.getNumber(), contractDTO.getTariffId(), contractDTO.getOptionsIds());
    }

    /**
     * Processing ajax request from 'add new contract' page.
     * @param selectedTariffId id of selected tariff
     * @return list of options available for selected tariff
     */
    @ResponseBody
    @RequestMapping(value = "/commons/getOptionsForTariff", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Option> getOptionsForContract(@RequestBody int selectedTariffId) {
        return optionService.getAllOptionsForTariff(selectedTariffId);
    }

    /**
     * Gets all options
     * @return list of all options
     */
    @ResponseBody
    @RequestMapping(value = "/commons/getAllOptions", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Option> getAllOptions() {
        return optionService.getAllOptions();
    }

    /**
     * Gets all tariffs
     * @return list of all tariffs
     */
    @ResponseBody
    @RequestMapping(value = "/commons/getAllTariffs", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Tariff> getAllTariffs() {
        return tariffService.getAllTariffs();
    }

    /**
     * Converts from forms date string to java.util.Date
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
}
