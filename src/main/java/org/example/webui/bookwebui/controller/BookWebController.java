package org.example.webui.bookwebui.controller;

import org.example.webui.bookwebui.dto.BookRequest;
import org.example.webui.bookwebui.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookWebController {

    private final BookService bookService;

    public BookWebController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String list(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/list";
    }

    @GetMapping("/books/new")
    public String form() {
        return "books/form";
    }

    @PostMapping("/books")
    public String create(@ModelAttribute BookRequest request) {
        bookService.create(request);
        return "redirect:/books";
    }
}