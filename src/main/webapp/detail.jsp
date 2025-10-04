<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Book"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
Book book = (Book) request.getAttribute("book");
if (book == null) {
%>
<h3>Không tìm thấy sách!</h3>
<%
return;
}
%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">

<title><%=book.getTitle()%> - Bookstore</title>
<link rel="stylesheet" href="detail.css">
<link rel="stylesheet" href="style.css">
</head>
<body>

	<!-- Header -->
	<header>
		<div class="header-top">
			<div class="logo">
				<img
					src="https://img.freepik.com/premium-vector/book-line-art-logo-icon-vector-white-transparent-background-web-use_1119746-162.jpg"
					alt="Logo">
				<div class="logo-text">
					<h2>Bookstore</h2>
					<span>ACCESSORIES</span>
				</div>
			</div>

			<div class="logo1">
				<img
					src="https://tse2.mm.bing.net/th/id/OIP.0khZCrzBSvpHdgKH3CiJrgHaHa?rs=1&pid=ImgDetMain&o=7&rm=3"
					alt="User"> 

				<!-- Nếu chưa đăng nhập -->
				<c:if test="${empty sessionScope.user}">
					<a href="login" class="login">Đăng nhập</a>
				</c:if>

				<!-- Nếu đã đăng nhập -->
				<c:if test="${not empty sessionScope.user}">
					<span>Xin chào, <b>${sessionScope.user.fullName}</b></span>
					<a href="logout" class="login">Đăng xuất</a>
				</c:if>
			</div>
		</div>

		<div class="intro">Chi tiết sách</div>

		<nav>
			<ul>
				<li><a href="book">Trang chủ</a></li>
				<li><a href="#">Sách phổ biến</a></li>
				<li><a href="#">Sách bán chạy</a></li>
				<li><a href="#">Sách mới</a></li>
				<li><a href="#">Giá thấp đến cao</a></li>
				<li><a href="#">Giá cao đến thấp</a></li>
			</ul>
			<div class="search">
				<input type="text" placeholder="Tìm sách...">
			</div>
		</nav>
	</header>

	<!-- Nội dung chi tiết -->
	<main>
		<div class="detail-container">
			<img src="<%=book.getImagePath()%>" alt="<%=book.getTitle()%>">

			<div class="book-info">
				<h2><%=book.getTitle()%></h2>
				<p>
					<b>Tác giả:</b>
					<%=book.getAuthor()%></p>
				<p class="price"><%=String.format("%,.0f", book.getPrice())%>₫
				<p class="amount">
					<b>Số lượng sách: </b><%=(book.getAmount())%>
				</p>

				<p>
					<b>Mô tả:</b> Hiện tại chưa có thông tin mô tả chi tiết cho cuốn
					sách này.
				</p>

				<form action="cart" method="post" style="margin-top: 15px;">
					<input type="hidden" name="action" value="add"> <input
						type="hidden" name="bookId" value="<%=book.getBookId()%>">

					<label for="amount">Số lượng: </label> <input type="number"
						id="amount" name="amount" value="1" min="1" style="width: 60px;">

					<button type="submit" class="btn" style="background: #4CAF50;">Thêm
						vào giỏ hàng</button>
				</form>

				<a href="book" class="btn"
					style="background: #2196F3; margin-left: 10px;">Quay lại</a>

			</div>
		</div>
	</main>

	<!-- Footer -->
	<footer> Bản quyền thuộc nhóm tác giả cuốn sách "Giáo trình
		Lập trình Java 2" </footer>

</body>
</html>
