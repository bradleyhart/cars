package org.fazz.service;


import org.fazz.model.Car;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.fazz.model.Car.car;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MongoDbCarListingsTest {

    private MongoDbCarListings mongoDbCarListings;
    private MongoTemplate mongoTemplate;

    @Before
    public void initializeController() {
        mongoTemplate = mock(MongoTemplate.class);
        mongoDbCarListings = new MongoDbCarListings(mongoTemplate);
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
        Mockito.when(mongoTemplate.findById("car-id", Car.class)).thenReturn(expectedCar);

        Car actualCar = mongoDbCarListings.get("car-id");

        assertThat(actualCar, is(equalTo(expectedCar)));
    }

}
