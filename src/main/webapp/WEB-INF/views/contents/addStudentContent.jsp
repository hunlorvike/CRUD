<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Create Student</h2>
<form method="POST" action="add-student">
    <input type="hidden" name="action" value="${pageContext.request.contextPath}/add-student"/>
    <div class="form-group">
        <label for="studentFullname">Fullname</label>
        <input type="text" class="form-control" id="studentFullname" name="fullname" value="${fullname}">
        <c:if test="${not empty errorFullname}">
            <span class="text-danger">${errorFullname}</span>
        </c:if>
    </div>
    <div class="form-group">
        <label for="studentPhone">Phone</label>
        <input type="text" class="form-control" id="studentPhone" name="phone" value="${phone}">
        <c:if test="${not empty errorPhone}">
            <span class="text-danger">${errorPhone}</span>
        </c:if>
    </div>
    <div class="form-group">
        <label for="studentAddress">Address</label>
        <input type="text" class="form-control" id="studentAddress" name="address" value="${address}">
        <c:if test="${not empty errorAddress}">
            <span class="text-danger">${errorAddress}</span>
        </c:if>
    </div>
    <div class="form-group">
        <label for="studentPoint">Point</label>
        <input type="number" step="0.01" class="form-control" id="studentPoint" name="point" value="${point}">
        <c:if test="${not empty errorPointStr}">
            <span class="text-danger">${errorPointStr}</span>
        </c:if>
    </div>
    <button type="submit" class="btn btn-primary">Save</button>
    <a href="${pageContext.request.contextPath}/index" class="btn btn-secondary">Back to List</a>
</form>
