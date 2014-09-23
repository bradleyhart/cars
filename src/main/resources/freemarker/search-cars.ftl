<#import "layout/layout.ftl" as layout>

<@layout.defaultLayout "Search Cars">

<script>
    $(function () {
        $('#make').autocomplete({
            serviceUrl: '${request.contextPath}/make-autocomplete',
            paramName: "make",
            transformResult: function (response) {
                return {
                    suggestions: $.map($.parseJSON(response), function (item) {
                        return { value: item.value, data: item.data };
                    })
                };
            }
        });

        $('#year').autocomplete({
            serviceUrl: '${request.contextPath}/year-autocomplete',
            paramName: "year",
            transformResult: function (response) {
                return {
                    suggestions: $.map($.parseJSON(response), function (item) {
                        return { value: item.value, data: item.data };
                    })
                };
            }
        });

        $('#model').autocomplete({
            serviceUrl: '${request.contextPath}/model-autocomplete',
            paramName: "model",
            transformResult: function (response) {
                return {
                    suggestions: $.map($.parseJSON(response), function (item) {
                        return { value: item.value, data: item.data };
                    })
                };
            }
        });
    })
</script>

<form action="search-cars" method="post">
    <label for="make">Make: </label><input id="make" name="make" type="text"/>
    <label for="model">Model: </label><input id="model" name="model" type="text"/>
    <label for="year">Year: </label><input id="year" name="year" type="text"/>
    <label for="price">Price: </label><input id="price" name="price" type="text"/>
    <input id="search" type="submit" value="Search"/>
</form>
</@layout.defaultLayout>
