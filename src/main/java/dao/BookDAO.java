package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Book;

public class BookDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/bookstore?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
	private String jdbcUser = "thanh";
	private String jdbcPass = "123456";

	public BookDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Lấy danh sách tất cả sách
	public List<Book> getAllBooks() {
		List<Book> list = new ArrayList<>();
		String sql = "SELECT * FROM books";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Book b = new Book(rs.getInt("bookId"), rs.getString("title"), rs.getString("author"),
						rs.getDouble("price"), rs.getString("imagePath"), rs.getInt("amount"));
				list.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Tìm sách theo ID
	public Book getBookById(int id) {
		String sql = "SELECT * FROM books WHERE bookId=?";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Book(rs.getInt("bookId"), rs.getString("title"), rs.getString("author"),
							rs.getDouble("price"), rs.getString("imagePath"),rs.getInt("amount"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Thêm sách
	public void addBook(Book book) {
		String sql = "INSERT INTO books (title, author, price, imagePath) VALUES (?, ?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setDouble(3, book.getPrice());
			ps.setString(4, book.getImagePath());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Xóa sách
	public void deleteBook(int id) {
		String sql = "DELETE FROM books WHERE bookId=?";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Cập nhật sách
	public void updateBook(Book book) {
		String sql = "UPDATE books SET title=?, author=?, price=?, imagePath=? WHERE bookId=?";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setDouble(3, book.getPrice());
			ps.setString(4, book.getImagePath());
			ps.setInt(5, book.getBookId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
