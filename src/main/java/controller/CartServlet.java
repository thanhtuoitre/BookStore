package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.CartDAO;
import model.Cart;
import model.User;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private CartDAO cartDAO;

    @Override
    public void init() throws ServletException {
        cartDAO = new CartDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        int userId = user.getUserId();

        // Xử lý action (remove)
        String action = request.getParameter("action");
        if ("remove".equals(action)) {
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            cartDAO.removeFromCart(cartId);
        }

        // Lấy giỏ hàng từ DB
        List<Cart> cartItems = cartDAO.getCartByUser(userId);
        request.setAttribute("cartItems", cartItems);

        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        int userId = user.getUserId();

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            int amount = Integer.parseInt(request.getParameter("amount"));
            cartDAO.addToCart(userId, bookId, amount);

        } else if ("update".equals(action)) {
            // Lấy danh sách cart của user
            List<Cart> cartItems = cartDAO.getCartByUser(userId);
            for (Cart c : cartItems) {
                String paramName = "amount_" + c.getCartId(); // form input: amount_{cartId}
                String amountStr = request.getParameter(paramName);
                if (amountStr != null) {
                    int newAmount = Integer.parseInt(amountStr);
                    if (newAmount > 0) {
                        cartDAO.updateQuantity(c.getCartId(), newAmount);
                    } else {
                        cartDAO.removeFromCart(c.getCartId());
                    }
                }
            }
        }

        response.sendRedirect("cart");
    }
}
