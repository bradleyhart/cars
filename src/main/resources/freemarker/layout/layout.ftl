<#macro defaultLayout title="Cars">
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
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