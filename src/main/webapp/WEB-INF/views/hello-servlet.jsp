<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <title>Items List</title>
</head>
<body>
<h1>Items List</h1>
<ul>
    <c:forEach var="item" items="${itemList}">
        <li>${item}</li>
    </c:forEach>
</ul>
</body>
</html>
