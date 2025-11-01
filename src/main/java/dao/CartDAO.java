package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cart;

public class CartDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/bookstore?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
	private String jdbcUser = "thanh"; 
	private String jdbcPass = "123456"; 

	public CartDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Thêm sách vào giỏ
	public void addToCart(int userId, int bookId, int quantity) {
		String sql = "INSERT INTO cart (userId, bookId, quantity) VALUES (?, ?, ?) "
				+ "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.setInt(2, bookId);
			ps.setInt(3, quantity);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Lấy giỏ hàng của 1 user
	public List<Cart> getCartByUser(int userId) {
		List<Cart> list = new ArrayList<>(); // thêm dòng này
		String sql = "SELECT c.cartId, c.userId, c.bookId, c.quantity, c.createdAt, "
				+ "b.title, b.author, b.price, b.imagePath " + "FROM cart c " + "JOIN books b ON c.bookId = b.bookId "
				+ "WHERE c.userId = ?";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Cart c = new Cart();
				c.setCartId(rs.getInt("cartId"));
				c.setUserId(rs.getInt("userId"));
				c.setBookId(rs.getInt("bookId"));
				c.setQuantity(rs.getInt("quantity"));
				c.setCreatedAt(rs.getTimestamp("createdAt"));

				// lấy thêm từ bảng books
				c.setTitle(rs.getString("title"));
				c.setAuthor(rs.getString("author"));
				c.setPrice(rs.getDouble("price"));
				c.setImagePath(rs.getString("imagePath"));

				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Cập nhật số lượng
	public void updateQuantity(int cartId, int quantity) {
		String sql = "UPDATE cart SET quantity = ? WHERE cartId = ?";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, quantity);
			ps.setInt(2, cartId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Xóa 1 sản phẩm khỏi giỏ
	public void removeFromCart(int cartId) {
		String sql = "DELETE FROM cart WHERE cartId = ?";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, cartId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Xóa giỏ hàng của user (dùng khi thanh toán xong)
	public void clearCart(int userId) {
		String sql = "DELETE FROM cart WHERE userId = ?";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Lấy toàn bộ giỏ hàng của tất cả người dùng (cho admin)
	public List<Cart> getAllCarts() {
	    List<Cart> list = new ArrayList<>();
	    String sql = "SELECT c.cartId, c.userId, c.bookId, c.quantity, c.createdAt, "
	               + "b.title, b.author, b.price, b.imagePath, "
	               + "u.username, u.fullName "
	               + "FROM cart c "
	               + "JOIN books b ON c.bookId = b.bookId "
	               + "JOIN users u ON c.userId = u.userId "
	               + "ORDER BY c.createdAt DESC";

	    try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Cart c = new Cart();
	            c.setCartId(rs.getInt("cartId"));
	            c.setUserId(rs.getInt("userId"));
	            c.setBookId(rs.getInt("bookId"));
	            c.setQuantity(rs.getInt("quantity"));
	            c.setCreatedAt(rs.getTimestamp("createdAt"));

	            // Lấy thông tin sách
	            c.setTitle(rs.getString("title"));
	            c.setAuthor(rs.getString("author"));
	            c.setPrice(rs.getDouble("price"));
	            c.setImagePath(rs.getString("imagePath"));

	            // Lấy thêm thông tin user
	            c.setUsername(rs.getString("username"));
	            c.setFullName(rs.getString("fullName"));

	            list.add(c);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

}
