package org.fazz.controller;


import org.fazz.model.Car;
import org.fazz.service.CarListings;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.fazz.model.Car.car;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CarControllerTest {

    private CarController carController;
    private CarListings carListings;

    @Before
    public void initializeController() {
        carListings = mock(CarListings.class);
        carController = new CarController(carListings);
    }

    @Test
    public void addsCarToListingsAndRedirectsToCar() {
        Car car = car("Audi", "A6", 2004, 40000);
        car.setId("car-id");

        String redirect = carController.addCar(car);

        assertThat(redirect, is(equalTo("redirect:view-car/car-id")));
        verify(carListings).add(car);
    }

    @Test
    public void viewCarFromListings() {
        Car car = car("Ferrari", "Enzo", 2003, 999999);
        when(carListings.get("car-id")).thenReturn(car);

        ModelAndView modelAndView = carController.viewCarPage("car-id");

        assertThat((Car) modelAndView.getModel().get("car"), is(equalTo(car)));
        assertThat(modelAndView.getViewName(), is(equalTo("view-car")));
    }

    @Test
    public void viewAllCars() {
        final Car car1 = car("Ferrari", "Enzo", 2003, 999999);
        final Car car2 = car("Ferrari", "Enzo", 2003, 999999);
        List<Car> cars = new ArrayList<Car>(){{
            add(car1);
            add(car2);
        }};

        when(carListings.get()).thenReturn(cars);

        ModelAndView modelAndView = carController.viewCars();

        assertThat((ArrayList<Car>) modelAndView.getModel().get("cars"), is(equalTo(cars)));
        assertThat(modelAndView.getViewName(), is(equalTo("view-cars")));
    }

}
