package org.fazz.controller;


import org.fazz.model.Car;
import org.fazz.repository.CarSearchRepository;
import org.fazz.service.CarListings;
import org.fazz.search.CarSearch;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.fazz.model.Car.car;
import static org.fazz.search.CarSearch.carSearch;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CarControllerTest {

    private CarController carController;
    private CarListings carListings;
    private CarSearchRepository carSearchRepository;

    @Before
    public void initializeController() {
        carListings = mock(CarListings.class);
        carSearchRepository = mock(CarSearchRepository.class);
        carController = new CarController(carListings, carSearchRepository);
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
    public void addCarPageHasCorrectView() {
        ModelAndView modelAndView = carController.addCarPage();

        assertThat(modelAndView.getViewName(), is(equalTo("add-car")));
    }

    @Test
    public void searchCarPageHasCorrectView() {
        ModelAndView modelAndView = carController.searchCarsPage();

        assertThat(modelAndView.getViewName(), is(equalTo("search-cars")));
    }

    @Test
    public void performsSearchAndRedirectsToResults() {
        CarSearch carSearch = carSearch();
        carSearch.setMake("Audi");
        carSearch.setModel("TT");
        carSearch.setYear(2003);
        carSearch.setPrice(30000);

        List<Car> cars = new ArrayList<Car>() {{
            add(car("Audi", "TT", 2003, 30000));
        }};
        when(carListings.match(carSearch)).thenReturn(cars);

        ModelAndView modelAndView = carController.searchCar(carSearch);

        assertThat(modelAndView.getViewName(), is(equalTo("search-cars-results")));
        assertThat((List<Car>) modelAndView.getModel().get("cars"), is(equalTo(cars)));
        verify(carListings).match(carSearch);
    }

    @Test
    public void searchIsLoggedToDatabase() {
        CarSearch carSearch = carSearch();
        carSearch.setMake("Audi");
        carSearch.setModel("TT");
        carSearch.setYear(2003);
        carSearch.setPrice(30000);

        carController.searchCar(carSearch);

        verify(carSearchRepository).save(carSearch);
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
        List<Car> cars = new ArrayList<Car>() {{
            add(car1);
            add(car2);
        }};

        when(carListings.all()).thenReturn(cars);

        ModelAndView modelAndView = carController.viewCars();

        assertThat((ArrayList<Car>) modelAndView.getModel().get("cars"), is(equalTo(cars)));
        assertThat(modelAndView.getViewName(), is(equalTo("view-cars")));
    }

    @Test
    public void autoCompleteMake() {
        when(carListings.make("A")).thenReturn(new ArrayList<String>() {{
            add("Results");
            add("Results2");
        }});

        List<CarController.Suggestion> makes = carController.makes("A");

        assertThat(makes.size(), is(equalTo(2)));
        assertThat(makes.get(0).getData(), is(equalTo(0)));
        assertThat(makes.get(0).getValue(), is(equalTo("Results")));

        assertThat(makes.get(1).getData(), is(equalTo(1)));
        assertThat(makes.get(1).getValue(), is(equalTo("Results2")));
    }

    @Test
    public void autoCompleteModel() {
        when(carListings.model("A")).thenReturn(new ArrayList<String>() {{
            add("Results");
            add("Results2");
        }});

        List<CarController.Suggestion> models = carController.models("A");

        assertThat(models.size(), is(equalTo(2)));
        assertThat(models.get(0).getData(), is(equalTo(0)));
        assertThat(models.get(0).getValue(), is(equalTo("Results")));

        assertThat(models.get(1).getData(), is(equalTo(1)));
        assertThat(models.get(1).getValue(), is(equalTo("Results2")));
    }

    @Test
    public void autoCompleteYear() {
        when(carListings.year("2")).thenReturn(new ArrayList<Integer>() {{
            add(2001);
            add(2002);
        }});

        List<CarController.Suggestion> models = carController.year("2");

        assertThat(models.size(), is(equalTo(2)));
        assertThat(models.get(0).getData(), is(equalTo(0)));
        assertThat(models.get(0).getValue(), is(equalTo("2001")));

        assertThat(models.get(1).getData(), is(equalTo(1)));
        assertThat(models.get(1).getValue(), is(equalTo("2002")));
    }


}
