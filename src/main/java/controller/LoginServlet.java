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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy cookie remember user
        Cookie[] cookies = req.getCookies();
        String rememberedUser = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("remembereduser".equals(c.getName())) {
                    rememberedUser = c.getValue();
                    break;
                }
            }
        }

        req.setAttribute("remembereduser", rememberedUser);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        // ======= KIỂM TRA ĐĂNG NHẬP TRONG DB =======
        User user = userDAO.checkLogin(username, password);

        if (user != null) {
            // Đăng nhập thành công
            HttpSession session = req.getSession();
            session.setAttribute("user", user); // lưu toàn bộ object User

            System.out.println(">>> Login success: " + username + " | Role = " + user.getRole());

            // === Ghi nhớ tài khoản bằng Cookie ===
            Cookie cookie = new Cookie("remembereduser",
                    "on".equals(remember) ? username : "");
            cookie.setMaxAge("on".equals(remember) ? 60 * 60 * 24 * 7 : 0); // 7 ngày hoặc xóa
            resp.addCookie(cookie);

            // === Điều hướng theo vai trò ===
            if (user.getRole() != null && user.getRole().equalsIgnoreCase("admin")) {
                resp.sendRedirect("adminHome");
            } else {
                resp.sendRedirect("book"); // người dùng thường
            }

        } else {
            // Sai tài khoản hoặc mật khẩu
            req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            System.out.println(">>> Login failed for user: " + username);
        }
    }
}
