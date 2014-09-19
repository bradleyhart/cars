package org.fazz.service;

import com.mongodb.*;
import org.fazz.model.Car;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
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

    @Override
    public List<String> make(String startsWith) {
        return exeuteStartsWithGroup(startsWith, "make");
    }

    @Override
    public List<String> model(String startsWith) {
        return exeuteStartsWithGroup(startsWith, "model");
    }

    private List<String> exeuteStartsWithGroup(String startsWith, String field) {
        DBCollection cars = mongoTemplate.getCollection("car");
        GroupCommand cmd = new GroupCommand(cars,
                new BasicDBObject(field, 1),
                new BasicDBObject(field, new BasicDBObject("$regex", "^" + startsWith)),
                new BasicDBObject("count", 0),
                "function(obj,prev) {prev.count++;}",
                null);

        BasicDBList results = (BasicDBList) cars.group(cmd);
        ArrayList<String> makes = new ArrayList<>();
        results.forEach((dbObject) -> makes.add((String) ((BasicDBObject) dbObject).get(field)));
        return makes;
    }

    public List<Car> match(CarSearch carSearch) {
        return mongoTemplate.find(query(carSearch.toCriteria()), Car.class);
    }

}
