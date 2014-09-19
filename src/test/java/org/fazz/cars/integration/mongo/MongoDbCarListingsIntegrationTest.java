package org.fazz.cars.integration.mongo;

import org.fazz.model.Car;
import org.fazz.mongo.MongoDb;
import org.fazz.service.CarSearch;
import org.fazz.service.MongoDbCarListings;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


import java.util.List;

import static org.fazz.model.Car.car;
import static org.fazz.service.CarSearch.carSearch;
import static org.hamcrest.CoreMatchers.is;

public class MongoDbCarListingsIntegrationTest {


    private MongoDbCarListings mongoDbCarListings;

    @Before
    public void startMongo() {
        MongoDb.isRunning();
        MongoDb.isEmpty();
    }

    @Before
    public void initializeController() {
        mongoDbCarListings = new MongoDbCarListings(MongoDb.getMongoTemplate());
    }

    @Test
    public void findCarThatMatchesExactly() {
        Car audi = car("Audi", "A6", 2004, 40000);
        mongoDbCarListings.add(audi);
        mongoDbCarListings.add(car("Jaguar", "X6", 2005, 30000));
        mongoDbCarListings.add(car("Nissan", "ZX", 2006, 10000));

        CarSearch search = carSearch();
        search.setModel("A6");
        search.setMake("Audi");
        search.setYear(2004);
        search.setPrice(40000);

        List<Car> cars = mongoDbCarListings.match(search);

        assertThat(cars.size(), is(1));
        assertThat(cars.get(0), is(audi));
    }

    @Test
    public void findCarThatMatchesMultiple() {
        Car audi1 = car("Audi", "A6", 2004, 40000);
        Car audi2 = car("Audi", "A6", 2004, 40000);
        mongoDbCarListings.add(audi1);
        mongoDbCarListings.add(audi2);
        mongoDbCarListings.add(car("Jaguar", "X6", 2005, 30000));
        mongoDbCarListings.add(car("Nissan", "ZX", 2006, 10000));

        CarSearch search = carSearch();
        search.setModel("A6");
        search.setMake("Audi");
        search.setYear(2004);
        search.setPrice(40000);

        List<Car> cars = mongoDbCarListings.match(search);

        assertThat(cars.size(), is(2));
        assertThat(cars.get(0), is(audi1));
        assertThat(cars.get(1), is(audi2));
    }

    @Test
    public void findCarThatMatchesMakeCriteria() {
        Car audi = car("Audi", "A6", 2004, 40000);
        mongoDbCarListings.add(audi);
        mongoDbCarListings.add(car("Jaguar", "X6", 2005, 30000));
        mongoDbCarListings.add(car("Nissan", "ZX", 2006, 10000));

        CarSearch search = carSearch();
        search.setMake("Audi");

        List<Car> cars = mongoDbCarListings.match(search);

        assertThat(cars.size(), is(1));
        assertThat(cars.get(0), is(audi));
    }

    @Test
    public void findCarThatMatchesModelCriteria() {
        Car audi = car("Audi", "A6", 2004, 40000);
        mongoDbCarListings.add(audi);
        mongoDbCarListings.add(car("Jaguar", "X6", 2005, 30000));
        mongoDbCarListings.add(car("Nissan", "ZX", 2006, 10000));

        CarSearch search = carSearch();
        search.setModel("A6");

        List<Car> cars = mongoDbCarListings.match(search);

        assertThat(cars.size(), is(1));
        assertThat(cars.get(0), is(audi));
    }

    @Test
    public void findCarThatMatchesPriceCriteria() {
        Car audi = car("Audi", "A6", 2004, 40000);
        mongoDbCarListings.add(audi);
        mongoDbCarListings.add(car("Jaguar", "X6", 2005, 30000));
        mongoDbCarListings.add(car("Nissan", "ZX", 2006, 10000));

        CarSearch search = carSearch();
        search.setPrice(40000);

        List<Car> cars = mongoDbCarListings.match(search);

        assertThat(cars.size(), is(1));
        assertThat(cars.get(0), is(audi));
    }

    @Test
    public void findCarThatMatchesYearCriteria() {
        Car audi = car("Audi", "A6", 2004, 40000);
        mongoDbCarListings.add(audi);
        mongoDbCarListings.add(car("Jaguar", "X6", 2005, 30000));
        mongoDbCarListings.add(car("Nissan", "ZX", 2006, 10000));

        CarSearch search = carSearch();
        search.setYear(2004);

        List<Car> cars = mongoDbCarListings.match(search);

        assertThat(cars.size(), is(1));
        assertThat(cars.get(0), is(audi));
    }

    @Test
    public void findCarThatMatchesCombinations() {
        Car audi = car("Audi", "A6", 2004, 40000);
        mongoDbCarListings.add(audi);
        mongoDbCarListings.add(car("Jaguar", "X6", 2005, 30000));
        mongoDbCarListings.add(car("Nissan", "ZX", 2006, 10000));

        CarSearch search = carSearch();
        search.setYear(2004);
        search.setModel("A6");

        List<Car> cars = mongoDbCarListings.match(search);

        assertThat(cars.size(), is(1));
        assertThat(cars.get(0), is(audi));
    }

    @Test
    public void findNoCarsWhenOneCriteriaDoesNotMatch() {
        Car audi = car("Audi", "A6", 2004, 40000);
        mongoDbCarListings.add(audi);
        mongoDbCarListings.add(car("Jaguar", "X6", 2005, 30000));
        mongoDbCarListings.add(car("Nissan", "ZX", 2006, 10000));

        CarSearch search = carSearch();
        search.setYear(2004);
        search.setModel("A10");

        List<Car> cars = mongoDbCarListings.match(search);

        assertThat(cars.size(), is(0));
    }

    @Test
    public void findAllMakesStartingWith() {
        mongoDbCarListings.add(car("Audi", "A6", 1920, 40000));
        mongoDbCarListings.add(car("Audi", "A6", 2004, 40000));
        mongoDbCarListings.add(car("Austin Martin", "34", 2005, 30000));
        mongoDbCarListings.add(car("Jaguar", "X6", 2005, 30000));
        mongoDbCarListings.add(car("Nissan", "ZX", 2006, 10000));

        List<String> makes = mongoDbCarListings.make("A");

        assertThat(makes.size(), is(2));
        assertThat(makes.get(0), is(equalTo("Audi")));
        assertThat(makes.get(1), is(equalTo("Austin Martin")));
    }

    @Test
    public void findAllMakesStartingWithMorePrecise() {
        mongoDbCarListings.add(car("Audi", "A6", 1920, 40000));
        mongoDbCarListings.add(car("Audi", "A6", 2004, 40000));
        mongoDbCarListings.add(car("Austin Martin", "34", 2005, 30000));
        mongoDbCarListings.add(car("Jaguar", "X6", 2005, 30000));
        mongoDbCarListings.add(car("Nissan", "ZX", 2006, 10000));

        List<String> makes = mongoDbCarListings.make("Aud");

        assertThat(makes.size(), is(1));
        assertThat(makes.get(0), is(equalTo("Audi")));
    }

    @Test
    public void findAllModelStartingWith() {
        mongoDbCarListings.add(car("Audi", "A6", 1920, 40000));
        mongoDbCarListings.add(car("Audi", "A10", 2004, 40000));
        mongoDbCarListings.add(car("Austin Martin", "34", 2005, 30000));
        mongoDbCarListings.add(car("Jaguar", "X6", 2005, 30000));
        mongoDbCarListings.add(car("Nissan", "ZX", 2006, 10000));

        List<String> makes = mongoDbCarListings.model("A");

        assertThat(makes.size(), is(2));
        assertThat(makes.get(0), is(equalTo("A6")));
        assertThat(makes.get(1), is(equalTo("A10")));
    }

    @Test
    public void findAllModelStartingWithMorePrecise() {
        mongoDbCarListings.add(car("Audi", "A102", 1920, 40000));
        mongoDbCarListings.add(car("Audi", "A101", 2004, 40000));
        mongoDbCarListings.add(car("Austin Martin", "34", 2005, 30000));
        mongoDbCarListings.add(car("Jaguar", "X6", 2005, 30000));
        mongoDbCarListings.add(car("Nissan", "ZX", 2006, 10000));

        List<String> makes = mongoDbCarListings.model("A10");

        assertThat(makes.size(), is(2));
        assertThat(makes.get(0), is(equalTo("A102")));
        assertThat(makes.get(1), is(equalTo("A101")));
    }


}
