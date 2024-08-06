<jsp:include page="../partials/header.jsp"/>

<!-- Button to Open Create Modal -->
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createStudentModal">
    Create Student
</button>

<table class="table">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Address</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <!-- Iterate over students and create rows -->
    <c:forEach var="student" items="${students}">
        <tr>
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.email}</td>
            <td>${student.address}</td>
            <td>
                <button type="button" class="btn btn-warning" onclick="showEditModal(${student.id})">Edit</button>
                <button type="button" class="btn btn-danger" onclick="showDeleteModal(${student.id})">Delete</button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<!-- Create Student Modal -->
<div class="modal fade" id="createStudentModal" tabindex="-1" role="dialog" aria-labelledby="createStudentModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createStudentModalLabel">Create Student</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="createStudentForm" method="post" action="createStudent">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="studentName">Name</label>
                        <input type="text" class="form-control" id="studentName" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="studentEmail">Email</label>
                        <input type="email" class="form-control" id="studentEmail" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="studentAddress">Address</label>
                        <input type="text" class="form-control" id="studentAddress" name="address" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Edit Student Modal -->
<div class="modal fade" id="editStudentModal" tabindex="-1" role="dialog" aria-labelledby="editStudentModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editStudentModalLabel">Edit Student</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="editStudentForm" method="post" action="editStudent">
                <input type="hidden" id="editStudentId" name="id">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="editStudentName">Name</label>
                        <input type="text" class="form-control" id="editStudentName" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="editStudentEmail">Email</label>
                        <input type="email" class="form-control" id="editStudentEmail" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="editStudentAddress">Address</label>
                        <input type="text" class="form-control" id="editStudentAddress" name="address" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Delete Student Modal -->
<div class="modal fade" id="deleteStudentModal" tabindex="-1" role="dialog" aria-labelledby="deleteStudentModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteStudentModalLabel">Delete Student</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="deleteStudentForm" method="post" action="deleteStudent">
                <input type="hidden" id="deleteStudentId" name="id">
                <div class="modal-body">
                    <p>Are you sure you want to delete this student?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-danger">Delete</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../partials/footer.jsp"/>