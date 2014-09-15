package org.fazz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Car {

    @RequestMapping(value = "/add-car", method = RequestMethod.GET)
    public ModelAndView addCar() {
        return new ModelAndView("add-car");
    }

}
