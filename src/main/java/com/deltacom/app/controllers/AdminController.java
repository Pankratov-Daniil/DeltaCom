package com.deltacom.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController extends CommonController {
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        return new ModelAndView("admin/index");
    }
}
