<jsp:include page="../partials/header.jsp"/>

<h2>Login</h2>
<form action="auth" method="post">
    <input type="hidden" name="action" value="signin">
    Username: <input type="text" name="username" required><br>
    Password: <input type="password" name="password" required><br>
    <input type="submit" value="Sign In">
</form>
<p><a href="signup.jsp">Sign Up</a></p>
<c:if test="${param.error != null}">
    <p style="color: red;">${param.error}</p>
</c:if>

<jsp:include page="../partials/footer.jsp"/>