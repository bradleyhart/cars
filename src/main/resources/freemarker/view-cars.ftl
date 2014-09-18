<!DOCTYPE html>
<html>
<head>
    <title>View Cars</title>
</head>

<body>
<ul>
<#list cars as car>
    <li>
        <dl>
            <dt>Make</dt>
            <dd class="make">${car.make}</dd>

            <dt>Model</dt>
            <dd class="model">${car.model}</dd>

            <dt>Year</dt>
            <dd class="year">${car.year}</dd>

            <dt>Price</dt>
            <dd class="price">${car.price}</dd>
        </dl>
    </li>
</#list>
</ul>

</body>
</html>