<#import "layout/layout.ftl" as layout>

<@layout.defaultLayout "View Cars">
<ul>
    <#list cars as car>
        <li>
            <dl>
                <dt>Make</dt>
                <dd class="make">${car.make}</dd>

                <dt>Model</dt>
                <dd class="model">${car.model}</dd>

                <dt>Year</dt>
                <dd class="year">${car.year?c}</dd>

                <dt>Price</dt>
                <dd class="price">${car.price}</dd>
            </dl>
        </li>
    </#list>
</ul>
</@layout.defaultLayout>
