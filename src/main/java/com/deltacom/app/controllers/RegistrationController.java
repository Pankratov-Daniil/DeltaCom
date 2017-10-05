package com.deltacom.app.controllers;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.entities.Client;
import com.deltacom.app.services.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/registration")
    public ModelAndView registration(@ModelAttribute("newUser") Client client){
        ModelAndView model = new ModelAndView("registration");
        model.addObject("clientsList", clientService.getAll());
        return model;
    }

    @RequestMapping(value = "/regNewUser")
    public ModelAndView regUser(@ModelAttribute("newUser") Client client){
        if(!client.getFirstName().equals("") &&
                !client.getLastName().equals("") &&
                !client.getAddress().equals("")) {
            int passwordStrength = 11;
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(passwordStrength);
            client.setPassword(encoder.encode(client.getPassword()));
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setId(1);
            client.setAccessLevels(Collections.singletonList(accessLevel));
            clientService.create(client);
        }
        return new ModelAndView("redirect:/registration");
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView deleteUser(@ModelAttribute("newUser") Client client, @PathVariable("id") int id){    
        clientService.delete(clientService.getById(id));
        return new ModelAndView("redirect:/registration");
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
