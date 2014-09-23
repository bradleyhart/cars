<#macro defaultLayout title="Cars">
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <script src="/resources/js/jquery.1.10.2.min.js"></script>
    <script src="/resources/js/jquery.autocomplete.min.js"></script>
    <link rel="stylesheet" href="/resources/css/autocomplete.css">
</head>

<body>

<header>
    <nav>
        <ul>
            <li><a href="/view-cars">All</a></li>
            <li><a href="/add-car">Add</a></li>
            <li><a href="/search-cars">Search</a></li>
        </ul>
    </nav>
</header>

<article>
    <#nested />
</article>

</body>

</html>
</#macro>