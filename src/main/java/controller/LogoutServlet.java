package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false); // lấy session hiện có
		if (session != null) {
			session.invalidate(); // xoá toàn bộ session
		}

		// cũng có thể xoá cookie nếu bạn muốn
		Cookie cookie = new Cookie("remembereduser", "");
		cookie.setMaxAge(0);
		resp.addCookie(cookie);

		resp.sendRedirect("book"); // quay về trang chủ
	}
}
