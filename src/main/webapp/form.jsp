<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Form Sách</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
	body {
		background-color: #f8f9fa;
	}
	.form-container {
		max-width: 600px;
		margin: 50px auto;
		background: #fff;
		padding: 30px;
		border-radius: 10px;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	}
	h2 {
		text-align: center;
		color: #0d6efd;
		font-weight: bold;
		margin-bottom: 25px;
	}
	.btn-primary {
		width: 100px;
	}
	.btn-secondary {
		width: 100px;
	}
</style>
</head>

<body>

	<div class="form-container">
		<h2>
			<c:choose>
				<c:when test="${not empty book}">Chỉnh sửa sách</c:when>
				<c:otherwise>Thêm sách mới</c:otherwise>
			</c:choose>
		</h2>

		<form action="adminHome" method="post">
			<c:if test="${not empty book}">
				<input type="hidden" name="id" value="${book.bookId}">
			</c:if>

			<div class="mb-3">
				<label for="title" class="form-label fw-semibold">Tiêu đề</label>
				<input type="text" class="form-control" id="title" name="title"
					value="${book.title}" required>
			</div>

			<div class="mb-3">
				<label for="author" class="form-label fw-semibold">Tác giả</label>
				<input type="text" class="form-control" id="author" name="author"
					value="${book.author}" required>
			</div>

			<div class="mb-3">
				<label for="price" class="form-label fw-semibold">Giá (VNĐ)</label>
				<input type="number" step="0.01" class="form-control" id="price"
					name="price" value="${book.price}" required>
			</div>

			<div class="mb-3">
				<label for="imagePath" class="form-label fw-semibold">Đường dẫn ảnh</label>
				<input type="text" class="form-control" id="imagePath" name="imagePath"
					value="${book.imagePath}">
			</div>

			<div class="d-flex justify-content-between">
				<button type="submit" class="btn btn-primary">Lưu</button>
				<a href="adminHome" class="btn btn-secondary">Hủy</a>
			</div>
		</form>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
