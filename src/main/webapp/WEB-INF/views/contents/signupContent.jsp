<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2 class="mb-4">Sign Up</h2>
<form action="auth" method="post">
    <input type="hidden" name="action" value="signup">
    <div class="mb-3">
        <label for="name" class="form-label">Name</label>
        <input type="text" class="form-control" id="name" name="name" value="${name}" required>
        <c:if test="${not empty errorName}">
            <span class="text-danger">${errorName}</span>
        </c:if>
    </div>
    <div class="mb-3">
        <label for="username" class="form-label">Username</label>
        <input type="text" class="form-control" id="username" name="username" value="${username}" required>
        <c:if test="${not empty errorUsername}">
            <span class="text-danger">${errorUsername}</span>
        </c:if>
    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input type="password" class="form-control" id="password" name="password" required>
        <c:if test="${not empty errorPassword}">
            <span class="text-danger">${errorPassword}</span>
        </c:if>
    </div>
    <button type="submit" class="btn btn-primary">Sign Up</button>
</form>
<p class="mt-3"><a href="${pageContext.request.contextPath}/auth?action=signin">Back to Login</a></p>

