package org.fazz.service;


import org.fazz.model.Car;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.fazz.model.Car.car;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class MongoDbCarListingsTest {

    private MongoDbCarListings mongoDbCarListings;
    private MongoTemplate mongoTemplate;

    @Before
    public void initializeController() {
        mongoTemplate = mock(MongoTemplate.class);
        mongoDbCarListings = new MongoDbCarListings(mongoTemplate);
    }

    @Test
    public void getAllCarsFromMongo() {
        final Car car1 = car("Audi", "A6", 2004, 40000);
        final Car car2 = car("Jaguar", "X6", 2005, 30000);
        final Car car3 = car("Nissan", "ZX", 2006, 10000);
        List<Car> expectedCars = new ArrayList<Car>(){{
            add(car1);
            add(car2);
            add(car3);
        }};

        when(mongoTemplate.findAll(Car.class)).thenReturn(expectedCars);

        List<Car> actualCars = mongoDbCarListings.all();

        assertThat(actualCars, is(equalTo(expectedCars)));
    }

    @Test
    public void addsCarToMongo() {
        Car car = car("Audi", "A6", 2004, 40000);

        mongoDbCarListings.add(car);

        verify(mongoTemplate).insert(car);
    }

    @Test
    public void getsCarFromMongo() {
        Car expectedCar = car("Audi", "A6", 2004, 40000);
        when(mongoTemplate.findById("car-id", Car.class)).thenReturn(expectedCar);

        Car actualCar = mongoDbCarListings.get("car-id");

        assertThat(actualCar, is(equalTo(expectedCar)));
    }


}
