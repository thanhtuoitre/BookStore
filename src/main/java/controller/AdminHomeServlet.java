package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookDAO;
import dao.CartDAO;
import dao.UserDAO;
import model.Book;
import model.Cart;
import model.User;

@WebServlet("/adminHome")
public class AdminHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BookDAO bookDAO;
	private UserDAO userDAO;

	@Override
	public void init() {
		bookDAO = new BookDAO();
		userDAO = new UserDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");

		// ==== Kiểm tra đăng nhập ====
		HttpSession session = req.getSession(false);
		User currentUser = (session == null) ? null : (User) session.getAttribute("user");

		if (currentUser == null) {
			System.out.println(">>> AdminHomeServlet: Chưa đăng nhập, quay về login");
			resp.sendRedirect("login");
			return;
		}

		String action = req.getParameter("action");
		if (action == null)
			action = "list";

		String idStr = req.getParameter("id");

		switch (action) {
		/* ================= SÁCH ================= */
		case "create":
			req.getRequestDispatcher("form.jsp").forward(req, resp);
			break;

		case "edit":
			int idEdit = Integer.parseInt(idStr);
			Book editBook = bookDAO.getBookById(idEdit);
			req.setAttribute("book", editBook);
			req.getRequestDispatcher("form.jsp").forward(req, resp);
			break;

		case "delete":
			int idDelete = Integer.parseInt(idStr);
			bookDAO.deleteBook(idDelete);
			resp.sendRedirect("adminHome");
			break;

		/* ================= NGƯỜI DÙNG ================= */
		case "createUser":
			req.getRequestDispatcher("formUser.jsp").forward(req, resp);
			break;

		case "editUser":
			int idU = Integer.parseInt(idStr);
			User editUser = userDAO.getUserById(idU);
			req.setAttribute("user", editUser);
			req.getRequestDispatcher("formUser.jsp").forward(req, resp);
			break;

		case "deleteUser":
			int idDel = Integer.parseInt(idStr);
			userDAO.deleteUser(idDel);
			resp.sendRedirect("adminHome");
			break;

		/* ================= MẶC ĐỊNH ================= */
		default:
			List<Book> bookList = bookDAO.getAllBooks();
			List<User> userList = userDAO.getAllUsers();
			List<Cart> cartList = new CartDAO().getAllCarts(); // hoặc bỏ nếu bạn không dùng tab giỏ hàng

			req.setAttribute("bookList", bookList);
			req.setAttribute("userList", userList);
			req.setAttribute("cartList", cartList);
			req.getRequestDispatcher("adminHome.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		HttpSession session = req.getSession(false);
		User currentUser = (session == null) ? null : (User) session.getAttribute("user");

		if (currentUser == null) {
			resp.sendRedirect("login");
			return;
		}

		/* ============ XỬ LÝ FORM SÁCH ============ */
		String formType = req.getParameter("formType"); // để phân biệt form nào gửi lên
		if (formType != null && formType.equals("user")) {
			// FORM NGƯỜI DÙNG
			String idStr = req.getParameter("id");
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			String fullName = req.getParameter("fullName");
			String role = req.getParameter("role");

			if (idStr == null || idStr.isEmpty()) {
				// thêm mới
				User newUser = new User(0, username, password, email, fullName, role);
				userDAO.addUser(newUser);
			} else {
				// cập nhật
				int id = Integer.parseInt(idStr);
				User updateUser = new User(id, username, password, email, fullName, role);
				userDAO.updateUser(updateUser);
			}

			resp.sendRedirect("adminHome");
			return;
		}

		// Nếu không có formType=user → là form sách
		String idStr = req.getParameter("id");
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String priceStr = req.getParameter("price");
		String imagePath = req.getParameter("imagePath");
		String amountStr = req.getParameter("amount");

		double price = 0.0;
		if (priceStr != null && !priceStr.isEmpty()) {
			try {
				price = Double.parseDouble(priceStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		int amount = 0;
		if (amountStr != null && !amountStr.isEmpty()) {
			try {
				amount = Integer.parseInt(amountStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		if (idStr == null || idStr.isEmpty()) {
			Book newBook = new Book(0, title, author, price, imagePath, amount);
			bookDAO.addBook(newBook);
		} else {
			int id = Integer.parseInt(idStr);
			Book updateBook = new Book(id, title, author, price, imagePath, amount);
			bookDAO.updateBook(updateBook);
		}

		resp.sendRedirect("adminHome");
	}
}
