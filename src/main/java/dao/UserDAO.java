package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/bookstore?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
	private String jdbcUser = "thanh";
	private String jdbcPass = "123456";

	public UserDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Thêm user mới (đăng ký)
	public void addUser(User user) {
		String sql = "INSERT INTO users(username, password, email, fullName, role) VALUES(?, ?, ?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getFullName());
			ps.setString(5, user.getRole());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Kiểm tra login
	public User checkLogin(String username, String password) {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new User(rs.getInt("userId"), rs.getString("username"), rs.getString("password"),
						rs.getString("email"), rs.getString("fullName"), rs.getString("role"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // login fail
	}

	// Lấy tất cả user (cho admin quản lý)
	public List<User> getAllUsers() {
		List<User> list = new ArrayList<>();
		String sql = "SELECT * FROM users";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				User u = new User(rs.getInt("userId"), rs.getString("username"), rs.getString("password"),
						rs.getString("email"), rs.getString("fullName"), rs.getString("role"));
				list.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Lấy user theo id
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE userId = ?";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new User(rs.getInt("userId"), rs.getString("username"), rs.getString("password"),
						rs.getString("email"), rs.getString("fullName"), rs.getString("role"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Cập nhật user
	public void updateUser(User user) {
		String sql = "UPDATE users SET username=?, password=?, email=?, fullName=?, role=? WHERE userId=?";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getFullName());
			ps.setString(5, user.getRole());
			ps.setInt(6, user.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Xóa user
	public void deleteUser(int userId) {
		String sql = "DELETE FROM users WHERE userId=?";
		try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
