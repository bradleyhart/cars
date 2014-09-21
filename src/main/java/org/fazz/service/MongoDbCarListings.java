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
        return executeStartsWithGrouping("make", startsWith);
    }

    @Override
    public List<String> model(String startsWith) {
        return executeStartsWithGrouping("model", startsWith);
    }

    @Override
    public List<Integer> year(String startsWith) {
        return executeRangeGrouping("year", Integer.valueOf(padLowerBound(startsWith)), Integer.valueOf(padHigherBound(startsWith)));
    }

    public List<Car> match(CarSearch carSearch) {
        return mongoTemplate.find(query(carSearch.toCriteria()), Car.class);
    }

    private String padLowerBound(String toPad) {
        if (toPad.length() == 1) {
            return toPad + "000";
        } else if (toPad.length() == 2) {
            return toPad + "00";
        } else if (toPad.length() == 3) {
            return toPad + "0";
        } else {
            return toPad;
        }
    }

    private String padHigherBound(String toPad) {
        if (toPad.length() == 1) {
            return toPad + "999";
        } else if (toPad.length() == 2) {
            return toPad + "99";
        } else if (toPad.length() == 3) {
            return toPad + "9";
        } else {
            return toPad;
        }
    }

    private List<Integer> executeRangeGrouping(String field, Integer greaterThan, Integer lessThan) {
        DBCollection cars = mongoTemplate.getCollection("car");
        GroupCommand groupCommand = new GroupCommand(cars,
                new BasicDBObject(field, 1),
                new BasicDBObject("$and", new BasicDBList(){{
                    add(new BasicDBObject(field, new BasicDBObject("$gt", greaterThan)));
                    add(new BasicDBObject(field, new BasicDBObject("$lt", lessThan)));
                }}),
                new BasicDBObject("count", 0),
                "function(obj,prev) {prev.count++;}",
                null);

        BasicDBList results = (BasicDBList) cars.group(groupCommand);
        List<Integer> makes = new ArrayList<>();
        results.forEach((dbObject) -> makes.add(((Double) ((BasicDBObject) dbObject).get(field)).intValue()));
        return makes;
    }

    private List<String> executeStartsWithGrouping(String field, String startsWith) {
        DBCollection cars = mongoTemplate.getCollection("car");
        GroupCommand groupCommand = new GroupCommand(cars,
                new BasicDBObject(field, 1),
                new BasicDBObject(field, new BasicDBObject("$regex", "^" + startsWith)),
                new BasicDBObject("count", 0),
                "function(obj,prev) {prev.count++;}",
                null);

        BasicDBList results = (BasicDBList) cars.group(groupCommand);
        List<String> makes = new ArrayList<>();
        results.forEach((dbObject) -> makes.add((String) ((BasicDBObject) dbObject).get(field)));
        return makes;
    }

}
