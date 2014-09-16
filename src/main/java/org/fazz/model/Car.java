package org.fazz.model;

public class Car {

    private String id;
    private String make;
    private String model;
    private Integer year;
    private Integer price;

    private Car() {
        // For Spring
    }

    public Car(String make, String model, Integer year, Integer price) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getPrice() {
        return price;
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
}
