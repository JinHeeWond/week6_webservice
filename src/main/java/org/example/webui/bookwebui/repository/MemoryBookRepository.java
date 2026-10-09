package org.example.webui.bookwebui.repository;

import org.example.webui.bookwebui.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryBookRepository implements BookRepository {

    private final Map<Long, Book> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Book save(Book book) {
        sequence++;

        Book savedBook = new Book(
                sequence,
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getCategory(),
                book.getIsbn()
        );

        store.put(sequence, savedBook);
        return savedBook;
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(store.values());
    }
}