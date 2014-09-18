package org.fazz.service;

import org.fazz.model.Car;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;

public class MongoDbCarListings implements CarListings {

    private MongoTemplate mongoTemplate;

    public MongoDbCarListings(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void add(Car car) {
        mongoTemplate.insert(car);
    }

    @Override
    public Car get(String id) {
        return mongoTemplate.findById(id, Car.class);
    }

    @Override
    public List<Car> all() {
        return mongoTemplate.findAll(Car.class);
    }

    public List<Car> match(CarSearch carSearch) {
        return mongoTemplate.find(query(carSearch.toCriteria()), Car.class);
    }

}
