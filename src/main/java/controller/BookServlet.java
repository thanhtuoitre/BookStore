package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.BookDAO;
import model.Book;

@WebServlet("/book")
public class BookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;

	@Override
	public void init() {
		bookDAO = new BookDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String action = req.getParameter("action");
		if (action == null || action.isEmpty()) {
			action = "list";
		}

		try {
			switch (action) {
			case "detail":
				showDetail(req, resp);
				break;
			case "search":
				searchBooks(req, resp); 
				break;
			default:
				showList(req, resp);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("error.jsp");
		}

	}

	private void showDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idStr = req.getParameter("id");
		if (idStr != null) {
			try {
				int id = Integer.parseInt(idStr);
				Book book = bookDAO.getBookById(id);
				if (book != null) {
					req.setAttribute("book", book);
					req.getRequestDispatcher("detail.jsp").forward(req, resp);
					return;
				}
			} catch (NumberFormatException e) {
				// ignore -> redirect
			}
		}
		resp.sendRedirect("book");
	}

	private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ✅ Xử lý phân trang
		int page = 1;
		int pageSize = 3;
		String pageParam = req.getParameter("page");

		if (pageParam != null) {
			try {
				page = Integer.parseInt(pageParam);
				if (page < 1)
					page = 1;
			} catch (NumberFormatException e) {
				page = 1;
			}
		}

		int totalBooks = bookDAO.getTotalBooks();
		int totalPages = (int) Math.ceil((double) totalBooks / pageSize);

		List<Book> bookList = bookDAO.getBooksByPage(page, pageSize);

		req.setAttribute("bookList", bookList);
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPages", totalPages);

		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

	private void searchBooks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String keyword = req.getParameter("keyword");
		if (keyword == null || keyword.trim().isEmpty()) {
			resp.sendRedirect("book");
			return;
		}

		List<Book> results = bookDAO.searchBooksByTitle(keyword);
		req.setAttribute("bookList", results);
		req.setAttribute("keyword", keyword);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String priceStr = req.getParameter("price");
		String imagePath = req.getParameter("imagePath");
		String amountStr = req.getParameter("amount");

		if (title != null && author != null && priceStr != null && amountStr != null) {
			try {
				double price = Double.parseDouble(priceStr);
				int amount = Integer.parseInt(amountStr);
				Book newBook = new Book(0, title, author, price, imagePath, amount);
				bookDAO.addBook(newBook);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		// ✅ Sau khi thêm, quay lại trang admin hoặc trang list
		resp.sendRedirect("book");
	}

	@Override
	public void destroy() {
		super.destroy();
		try {
	        com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
	        java.sql.Driver driver = java.sql.DriverManager.getDriver("jdbc:mysql://localhost:3306/bookstore");
	        java.sql.DriverManager.deregisterDriver(driver);
	        System.out.println("✅ JDBC driver & cleanup thread stopped.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
