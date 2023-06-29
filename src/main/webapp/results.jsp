<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
</head>
<body>

<h2>Search results</h2>
<h3>Definitions:</h3>
<ul>
    <c:forEach items="${definitions}" var="definition">
        <li>${definition}</li>
    </c:forEach>
</ul>
<h3>Results:</h3>
<ul>
    <c:forEach items="${results}" var="result">
        <li>${result}</li>
    </c:forEach>
</ul>
</body>
</html>