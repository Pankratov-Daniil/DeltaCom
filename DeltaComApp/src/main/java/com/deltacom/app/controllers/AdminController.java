package com.deltacom.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for processing requests from/to admins pages
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends CommonController {
    /**
     * Processing request to admin index page
     * @return index page
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        return new ModelAndView("admin/index");
    }

    /**
     * Processing request to admin grant access page
     * @return grant access page
     */
    @RequestMapping(value = "/grantAccess")
    public ModelAndView grantAccess() {
        return new ModelAndView("admin/grantAccess");
    }
}
