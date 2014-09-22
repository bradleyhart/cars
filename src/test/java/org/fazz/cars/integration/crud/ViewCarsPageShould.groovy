package org.fazz.cars.integration.crud

import geb.spock.GebReportingSpec
import org.fazz.cars.integration.page.AddCarPage
import org.fazz.cars.integration.page.ViewCarPage
import org.fazz.cars.integration.page.ViewCarsPage
import org.fazz.mongo.MongoDb
import org.fazz.tomcat.WebApplication

class ViewCarsPageShould extends GebReportingSpec {

    def "have title view car"() {
        given:
        WebApplication.isRunning()

        when:
        to ViewCarsPage

        then:
        title == "View Cars"
    }

    def "display all cars that have been added"() {
        given:
        MongoDb.isRunning()
        MongoDb.isEmpty()
        WebApplication.isRunning()

        addCar("Peugeot", "206", "2004", "10000")
        addCar("Dodge", "Viper", "2001", "20000")
        addCar("Renault", "Clio", "1976", "10")

        when:
        to ViewCarsPage

        then:
        $("li").size() == 3
        $("li")[0].find(".make").text() == "Peugeot"
    }

    def addCar(String make, String model, String year, String price) {
        to AddCarPage
        $("form").make() << make
        $("form").model() << model
        $("form").year() << year
        $("form").price() << price
        $("#add").click(ViewCarPage)
    }

}
