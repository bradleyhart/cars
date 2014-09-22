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
        $(".year").text() == "2,014"
        $(".price").text() == "30,000"
    }

}
