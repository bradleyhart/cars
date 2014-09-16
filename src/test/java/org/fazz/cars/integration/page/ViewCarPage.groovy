package org.fazz.cars.integration.page

import geb.Page

class ViewCarPage extends Page {
    static url = "/view-car"
    static at = { title == "View Car" }
}
