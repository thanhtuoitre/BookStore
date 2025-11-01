<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Giỏ hàng - Bookstore</title>
<link rel="stylesheet" href="assets/css/style.css">
<link rel="stylesheet" href="assets/css/cart.css">

</head>
<body>

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
					alt="Logo">
				<c:if test="${empty sessionScope.user}">
					<a href="login" class="login">Đăng nhập</a>
				</c:if>
				<c:if test="${not empty sessionScope.user}">
					<span>Xin chào, <b>${sessionScope.user.fullName}</b></span>
					<a href="logout" class="login">Đăng xuất</a>
				</c:if>
			</div>
		</div>

		<div class="intro">Giỏ hàng của bạn</div>

		<nav>
			<ul>
				<li><a href="book">Trang chủ</a></li>
				<li><a href="cart">Giỏ hàng</a></li>
			</ul>

		</nav>
	</header>

	<main>
		<section class="content">
			<h2>Danh sách sản phẩm trong giỏ hàng</h2>

			<c:if test="${empty cartItems}">
				<p>Giỏ hàng của bạn đang trống.</p>
			</c:if>

			<c:if test="${not empty cartItems}">
				<form action="cart" method="post">
					<table class="cart-table">
						<tr>
							<th>Ảnh</th>
							<th>Tên sách</th>
							<th>Tác giả</th>
							<th>Giá</th>
							<th>Số lượng</th>
							<th>Thành tiền</th>
							<th>Hành động</th>
						</tr>
						<c:set var="total" value="0" />
						<c:forEach var="c" items="${cartItems}">
							<tr>
								<td><img src="${c.imagePath}" alt="${c.title}" width="80"></td>
								<td>${c.title}</td>
								<td>${c.author}</td>
								<td>${c.price}₫</td>
								<td><input type="number" name="amount_${c.cartId}"
									value="${c.quantity}" min="1" style="width: 50px;"></td>
								<td>${c.price * c.quantity}₫</td>
								<td class="cart-actions"><a
									href="cart?action=remove&cartId=${c.cartId}">Xóa</a></td>
							</tr>
							<c:set var="total" value="${total + (c.price * c.quantity)}" />
						</c:forEach>
					</table>

					<div class="total">Tổng tiền: ${total} ₫</div>

					<div style="text-align: right; margin-top: 10px;">
						<button type="submit" name="action" value="update">Cập
							nhật giỏ hàng</button>
						<a href="checkout" style="margin-left: 20px; font-weight: bold;">Thanh
							toán</a>
					</div>
				</form>
			</c:if>
		</section>
	</main>



</body>
</html>
