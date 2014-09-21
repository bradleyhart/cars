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
        return executeRangeGrouping("year", Integer.valueOf(padYearLowerBound(startsWith)), Integer.valueOf(padYearHigherBound(startsWith)));
    }

    public List<Car> match(CarSearch carSearch) {
        return mongoTemplate.find(query(carSearch.toCriteria()), Car.class);
    }

    private String padYearLowerBound(String toPad) {
        return padYear(toPad, '0');
    }

    private String padYearHigherBound(String toPad) {
        return padYear(toPad, '9');
    }

    private String padYear(String toPad, char padChar) {
        switch (toPad.length()) {
            case 1: return toPad + padChar + padChar + padChar;
            case 2: return toPad + padChar + padChar;
            case 3: return toPad + padChar;
            case 4: return toPad;
            default: throw new IllegalArgumentException("Year must be valid");
        }
    }

    private List<Integer> executeRangeGrouping(String field, Integer greaterThan, Integer lessThan) {
        DBCollection cars = mongoTemplate.getCollection("car");
        GroupCommand groupCommand = new GroupCommand(cars,
                dbObject(field, 1),
                dbObject("$and", dbList(
                    dbObject(field, dbObject("$gt", greaterThan)),
                    dbObject(field, dbObject("$lt", lessThan))
                )),
                dbObject("count", 0),
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
                dbObject(field, 1),
                dbObject(field, dbObject("$regex", "^" + startsWith)),
                dbObject("count", 0),
                "function(obj,prev) {prev.count++;}",
                null);

        BasicDBList results = (BasicDBList) cars.group(groupCommand);
        List<String> makes = new ArrayList<>();
        results.forEach((dbObject) -> makes.add((String) ((BasicDBObject) dbObject).get(field)));
        return makes;
    }
    
    private static BasicDBObject dbObject(String key, Object object){
        return new BasicDBObject(key, object);
    }

    private static BasicDBList dbList(BasicDBObject... objects){
        return new BasicDBList(){{
            for (BasicDBObject object : objects) {
                add(object);
            }
        }};
    }

}
