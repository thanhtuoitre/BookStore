<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Form Sách</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="container mt-4">

	<h2>
		<c:choose>
			<c:when test="${not empty book}">Chỉnh sửa sách</c:when>
			<c:otherwise>Thêm sách mới</c:otherwise>
		</c:choose>
	</h2>

	<form action="adminHome" method="post" class="mt-3">
		<!-- Nếu là edit thì có hidden id -->
		<c:if test="${not empty book}">
			<input type="hidden" name="id" value="${book.bookId}">
		</c:if>

		<div class="mb-3">
			<label for="title" class="form-label">Tiêu đề</label> <input
				type="text" class="form-control" id="title" name="title"
				value="${book.title}" required>
		</div>

		<div class="mb-3">
			<label for="author" class="form-label">Tác giả</label> <input
				type="text" class="form-control" id="author" name="author"
				value="${book.author}" required>
		</div>

		<div class="mb-3">
			<label for="price" class="form-label">Giá</label> <input
				type="number" step="0.01" class="form-control" id="price"
				name="price" value="${book.price}" required>
		</div>

		<div class="mb-3">
			<label for="imagePath" class="form-label">Đường dẫn ảnh</label> <input
				type="text" class="form-control" id="imagePath" name="imagePath"
				value="${book.imagePath}">
		</div>

		<button type="submit" class="btn btn-primary">Lưu</button>
		<a href="adminHome" class="btn btn-secondary">Hủy</a>
	</form>

</body>
</html>
