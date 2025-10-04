package controller;

import dao.UserDAO;
import model.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDAO userDAO;

	@Override
	public void init() throws ServletException {
		userDAO = new UserDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy cookie remember
		Cookie[] cookies = req.getCookies();
		String remembereduser = null;

		if (cookies != null) {
			for (Cookie c : cookies) {
				if ("remembereduser".equals(c.getName())) {
					remembereduser = c.getValue();
					break;
				}
			}
		}
		req.setAttribute("remembereduser", remembereduser);
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");

		// check trong DB
		User user = userDAO.checkLogin(username, password);
		if (user != null) {
			// Đăng nhập thành công
			HttpSession session = req.getSession();
			session.setAttribute("user", user); // lưu object User

			// nhớ tài khoản
			if ("on".equals(remember)) {
				Cookie cookie = new Cookie("remembereduser", username);
				cookie.setMaxAge(60 * 60 * 24 * 7); // 7 ngày
				resp.addCookie(cookie);
			} else {
				Cookie cookie = new Cookie("remembereduser", "");
				cookie.setMaxAge(0); // xóa cookie
				resp.addCookie(cookie);
			}

			// điều hướng theo role
			if ("admin".equalsIgnoreCase(user.getRole())) {
				resp.sendRedirect("adminHome");
			} else {
				resp.sendRedirect("book"); // về trang chủ cho user
			}
		} else {
			req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu.");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
	}
}
