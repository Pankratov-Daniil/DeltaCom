package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.services.api.AccessLevelService;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.utils.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class RegistrationController {
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/registration")
    public ModelAndView registration(@ModelAttribute("newUser") Client client){
        ModelAndView model = new ModelAndView("registration");
        model.addObject("clientsList", clientService.getAllEntities());
        return model;
    }

    @RequestMapping(value = "/regNewUser")
    public ModelAndView regUser(@ModelAttribute("newUser") Client client){
        client.setPassword(MD5Encoder.encodePassword(client.getPassword()));
        clientService.createEntity(client);
        return new ModelAndView("redirect:/registration");
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView deleteUser(@ModelAttribute("newUser") Client client, @PathVariable("id") int id){
        clientService.deleteEntity(clientService.getEntityById(id));
        return new ModelAndView("redirect:/registration");
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(){
        return new ModelAndView("login");
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
