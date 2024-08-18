<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .error-page {
            padding: 60px 0;
            text-align: center;
        }
        .error-message {
            font-size: 24px;
            font-weight: bold;
            color: #dc3545;
        }
        .error-details {
            font-size: 16px;
            color: #6c757d;
        }
    </style>
</head>
<body>
<div class="container error-page">
    <h1 class="display-4">Error</h1>
    <p class="error-message">
        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "An unexpected error occurred." %>
    </p>
    <p class="error-details">
        Please try again later or contact support if the problem persists.
    </p>
    <a href="<%= request.getContextPath() %>" class="btn btn-primary">Go to Home</a>
</div>
</body>
</html>
