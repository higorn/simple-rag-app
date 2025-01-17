package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.application.BooksFacade;
import com.epam.training.gen.ai.controller.dto.Book;
import com.epam.training.gen.ai.controller.dto.BookCreationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BooksController {
    private final BooksFacade booksFacade;

    @PostMapping
    public BookCreationResponse createBook(@RequestBody Book request) {
        return booksFacade.create(request);
    }

    @PostMapping("/list")
    public List<BookCreationResponse> createBookList(@RequestBody List<Book> request) {
        return request.stream().map(booksFacade::create).toList();
    }

    @GetMapping
    public List<Book> search(@RequestParam String term, @RequestParam int limit) {
        return booksFacade.search(term, limit);
    }
}
