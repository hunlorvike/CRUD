<%--
  Created by IntelliJ IDEA.
  User: Nitro
  Date: 8/16/2024
  Time: 8:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<h2>Sign Up</h2>
<form action="auth" method="post">
    <input type="hidden" name="action" value="signup">
    Name: <input type="text" name="name" required><br>
    Username: <input type="text" name="username" required><br>
    Password: <input type="password" name="password" required><br>
    <input type="submit" value="Sign Up">
</form>
<p><a href="login.jsp">Back to Login</a></p>
<c:if test="${param.message != null}">
    <p style="color: green;">${param.message}</p>
</c:if>
</body>
</html>
