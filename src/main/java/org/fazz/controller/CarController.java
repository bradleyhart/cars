package org.fazz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarController {

    @RequestMapping(value = "/add-car", method = RequestMethod.GET)
    public ModelAndView addCarPage() {
        return new ModelAndView("add-car");
    }

    @RequestMapping(value = "/view-car", method = RequestMethod.GET)
    public ModelAndView viewCarPage() {
        return new ModelAndView("view-car");
    }

    @RequestMapping(value = "/add-car", method = RequestMethod.POST)
    public String addCar() {
        return "redirect:view-car";
    }

}
