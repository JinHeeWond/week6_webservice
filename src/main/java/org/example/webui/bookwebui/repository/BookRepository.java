package org.example.webui.bookwebui.repository;

import org.example.webui.bookwebui.domain.Book;

import java.util.List;

public interface BookRepository {

    Book save(Book book);

    List<Book> findAll();
}