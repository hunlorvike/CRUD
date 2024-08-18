<h2>Delete Student</h2>
<form method="POST" action="${pageContext.request.contextPath}/delete-student">
    <input type="hidden" name="id" value="${student.id}"/>
    <div class="form-group">
        <p>Are you sure you want to delete the student <strong>${student.fullname}</strong>?</p>
    </div>
    <button type="submit" class="btn btn-danger">Delete</button>
    <a href="${pageContext.request.contextPath}/index" class="btn btn-secondary">Back to List</a>
</form>