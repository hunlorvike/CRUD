<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a href="${pageContext.request.contextPath}/add-student" class="btn btn-primary mb-3">
    Create Student
</a>

<table class="table">
    <thead>
    <tr>
        <th>ID</th>
        <th>Fullname</th>
        <th>Phone</th>
        <th>Address</th>
        <th>Point</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${not empty students}">
            <c:forEach var="student" items="${students}">
                <tr>
                    <td>${student.id}</td>
                    <td>${student.fullname}</td>
                    <td>${student.phone}</td>
                    <td>${student.address}</td>
                    <td>${student.point}</td>
                    <td>
                        <a class="btn btn-warning" href="${pageContext.request.contextPath}/edit-student?id=${student.id}">
                            Edit
                        </a>
                        <a class="btn btn-danger"
                           href="${pageContext.request.contextPath}/delete-student?id=${student.id}">
                            Delete
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="6" class="text-center">No students found</td>
            </tr>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>
