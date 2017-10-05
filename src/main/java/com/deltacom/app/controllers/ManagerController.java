package com.deltacom.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/manager")
public class ManagerController extends CommonController {
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        return new ModelAndView("manager/index");
    }

    @RequestMapping(value = "/editContracts")
    public ModelAndView editContracts() {
        return new ModelAndView("manager/editContracts");
    }

    @RequestMapping(value = "/editTariffs")
    public ModelAndView editTariffs() {
        return new ModelAndView("manager/editTariffs");
    }

    @RequestMapping(value = "/addNewClient")
    public ModelAndView addNewClient() {
        return new ModelAndView("manager/addNewClient");
    }
}
