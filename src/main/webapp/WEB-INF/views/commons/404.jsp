<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>404 Not Found</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }
        .container { max-width: 600px; margin: 0 auto; }
        h1 { font-size: 3em; color: #333; }
        p { font-size: 1.2em; color: #666; }
    </style>
</head>
<body>
<div class="container">
    <h1>404 - Page Not Found</h1>
    <p>Sorry, the page you are looking for does not exist.</p>
    <a href="${pageContext.request.contextPath}/index">Go to Homepage</a>
</div>
</body>
</html>
