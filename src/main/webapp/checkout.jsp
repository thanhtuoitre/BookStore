<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Thanh toán - Bookstore</title>
<link rel="stylesheet" href="assets/css/style.css">
<link rel="stylesheet" href="assets/css/cart.css">
<style>
.checkout-container {
	width: 80%;
	margin: 30px auto;
	background: #fff;
	padding: 30px;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
.checkout-container h2 {
	text-align: center;
	margin-bottom: 25px;
	color: #333;
}
.checkout-form label {
	font-weight: 600;
	display: block;
	margin-bottom: 5px;
}
.checkout-form input, .checkout-form textarea, .checkout-form select {
	width: 100%;
	padding: 8px;
	border: 1px solid #ddd;
	border-radius: 5px;
	margin-bottom: 15px;
}
.checkout-summary {
	border-top: 2px solid #eee;
	padding-top: 15px;
	font-size: 16px;
}
.payment-method {
	margin-top: 20px;
}
.payment-method label {
	margin-right: 20px;
}
.checkout-btn {
	display: flex;
	justify-content: space-between;
	margin-top: 25px;
}
.checkout-btn button {
	background-color: #28a745;
	color: #fff;
	padding: 10px 20px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}
.checkout-btn a {
	color: #555;
	text-decoration: none;
}
</style>
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
					alt="User">
				<c:if test="${empty sessionScope.user}">
					<a href="login" class="login">Đăng nhập</a>
				</c:if>
				<c:if test="${not empty sessionScope.user}">
					<span>Xin chào, <b>${sessionScope.user.fullName}</b></span>
					<a href="logout" class="login">Đăng xuất</a>
				</c:if>
			</div>
		</div>

		<div class="intro">Thanh toán đơn hàng</div>

		<nav>
			<ul>
				<li><a href="book">Trang chủ</a></li>
				<li><a href="cart">Giỏ hàng</a></li>
				<li><a href="checkout" class="active">Thanh toán</a></li>
			</ul>
		</nav>
	</header>

	<main>
		<div class="checkout-container">
			<h2>Thông tin thanh toán</h2>

			<!-- Gửi form tới servlet ajaxServlet (vnpay-payment) -->
			<form class="checkout-form" action="vnpay-payment" method="post">
				<label>Họ và tên người nhận</label>
				<input type="text" name="fullname" required
					value="${sessionScope.user.fullName}">

				<label>Email</label>
				<input type="email" name="email" required
					value="${sessionScope.user.email}">

				<label>Số điện thoại</label>
				<input type="text" name="phone" required placeholder="Nhập số điện thoại">

				<label>Địa chỉ giao hàng</label>
				<textarea name="address" rows="3" required
					placeholder="Nhập địa chỉ nhận hàng..."></textarea>

				<div class="checkout-summary">
					<p><b>Tổng tiền:</b> 
						<span style="color: red; font-size: 18px;">
							${total} ₫
						</span>
					</p>
				</div>

				<div class="payment-method">
					<label><input type="radio" name="method" value="cod" checked> Thanh toán khi nhận hàng (COD)</label>
					<label><input type="radio" name="method" value="vnpay"> Thanh toán qua VNPAY</label>
				</div>

				<!-- Gửi tổng tiền để servlet xử lý -->
				<input type="hidden" name="amount" value="${total}">

				<div class="checkout-btn">
					<a href="cart">← Quay lại giỏ hàng</a>
					<button type="submit">Xác nhận thanh toán</button>
				</div>
			</form>
		</div>
	</main>

</body>
</html>
