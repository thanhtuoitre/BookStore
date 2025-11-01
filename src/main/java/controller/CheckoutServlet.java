package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CartDAO;
import model.Cart;
import model.User; // ✅ Thêm import này

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CartDAO cartDAO;

    @Override
    public void init() {
        cartDAO = new CartDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Lấy session hiện tại
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // Nếu chưa đăng nhập → chuyển hướng sang trang login
            response.sendRedirect("login");
            return;
        }

        // ✅ Lấy đối tượng User từ session
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId(); // Lấy id từ đối tượng user

        // ✅ Lấy danh sách sản phẩm trong giỏ hàng theo user
        List<Cart> cartItems = cartDAO.getCartByUser(userId);

        // ✅ Tính tổng tiền
        double total = 0;
        for (Cart c : cartItems) {
            total += c.getPrice() * c.getQuantity();
        }

        // ✅ Gửi dữ liệu sang checkout.jsp
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("total", total);

        // ✅ Chuyển tiếp đến trang checkout.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("checkout.jsp");
        dispatcher.forward(request, response);
    }
}
