package org.fazz.service;

import org.springframework.data.mongodb.core.query.Criteria;

public class CarSearch {

    private String make;
    private String model;
    private Integer year;
    private Integer price;

    public static CarSearch carSearch() {
        return new CarSearch();
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Criteria toCriteria() {
        Criteria criteria = new Criteria();
        if (make != null) {
            criteria.and("make").is(make);
        }
        if (model != null) {
            criteria.and("model").is(model);
        }
        if (price != null) {
            criteria.and("price").is(price);
        }
        if (year != null) {
            criteria.and("year").is(year);
        }
        return criteria;
    }
}
