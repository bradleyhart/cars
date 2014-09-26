<#macro defaultLayout title="Cars">
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <script src="${request.contextPath}/resources/js/jquery.1.10.2.min.js"></script>
    <script src="${request.contextPath}/resources/js/jquery.autocomplete.min.js"></script>
    <link rel="stylesheet" href="${request.contextPath}/resources/css/autocomplete.css">
</head>

<body>

<header>
    <nav>
        <ul>
            <li><a href="${request.contextPath}/view-cars">All</a></li>
            <li><a href="${request.contextPath}/add-car">Add</a></li>
            <li><a href="${request.contextPath}/search-cars">Search</a></li>
        </ul>
    </nav>
</header>

<article>
    <#nested />
</article>

</body>

</html>
</#macro>