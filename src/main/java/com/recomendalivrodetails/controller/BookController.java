package com.recomendalivrodetails.controller;

import com.recomendalivrodetails.entities.BookInfo;
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


    @PostMapping(value = "/book-details")
    ResponseEntity<List<BookInfo>> generateBookDescription(@RequestBody List<String> books) {
        List<BookInfo> bookInfoList = bookService.retreiveBookInfo(books);
        return ResponseEntity.ok(bookInfoList);
    }


}
