package org.fazz.service;

import org.fazz.model.Car;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

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
    public List<Car> get() {
        return mongoTemplate.findAll(Car.class);
    }

}
