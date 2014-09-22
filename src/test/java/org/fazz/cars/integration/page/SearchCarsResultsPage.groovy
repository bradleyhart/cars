package org.fazz.cars.integration.page

import geb.Page

class SearchCarsResultsPage extends Page {
    static url = "/search-cars-results"
    static at = { title == "Search Cars Results" }
}
