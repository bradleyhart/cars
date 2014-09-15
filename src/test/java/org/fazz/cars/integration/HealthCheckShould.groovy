package org.fazz.cars.integration

import org.fazz.tomcat.EmbeddedTomcat
import spock.lang.Specification

class HealthCheckShould extends Specification {

    def "application can start in a tomcat 7 container"(){
        given:
        new EmbeddedTomcat().start()

        when:
        def response = new URL("http://localhost:8080/health").text

        then:
        response == "I am running"
    }


}
