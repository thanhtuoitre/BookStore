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
		// Khởi tạo DAO
		bookDAO = new BookDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		if (action == null || action.isEmpty()) {
			action = "list";
		}

		try {
			switch (action) {
			case "detail": {
				String idStr = req.getParameter("id");
				if (idStr != null) {
					int id = Integer.parseInt(idStr);
					Book book = bookDAO.getBookById(id);
					if (book != null) {
						req.setAttribute("book", book);
						req.getRequestDispatcher("detail.jsp").forward(req, resp);
					} else {
						resp.sendRedirect("book");
					}
				} else {
					resp.sendRedirect("book");
				}
				break;
			}
			default: { // list
				List<Book> bookList = bookDAO.getAllBooks();
				req.setAttribute("bookList", bookList);
				req.getRequestDispatcher("index.jsp").forward(req, resp);
				break;
			}
			}
		} catch (NumberFormatException e) {
			resp.sendRedirect("book");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
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

		resp.sendRedirect("adminHome");
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
