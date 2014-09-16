package org.fazz.cars.integration.crud

import geb.spock.GebReportingSpec
import org.fazz.cars.integration.page.AddCarPage
import org.fazz.cars.integration.page.ViewCarPage
import org.fazz.tomcat.WebApplication

class AddCarsPageShould extends GebReportingSpec {

    def "have title add car"() {
        given:
        WebApplication.isRunning()

        when:
        to AddCarPage

        then:
        title == "Add Car"
    }

    def "have title input fields for make, model, year, price and submit"() {
        given:
        WebApplication.isRunning()

        when:
        to AddCarPage

        then:
        $("form").find("input", id: "make")?.@type == "text"
        $("form").find("input", id: "model")?.@type == "text"
        $("form").find("input", id: "year")?.@type == "text"
        $("form").find("input", id: "price")?.@type == "text"
        $("form").find("input", id: "add")?.@type == "submit"
    }

    def "can submit information and get redirected"() {
        given:
        WebApplication.isRunning()

        when:
        to AddCarPage
        $("form").make() << "Peugeot"
        $("form").model() << "206"
        $("form").year() << "2014"
        $("form").price() << "30000"
        $("#add").click(ViewCarPage)

        then:
        $("#make").text() == "Peugeot"
        $("#model").text() == "206"
        $("#year").text() == "2,014"
        $("#price").text() == "30,000"
    }

}
