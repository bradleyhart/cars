package org.fazz.service;

import com.mongodb.*;
import org.fazz.model.Car;
import org.fazz.session.CarSearch;
import org.fazz.util.MongoDriverFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static org.fazz.util.MongoDriverFactory.*;
import static org.fazz.util.YearPadder.padYearHigherBound;
import static org.fazz.util.YearPadder.padYearLowerBound;
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
        return executeRangeGrouping("year",
                Integer.valueOf(padYearLowerBound(startsWith)),
                Integer.valueOf(padYearHigherBound(startsWith)));
    }

    @Override
    public List<Car> match(CarSearch carSearch) {
        return mongoTemplate.find(query(carSearch.toCriteria()), Car.class);
    }

    private List<Integer> executeRangeGrouping(String field, Integer greaterThan, Integer lessThan) {
        return MongoDriverFactory.<Double>listResults(field, group(carCollection(), groupCommand(carCollection(),
                declareKey(field),
                and(
                        dbObject(field, dbObject("$gte", greaterThan)),
                        dbObject(field, dbObject("$lte", lessThan))
                ),
                EMPTY_INITIAL,
                EMPTY_REDUCE,
                EMPTY_FINIALIZE))).stream().map(Double::intValue).collect(Collectors.toList());
    }

    private List<String> executeStartsWithGrouping(String field, String startsWith) {
        return listResults(field, group(carCollection(), groupCommand(carCollection(),
                declareKey(field),
                dbObject(field, dbObject("$regex", "^" + startsWith)),
                EMPTY_INITIAL,
                EMPTY_REDUCE,
                EMPTY_FINIALIZE)));
    }

    private DBCollection carCollection() {
        return mongoTemplate.getCollection("car");
    }

}
