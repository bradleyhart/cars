package org.fazz.cars.integration.crud

import geb.spock.GebReportingSpec
import org.fazz.cars.integration.page.AddCarPage
import org.fazz.cars.integration.page.SearchCarsPage
import org.fazz.cars.integration.page.SearchCarsResultsPage
import org.fazz.cars.integration.page.ViewCarPage
import org.fazz.mongo.MongoDb
import org.fazz.tomcat.WebApplication

class SearchCarsPageShould extends GebReportingSpec {

    def "have title search cars"() {
        given:
        WebApplication.isRunning()

        when:
        to SearchCarsPage

        then:
        title == "Search Cars"
    }

    def "have title input fields for make, model, year, price and search"() {
        given:
        WebApplication.isRunning()

        when:
        to SearchCarsPage

        then:
        $("form").find("input", id: "make")?.@type == "text"
        $("form").find("input", id: "model")?.@type == "text"
        $("form").find("input", id: "year")?.@type == "text"
        $("form").find("input", id: "price")?.@type == "text"
        $("form").find("input", id: "search")?.@type == "submit"
    }

    def "can search cars and show results"() {
        given:
        MongoDb.isRunning()
        MongoDb.isEmpty()
        WebApplication.isRunning()

        to AddCarPage
        $("form").make() << "Peugeot"
        $("form").model() << "206"
        $("form").year() << "2014"
        $("form").price() << "30000"
        $("#add").click(ViewCarPage)

        when:
        to SearchCarsPage
        $("form").make() << "Peugeot"
        $("form").model() << "206"
        $("form").year() << "2014"
        $("form").price() << "30000"
        $("#search").click(SearchCarsResultsPage)

        then:
        $(".make").text() == "Peugeot"
        $(".model").text() == "206"
        $(".year").text() == "2014"
        $(".price").text() == "30,000"
    }

    def "can search cars and show multiple results"() {
        given:
        MongoDb.isRunning()
        MongoDb.isEmpty()
        WebApplication.isRunning()

        to AddCarPage
        addCar("Peugeot", "206", "2014", "30000")
        to AddCarPage
        addCar("Peugeot", "407", "2012", "2000")
        to AddCarPage
        addCar("Renault", "Clio", "2011", "2000")

        when:
        to SearchCarsPage
        $("form").make() << "Peugeot"
        $("#search").click(SearchCarsResultsPage)

        then:
        def results = $(".car")
        results.size() == 2
        results[0].find(".make").text() == "Peugeot"
        results[0].find(".model").text() == "206"
        results[0].find(".year").text() == "2014"
        results[0].find(".price").text() == "30,000"

        results[1].find(".make").text() == "Peugeot"
        results[1].find(".model").text() == "407"
        results[1].find(".year").text() == "2012"
        results[1].find(".price").text() == "2,000"
    }

    def "can search using model"() {
        given:
        MongoDb.isRunning()
        MongoDb.isEmpty()
        WebApplication.isRunning()

        to AddCarPage
        addCar("Peugeot", "206", "2014", "30000")
        to AddCarPage
        addCar("Peugeot", "407", "2012", "2000")

        when:
        to SearchCarsPage
        $("form").model() << "206"
        $("#search").click(SearchCarsResultsPage)

        then:
        def results = $(".car")
        results.size() == 1
        results[0].find(".make").text() == "Peugeot"
        results[0].find(".model").text() == "206"
        results[0].find(".year").text() == "2014"
        results[0].find(".price").text() == "30,000"
    }

    def "can search using price"() {
        given:
        MongoDb.isRunning()
        MongoDb.isEmpty()
        WebApplication.isRunning()

        to AddCarPage
        addCar("Peugeot", "206", "2014", "30000")
        to AddCarPage
        addCar("Peugeot", "407", "2012", "2000")

        when:
        to SearchCarsPage
        $("form").price() << "30000"
        $("#search").click(SearchCarsResultsPage)

        then:
        def results = $(".car")
        results.size() == 1
        results[0].find(".make").text() == "Peugeot"
        results[0].find(".model").text() == "206"
        results[0].find(".year").text() == "2014"
        results[0].find(".price").text() == "30,000"
    }

    def "can search using year"() {
        given:
        MongoDb.isRunning()
        MongoDb.isEmpty()
        WebApplication.isRunning()

        to AddCarPage
        addCar("Peugeot", "206", "2014", "30000")
        to AddCarPage
        addCar("Peugeot", "407", "2012", "2000")

        when:
        to SearchCarsPage
        $("form").year() << "2014"
        $("#search").click(SearchCarsResultsPage)

        then:
        def results = $(".car")
        results.size() == 1
        results[0].find(".make").text() == "Peugeot"
        results[0].find(".model").text() == "206"
        results[0].find(".year").text() == "2014"
        results[0].find(".price").text() == "30,000"
    }

    def "can search using combinations"() {
        given:
        MongoDb.isRunning()
        MongoDb.isEmpty()
        WebApplication.isRunning()

        to AddCarPage
        addCar("Peugeot", "206", "2014", "30000")
        to AddCarPage
        addCar("Peugeot", "407", "2012", "2000")
        to AddCarPage
        addCar("Peugeot", "507", "2014", "2000")


        when:
        to SearchCarsPage
        $("form").year() << "2014"
        $("form").model() << "206"
        $("#search").click(SearchCarsResultsPage)

        then:
        def results = $(".car")
        results.size() == 1
        results[0].find(".make").text() == "Peugeot"
        results[0].find(".model").text() == "206"
        results[0].find(".year").text() == "2014"
        results[0].find(".price").text() == "30,000"
    }


}
