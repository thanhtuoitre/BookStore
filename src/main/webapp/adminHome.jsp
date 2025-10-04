<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý Sách</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="container mt-4">

	<h2>Danh sách Sách</h2>
	<a href="adminHome?action=addForm" class="btn btn-success mb-2">+ Thêm
		mới</a>
	<table class="table table-bordered">
		<thead class="table-dark">
			<tr>
				<th>ID</th>
				<th>Tiêu đề</th>
				<th>Tác giả</th>
				<th>Giá</th>
				<th>Ảnh</th>
				<th>Thao tác</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="b" items="${bookList}">
				<tr>
					<td>${b.bookId}</td>
					<td>${b.title}</td>
					<td>${b.author}</td>
					<td>${b.price}</td>
					<td><img src="${b.imagePath}" width="60"></td>
					<td><a href="adminHome?action=edit&id=${b.bookId}"
						class="btn btn-warning btn-sm">Sửa</a> <a
						href="adminHome?action=delete&id=${b.bookId}"
						class="btn btn-danger btn-sm"
						onclick="return confirm('Xóa sách này?');">Xóa</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>
