<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Quản lý Người dùng</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
  <h3>${user != null ? "Sửa Người dùng" : "Thêm Người dùng"}</h3>
  <form method="post" action="adminHome">
    <input type="hidden" name="formType" value="user">
    <input type="hidden" name="id" value="${user.userId}">
    
    <div class="mb-3">
      <label>Tên đăng nhập</label>
      <input name="username" class="form-control" value="${user.username}">
    </div>

    <div class="mb-3">
      <label>Mật khẩu</label>
      <input name="password" type="password" class="form-control" value="${user.password}">
    </div>

    <div class="mb-3">
      <label>Email</label>
      <input name="email" class="form-control" value="${user.email}">
    </div>

    <div class="mb-3">
      <label>Họ tên</label>
      <input name="fullName" class="form-control" value="${user.fullName}">
    </div>

    <div class="mb-3">
      <label>Vai trò</label>
      <select name="role" class="form-control">
        <option ${user.role == 'USER' ? "selected" : ""}>USER</option>
        <option ${user.role == 'ADMIN' ? "selected" : ""}>ADMIN</option>
      </select>
    </div>

    <button type="submit" class="btn btn-primary">Lưu</button>
    <a href="adminHome" class="btn btn-secondary">Hủy</a>
  </form>
</body>
</html>
