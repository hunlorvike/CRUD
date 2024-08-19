<h2>Edit Student</h2>
<form method="POST" action="edit-student">
    <input type="hidden" name="action" value="${pageContext.request.contextPath}/edit-student"/>
    <input type="hidden" name="csrfToken" value="${requestScope.csrfToken}">
    <input type="hidden" name="id" value="${student.id}"/>
    <div class="form-group">
        <label for="editStudentFullname">Fullname</label>
        <input type="text" class="form-control" id="editStudentFullname" name="fullname" value="${student.fullname}"
               required>
    </div>
    <div class="form-group">
        <label for="editStudentPhone">Phone</label>
        <input type="text" class="form-control" id="editStudentPhone" name="phone" value="${student.phone}" required>
    </div>
    <div class="form-group">
        <label for="editStudentAddress">Address</label>
        <input type="text" class="form-control" id="editStudentAddress" name="address" value="${student.address}"
               required>
    </div>
    <div class="form-group">
        <label for="editStudentPoint">Point</label>
        <input type="number" step="0.01" class="form-control" id="editStudentPoint" name="point"
               value="${student.point}" required>
    </div>
    <button type="submit" class="btn btn-primary">Save Changes</button>
    <a href="${pageContext.request.contextPath}/index" class="btn btn-secondary">Back to List</a>
</form>