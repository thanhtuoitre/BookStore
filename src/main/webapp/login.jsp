<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đăng nhập - Bookstore</title>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="login.css">
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
					alt="User"> <a href="login.jsp" class="login">Đăng nhập</a>
			</div>
		</div>

		<div class="intro">Trang đăng nhập Bookstore</div>

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

	<!-- Nội dung login -->
	<main>
		<div class="login-container">
			<form action="login" method="post">
				<h2>Đăng nhập</h2>
				<label for="username">Tên đăng nhập</label> <input type="text"
					id="username" name="username" required> <label
					for="password">Mật khẩu</label> <input type="password"
					id="password" name="password" required>

				<button type="submit">Đăng nhập</button>

				<div class="links">
					<a href="#">Quên mật khẩu?</a> | <a href="#">Đăng ký tài khoản
						mới</a>
				</div>
			</form>
		</div>
	</main>

	<!-- Footer -->
	<footer> Bản quyền thuộc nhóm tác giả cuốn sách "Giáo trình
		Lập trình Java 2" </footer>

</body>
</html>
