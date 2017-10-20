package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.Option;
import com.deltacom.app.entities.Tariff;
import com.deltacom.app.services.api.AccessLevelService;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.OptionService;
import com.deltacom.app.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
     * Registers new client
     */
    @RequestMapping(value = "/commons/regNewClient")
    public ModelAndView regNewClient(@ModelAttribute(value = "newUser") Client client,
                             HttpServletRequest request, HttpSession session,
                                     RedirectAttributes ra) {
        String[] accessLevelsIds = request.getParameterValues("accessLevelsSelect");
        if(clientService.addNewClient(client, accessLevelsIds)) {
            session.setAttribute("successClientCreation", true);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for(GrantedAuthority authority : auth.getAuthorities()) {
            if (authority.getAuthority().contains("ADMIN")) {
                return new ModelAndView("redirect:/admin/addNewClient");
            } else if (authority.getAuthority().contains("MANAGER")) {
                session.setAttribute("clientId", client.getId());
                return new ModelAndView("redirect:/manager/addNewContract");
            }
        }
        return new ModelAndView("redirect:/index");
    }

    /**
     * Processing ajax request from 'add new contract' page.
     * @param selectedTariffId id of selected tariff
     * @return list of options available for selected tariff
     */
    @ResponseBody
    @RequestMapping(value = "/commons/getOptionsForTariff", produces=MediaType.APPLICATION_JSON_VALUE)
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
