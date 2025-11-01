<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Bookstore</title>
<link rel="stylesheet" href="assets/css/style.css">
</head>
<body>

	<!-- Header -->
	<header>
		<div class="header-top">
			<!-- Logo bên trái -->
			<div class="logo">
				<img src="${pageContext.request.contextPath}/assets/img/logo.jpg"
					alt="Logo">
				<div class="logo-text">
					<h2>Bookstore</h2>
					<span>ACCESSORIES</span>
				</div>
			</div>

			<!-- Khu vực user + giỏ hàng bên phải -->
			<div class="header-right">
				<div class="user-box">
					<img src="${pageContext.request.contextPath}/assets/img/user.jpg"
						alt="User">
					<c:choose>
						<c:when test="${empty sessionScope.user}">
							<a href="login" class="login">Đăng nhập</a>
						</c:when>
						<c:otherwise>
							<span>Xin chào, <b>${sessionScope.user.fullName}</b></span>
							<a href="logout" class="login">Đăng xuất</a>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="cart-box">
					<a href="cart"> <img
						src="${pageContext.request.contextPath}/assets/img/cart.jpg"
						alt="Cart"> <span>Giỏ hàng</span>
					</a>
				</div>
			</div>
		</div>


		<!-- Giới thiệu -->
		<div class="intro">Website Cửa Hàng Sách với JSP/Servlet</div>

		<!-- Menu -->
		<nav>
			<ul>
				<li><a href="book?action=list">Trang chủ</a></li>
				<li><a href="book?action=bestseller">Sách bán chạy</a></li>
				<li><a href="book?action=newest">Sách mới</a></li>
				<li><a href="book?action=priceAsc">Giá thấp đến cao</a></li>
				<li><a href="book?action=priceDesc">Giá cao đến thấp</a></li>
			</ul>
			<div class="search">
				<form action="book" method="get">
					<input type="hidden" name="action" value="search"> <input
						type="text" name="keyword" placeholder="Tìm sách..."
						value="${keyword}">
					<button type="submit">🔍</button>
				</form>
			</div>

		</nav>
	</header>

	<!-- Nội dung chính -->
	<main>
		<!-- Sidebar -->
		<aside>
			<div class="newsletter">
				<p>
					<b>Đăng ký nhận email</b>
				</p>
				<input type="email" placeholder="Nhập email">
				<button>Gửi</button>
			</div>
		</aside>

		<section class="content">
			<div class="books">
				<c:forEach var="b" items="${bookList}">
					<div class="book">
						<img src="${pageContext.request.contextPath}/${b.imagePath}">
						<h4>${b.title}</h4>
						<p>Tác giả: ${b.author}</p>
						<p>Giá: ${b.price} ₫</p>
						<a href="book?action=detail&id=${b.bookId}">Xem chi tiết</a>
					</div>
				</c:forEach>
			</div>
			<c:if test="${empty bookList}">
				<p>
					Không tìm thấy sách nào với từ khóa "<b>${keyword}</b>"
				</p>
			</c:if>

			<div class="pagination">
				<c:choose>
					<c:when test="${currentPage == 1}">
						<span class="disabled">Previous</span>
					</c:when>
					<c:otherwise>
						<a href="book?page=${currentPage - 1}">Previous</a>
					</c:otherwise>
				</c:choose>

				<c:forEach var="i" begin="1" end="${totalPages}">
					<c:choose>
						<c:when test="${i == currentPage}">
							<span class="active">${i}</span>
						</c:when>
						<c:otherwise>
							<a href="book?page=${i}">${i}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<c:choose>
					<c:when test="${currentPage == totalPages}">
						<span class="disabled">Next</span>
					</c:when>
					<c:otherwise>
						<a href="book?page=${currentPage + 1}">Next</a>
					</c:otherwise>
				</c:choose>
			</div>

		</section>
	</main>

</body>
</html>
