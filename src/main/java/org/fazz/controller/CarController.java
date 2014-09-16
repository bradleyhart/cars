package org.fazz.controller;

import org.fazz.domain.Car;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarController {

    private Car car;

    @RequestMapping(value = "/add-car", method = RequestMethod.GET)
    public ModelAndView addCarPage() {
        return new ModelAndView("add-car");
    }

    @RequestMapping(value = "/view-car", method = RequestMethod.GET)
    public ModelAndView viewCarPage() {
        ModelAndView modelAndView = new ModelAndView("view-car");
        modelAndView.addObject("car", car);
        return modelAndView;
    }

    @RequestMapping(value = "/add-car", method = RequestMethod.POST)
    public String addCar(@ModelAttribute("car") Car car) {
        this.car = car;
        return "redirect:view-car";
    }

}
