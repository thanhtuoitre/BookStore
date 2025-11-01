<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Quản lý Người dùng</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
	body {
		background-color: #f8f9fa;
	}
	.user-form {
		max-width: 600px;
		margin: 50px auto;
		background: #fff;
		padding: 30px;
		border-radius: 10px;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	}
	h3 {
		text-align: center;
		color: #0d6efd;
		font-weight: bold;
		margin-bottom: 25px;
	}
	label {
		font-weight: 600;
	}
	.btn {
		width: 100px;
	}
</style>
</head>

<body>

	<div class="user-form">
		<h3>${user != null ? "Sửa Người dùng" : "Thêm Người dùng"}</h3>

		<form method="post" action="adminHome">
			<input type="hidden" name="formType" value="user">
			<input type="hidden" name="id" value="${user.userId}">

			<div class="mb-3">
				<label for="username">Tên đăng nhập</label>
				<input id="username" name="username" class="form-control" value="${user.username}" required>
			</div>

			<div class="mb-3">
				<label for="password">Mật khẩu</label>
				<input id="password" name="password" type="password" class="form-control" value="${user.password}" required>
			</div>

			<div class="mb-3">
				<label for="email">Email</label>
				<input id="email" name="email" type="email" class="form-control" value="${user.email}" required>
			</div>

			<div class="mb-3">
				<label for="fullName">Họ tên</label>
				<input id="fullName" name="fullName" class="form-control" value="${user.fullName}" required>
			</div>

			<div class="mb-3">
				<label for="role">Vai trò</label>
				<select id="role" name="role" class="form-select" required>
					<option value="USER" ${user.role == 'USER' ? "selected" : ""}>USER</option>
					<option value="ADMIN" ${user.role == 'ADMIN' ? "selected" : ""}>ADMIN</option>
				</select>
			</div>

			<div class="d-flex justify-content-between">
				<button type="submit" class="btn btn-primary">Lưu</button>
				<a href="adminHome" class="btn btn-secondary">Hủy</a>
			</div>
		</form>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
