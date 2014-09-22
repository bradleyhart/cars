package org.fazz.cars.integration.page

import geb.Page

class AddCarPage extends Page {
    static url = "/add-car"
    static at = { title == "Add Car" }

    def addCar(make, model, year, price){
        $("form").make() << make
        $("form").model() << model
        $("form").year() << year
        $("form").price() << price
        $("#add").click(ViewCarPage)
    }
}
