<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${pageTitle}"/> - CRUD Application</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <div class="collapse navbar-collapse d-flex justify-content-center" id="navbarSupportedContent">
            <ul class="navbar-nav mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/">Home</a>
                </li>

                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/auth?action=signin">Sign In</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/auth?action=signup">Sign Up</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="#">${sessionScope.user.fullname}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/auth?action=signout">Sign Out</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<main class="container">
    <!-- Page content -->
    <jsp:include page="${contentPage}" />
</main>

<!-- Toast Container -->
<div class="toast-container position-absolute top-0 end-0 p-3" style="z-index: 1050;">
    <c:if test="${not empty toastMessage}">
        <div class="toast ${toastType == 'success' ? 'bg-success text-white' : 'bg-danger text-white'}" role="alert"
             aria-live="assertive" aria-atomic="true">
            <div class="toast-body">
                    ${toastMessage}
            </div>
        </div>
    </c:if>
</div>

<footer class="text-center mt-4">
    <p>&copy; 2024 CRUD Application</p>
</footer>

<script>
    $(document).ready(function () {
        const toastElement = $('.toast');
        if (toastElement.length) {
            toastElement.each(function () {
                const toast = new bootstrap.Toast(this, {
                    autohide: true,
                    delay: 5000
                });
                toast.show();

                setTimeout(() => {
                    $(this).remove();
                }, 5000);
            });
        }
    });
</script>

</body>
</html>
