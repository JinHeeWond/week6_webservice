package org.example.webui.bookwebui.service;

import org.example.webui.bookwebui.domain.Book;
import org.example.webui.bookwebui.dto.BookRequest;
import org.example.webui.bookwebui.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book create(BookRequest request) {
        Book book = new Book(
                null,
                request.getTitle(),
                request.getAuthor(),
                request.getPrice(),
                request.getCategory(),
                request.getIsbn()
        );

        return bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}