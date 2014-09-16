package org.fazz.cars.integration.page

import geb.Page

class AddCarPage extends Page {
    static url = "/add-car"
    static at = { title == "Add Car" }
}
