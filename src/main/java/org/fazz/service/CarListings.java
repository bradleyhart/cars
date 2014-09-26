package org.fazz.service;

import org.fazz.model.Car;
import org.fazz.search.CarSearch;

import java.util.List;

public interface CarListings {

    void add(Car car);

    Car get(String ca);

    List<Car> all();

    List<String> make(String startsWith);

    List<String> model(String startsWith);

    List<Integer> year(String startsWith);

    List<Car> match(CarSearch carSearch);
}
