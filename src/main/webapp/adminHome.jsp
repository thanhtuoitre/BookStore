<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Bảng điều khiển quản trị</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
body {
  background-color: #121212;
  color: #f1f1f1;
}

.nav-tabs .nav-link.active {
  background-color: #f9d342;
  color: #000 !important;
  font-weight: bold;
}

h2, h4 {
  color: #f9d342;
}

.table-dark {
  background-color: #1e1e1e !important;
  color: #f9d342 !important;
}

.table td, .table th {
  vertical-align: middle;
}

.btn-success {
  background-color: #28a745;
  border: none;
}

.btn-warning {
  background-color: #ffc107;
  color: black;
  border: none;
}

.btn-danger {
  background-color: #dc3545;
  border: none;
}

.btn:hover {
  opacity: 0.85;
}

.action-group .btn {
  min-width: 110px;
  font-weight: 500;
}

tr.selected {
  background-color: #f9d342 !important;
  color: #000 !important;
  font-weight: bold;
}

tr:hover {
  background-color: #333;
  cursor: pointer;
}
</style>
</head>

<body class="container mt-4">

  <h2 class="mb-4 text-center fw-bold">📊 Bảng điều khiển quản trị</h2>

  <!-- Tabs -->
  <ul class="nav nav-tabs mb-4" id="adminTabs">
    <li class="nav-item"><a class="nav-link active" data-bs-toggle="tab" href="#books">📚 Sách</a></li>
    <li class="nav-item"><a class="nav-link" data-bs-toggle="tab" href="#users">👤 Người dùng</a></li>
  </ul>

  <div class="tab-content">

    <!-- TAB SÁCH -->
    <div class="tab-pane fade show active" id="books">
      <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="m-0">📚 Danh sách sách</h4>
        <div class="action-group">
          <a href="adminHome?action=create" class="btn btn-success me-2">+ Thêm</a>
          <button id="btnEditBook" class="btn btn-warning me-2" disabled>✏️ Sửa</button>
          <button id="btnDeleteBook" class="btn btn-danger" disabled>🗑️ Xóa</button>
        </div>
      </div>

      <table id="bookTable" class="table table-bordered table-striped align-middle">
        <thead class="table-dark text-center">
          <tr>
            <th>ID</th>
            <th>Tiêu đề</th>
            <th>Tác giả</th>
            <th>Giá</th>
            <th>Ảnh</th>
            <th>Số lượng</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="b" items="${bookList}">
            <tr data-id="${b.bookId}">
              <td>${b.bookId}</td>
              <td>${b.title}</td>
              <td>${b.author}</td>
              <td>${b.price}</td>
              <td class="text-center"><img src="${b.imagePath}" width="60"></td>
              <td>${b.amount}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <!-- TAB NGƯỜI DÙNG -->
    <div class="tab-pane fade" id="users">
      <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="m-0">👤 Danh sách người dùng</h4>
        <div class="action-group">
          <a href="adminHome?action=createUser" class="btn btn-success me-2">+ Thêm</a>
          <button id="btnEditUser" class="btn btn-warning me-2" disabled>✏️ Sửa</button>
          <button id="btnDeleteUser" class="btn btn-danger" disabled>🗑️ Xóa</button>
        </div>
      </div>

      <table id="userTable" class="table table-bordered table-striped align-middle">
        <thead class="table-dark text-center">
          <tr>
            <th>ID</th>
            <th>Tên đăng nhập</th>
            <th>Email</th>
            <th>Họ tên</th>
            <th>Vai trò</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="u" items="${userList}">
            <tr data-id="${u.userId}">
              <td>${u.userId}</td>
              <td>${u.username}</td>
              <td>${u.email}</td>
              <td>${u.fullName}</td>
              <td>${u.role}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

  <!-- Script xử lý chọn hàng -->
  <script>
  function enableRowSelection(tableId, editBtnId, deleteBtnId, editUrl, deleteUrl) {
    const table = document.getElementById(tableId);
    const btnEdit = document.getElementById(editBtnId);
    const btnDelete = document.getElementById(deleteBtnId);
    let selectedId = null;

    if (!table) return;

    table.querySelectorAll("tbody tr").forEach(row => {
      row.addEventListener("click", function() {
        table.querySelectorAll("tr").forEach(r => r.classList.remove("selected"));
        this.classList.add("selected");
        selectedId = this.getAttribute("data-id");
        btnEdit.disabled = false;
        btnDelete.disabled = false;
      });
    });

    btnEdit.addEventListener("click", function() {
      if (selectedId) window.location.href = editUrl + selectedId;
    });

    btnDelete.addEventListener("click", function() {
      if (selectedId && confirm("Bạn có chắc muốn xóa ID " + selectedId + " ?")) {
        window.location.href = deleteUrl + selectedId;
      }
    });
  }

  document.addEventListener("DOMContentLoaded", function() {
    enableRowSelection("bookTable", "btnEditBook", "btnDeleteBook",
      "adminHome?action=edit&id=", "adminHome?action=delete&id=");
    enableRowSelection("userTable", "btnEditUser", "btnDeleteUser",
      "adminHome?action=editUser&id=", "adminHome?action=deleteUser&id=");
  });
  </script>

</body>
</html>
