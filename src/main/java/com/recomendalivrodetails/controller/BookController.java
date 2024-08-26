package com.recomendalivrodetails.controller;

import com.recomendalivrodetails.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping(value = "/test")
    ResponseEntity<String> generateBookDescription(@RequestBody List<String> books) {
        bookService.getBookInfo(books);
        return ResponseEntity.ok("Ok");
    }


}
