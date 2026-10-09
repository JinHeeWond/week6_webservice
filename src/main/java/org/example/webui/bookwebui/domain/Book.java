package org.example.webui.bookwebui.domain;

public class Book {

    private Long id;
    private String title;
    private String author;
    private int price;
    private String category;
    private String isbn;

    public Book() {
    }

    public Book(Long id, String title, String author, int price, String category, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getIsbn() {
        return isbn;
    }
}