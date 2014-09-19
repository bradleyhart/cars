package org.fazz.service;

import com.mongodb.*;
import org.fazz.model.Car;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        DBCollection cars = mongoTemplate.getCollection("car");
        GroupCommand cmd = new GroupCommand(cars,
                new BasicDBObject("make", 1),
                new BasicDBObject("make", new BasicDBObject("$regex", "^A")),
                new BasicDBObject("count", 0),
                "function(obj,prev) {prev.count++;}",
                null);

        BasicDBList results = (BasicDBList) cars.group(cmd);
        ArrayList<String> makes = new ArrayList<>();
        for (Object result : results) {
            BasicDBObject dbObject = (BasicDBObject) result;
            makes.add((String) dbObject.get("make"));
        }
        return makes;
    }

    public List<Car> match(CarSearch carSearch) {
        return mongoTemplate.find(query(carSearch.toCriteria()), Car.class);
    }

}
