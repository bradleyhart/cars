package org.fazz.cars.integration.crud

import geb.spock.GebReportingSpec
import org.fazz.cars.integration.page.AddCarPage
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



}
