<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a href="${pageContext.request.contextPath}/add-student" class="btn btn-primary mb-3">
    Create Student
</a>

<form action="${pageContext.request.contextPath}/index" method="get" class="mb-3">
    <div class="form-row">
        <div class="col">
            <input type="text" name="name" class="form-control" placeholder="Name" value="${param.name}">
        </div>
        <div class="col">
            <input type="text" name="phone" class="form-control" placeholder="Phone" value="${param.phone}">
        </div>
        <div class="col">
            <input type="text" name="address" class="form-control" placeholder="Address" value="${param.address}">
        </div>
        <div class="col">
            <button type="submit" class="btn btn-primary">Filter</button>
        </div>
    </div>
</form>

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

<!-- Pagination controls -->
<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        <li class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>'">
            <a class="page-link" href="?page=${currentPage - 1}&name=${param.name}&phone=${param.phone}&address=${param.address}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <c:forEach var="i" begin="1" end="${totalPages}">
            <li class="page-item <c:if test='${currentPage == i}'>active</c:if>'">
                <a class="page-link" href="?page=${i}&name=${param.name}&phone=${param.phone}&address=${param.address}">${i}</a>
            </li>
        </c:forEach>
        <li class="page-item <c:if test='${currentPage == totalPages}'>disabled</c:if>'">
            <a class="page-link" href="?page=${currentPage + 1}&name=${param.name}&phone=${param.phone}&address=${param.address}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>
