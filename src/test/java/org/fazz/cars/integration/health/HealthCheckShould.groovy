package org.fazz.cars.integration.health

import org.fazz.tomcat.WebApplication
import spock.lang.Specification

class HealthCheckShould extends Specification {

    def "application can start in a tomcat 7 container"(){
        given:
        WebApplication.isRunning()

        when:
        def response = new URL("http://localhost:9090/health").text

        then:
        response == "I am running"
    }


}
