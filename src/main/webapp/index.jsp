<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Bookstore</title>
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

		<!-- Giới thiệu -->
		<div class="intro">Website Cửa Hàng Sách với JSP/Servlet</div>

		<!-- Menu -->
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

	<!-- Nội dung chính -->
	<main>
		<!-- Sidebar -->
		<aside>
			<ul>
				<li><label><input type="checkbox"> Sách thiếu nhi</label></li>
				<li><label><input type="checkbox"> Sách ngoại ngữ</label></li>
				<li><label><input type="checkbox"> Sách kỹ năng sống</label></li>
				<li><label><input type="checkbox"> Sách nuôi dạy con</label></li>
				<li><label><input type="checkbox"> Sách tin học</label></li>
				<li><label><input type="checkbox"> Sách kinh tế - xã hội</label></li>
				<li><label><input type="checkbox"> Sách khoa học - công nghệ</label></li>
			</ul>

			<div class="newsletter">
				<p><b>Đăng ký nhận email</b></p>
				<input type="email" placeholder="Nhập email">
				<button>Gửi</button>
			</div>
		</aside>

		<section class="content">
			<div class="books">
				<c:forEach var="b" items="${bookList}">
					<div class="book">
						<img src="${b.imagePath}" alt="${b.title}" style="width: 120px; height: 160px;">
						<h4>${b.title}</h4>
						<p>Tác giả: ${b.author}</p>
						<p>Giá: ${b.price} ₫</p>
						<a href="book?action=detail&id=${b.bookId}">Xem chi tiết</a>
					</div>
				</c:forEach>
			</div>

			<div class="pagination">
				<a href="#">Previous</a> <a href="#">1</a> <a href="#">2</a>
			</div>
		</section>
	</main>

</body>
</html>
