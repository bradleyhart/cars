package org.fazz.controller;

import org.fazz.model.Car;
import org.fazz.service.CarListings;
import org.fazz.session.CarSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CarController {

    private CarListings carListings;

    @Autowired
    public CarController(CarListings carListings) {
        this.carListings = carListings;
    }

    @RequestMapping(value = "/add-car", method = RequestMethod.GET)
    public ModelAndView addCarPage() {
        return new ModelAndView("add-car");
    }

    @RequestMapping(value = "/view-car/{id}", method = RequestMethod.GET)
    public ModelAndView viewCarPage(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("view-car");
        modelAndView.addObject("car", carListings.get(id));
        return modelAndView;
    }

    @RequestMapping(value = "/add-car", method = RequestMethod.POST)
    public String addCar(@ModelAttribute("car") Car car) {
        carListings.add(car);
        return "redirect:view-car/" + car.getId();
    }

    @RequestMapping(value = "/view-cars", method = RequestMethod.GET)
    public ModelAndView viewCars() {
        ModelAndView modelAndView = new ModelAndView("view-cars");
        modelAndView.addObject("cars", carListings.all());
        return modelAndView;
    }

    @RequestMapping(value = "/search-cars", method = RequestMethod.GET)
    public ModelAndView searchCarsPage() {
        return new ModelAndView("search-cars");
    }

    @RequestMapping(value = "/search-cars", method = RequestMethod.POST)
    public ModelAndView searchCar(CarSearch carSearch) {
        ModelAndView modelAndView = new ModelAndView("search-cars-results");
        modelAndView.addObject("cars", carListings.match(carSearch));
        return modelAndView;
    }

}
