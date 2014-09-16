package org.fazz.controller;

import org.fazz.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarController {

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping(value = "/add-car", method = RequestMethod.GET)
    public ModelAndView addCarPage() {
        return new ModelAndView("add-car");
    }

    @RequestMapping(value = "/view-car/{id}", method = RequestMethod.GET)
    public ModelAndView viewCarPage(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("view-car");
        modelAndView.addObject("car", mongoTemplate.findById(id, Car.class));
        return modelAndView;
    }

    @RequestMapping(value = "/add-car", method = RequestMethod.POST)
    public String addCar(@ModelAttribute("car") Car car) {
        mongoTemplate.insert(car);
        return "redirect:view-car/" + car.getId();
    }

}
