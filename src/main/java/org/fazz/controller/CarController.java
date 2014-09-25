package org.fazz.controller;

import org.fazz.model.Car;
import org.fazz.service.CarListings;
import org.fazz.session.CarSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

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
        return new ModelAndView("view-car") {{
            addObject("car", carListings.get(id));
        }};
    }

    @RequestMapping(value = "/add-car", method = RequestMethod.POST)
    public String addCar(@ModelAttribute("car") Car car) {
        carListings.add(car);
        return "redirect:view-car/" + car.getId();
    }

    @RequestMapping(value = "/view-cars", method = RequestMethod.GET)
    public ModelAndView viewCars() {
        return new ModelAndView("view-cars") {{
            addObject("cars", carListings.all());
        }};
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

    @RequestMapping(value = "/make-autocomplete", method = RequestMethod.GET)
    @ResponseBody
    public List<Suggestion> makes(@RequestParam("make") String make) {
        return suggestions(carListings.make(make));
    }

    @RequestMapping(value = "/model-autocomplete", method = RequestMethod.GET)
    @ResponseBody
    public List<Suggestion> models(@RequestParam("model") String model) {
        return suggestions(carListings.model(model));
    }

    @RequestMapping(value = "/year-autocomplete", method = RequestMethod.GET)
    @ResponseBody
    public List<Suggestion> year(@RequestParam("year") String years) {
        return suggestions(carListings.year(years).stream()
                .map(Object::toString)
                .collect(toList()));
    }

    private List<Suggestion> suggestions(List<String> values) {
        return range(0, values.size())
                .mapToObj(index -> new Suggestion(index, values.get(index)))
                .collect(toList());
    }

    public class Suggestion {

        private String value;
        private int data;

        public Suggestion(int data, String value) {
            this.data = data;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getData() {
            return data;
        }
    }


}
