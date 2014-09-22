package org.fazz.cars.integration.page

import geb.Page

class SearchCarsPage extends Page {
    static url = "/search-cars"
    static at = { title == "Search Cars" }
}
