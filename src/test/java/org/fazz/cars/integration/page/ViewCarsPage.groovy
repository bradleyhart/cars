package org.fazz.cars.integration.page

import geb.Page

class ViewCarsPage extends Page {
    static url = "/view-cars"
    static at = { title == "View Cars" }
}
