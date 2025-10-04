package model;

public class Book {
	private int bookId;
	private String title;
	private String author;
	private double price;
	private String imagePath;
	private int amount;

	public Book(int bookId, String title, String author, double price, String imagePath, int amount) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.price = price;
		this.imagePath = imagePath;
		this.amount = amount;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
