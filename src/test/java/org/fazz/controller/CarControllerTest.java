package org.fazz.controller;


import org.fazz.model.Car;
import org.fazz.service.CarListings;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import static org.fazz.model.Car.car;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        Mockito.when(carListings.get("car-id")).thenReturn(car);

        ModelAndView modelAndView = carController.viewCarPage("car-id");

        assertThat((Car) modelAndView.getModel().get("car"), is(equalTo(car)));
        assertThat(modelAndView.getViewName(), is(equalTo("view-car")));
    }

}
