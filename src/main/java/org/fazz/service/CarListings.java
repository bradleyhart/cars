package org.fazz.service;

import org.fazz.model.Car;

import java.util.List;

public interface CarListings {

    void add(Car car);

    Car get(String ca);

    List<Car> all();
}
