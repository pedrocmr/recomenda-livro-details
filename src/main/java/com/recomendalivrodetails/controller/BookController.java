package com.recomendalivrodetails.controller;

import com.recomendalivrodetails.entities.front.BookData;
import com.recomendalivrodetails.entities.front.BookPreference;
import com.recomendalivrodetails.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "/save-preference")
    ResponseEntity<?> saveBookPreference(@RequestBody BookPreference bookPreference){
        if(bookService.saveBookPreference(bookPreference)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Already have a preference saved to this email.", HttpStatus.CONFLICT);
    }

    @DeleteMapping(value = "/remove-preference")
    ResponseEntity<?> removeBookPreference(@RequestBody BookPreference bookPreference) {
        bookService.removeBookPreference(bookPreference);

        return null;
    }
    @GetMapping(value = "/get-preference")
    ResponseEntity<?> getPreference(@RequestBody String email) {
        return new ResponseEntity<>(bookService.getPreference(email), HttpStatus.OK);
    }

    @GetMapping(value = "/get-favorite")
    ResponseEntity<?> getFavoriteBook(){

        return null;
    }

    @DeleteMapping(value = "/remove-book")
    ResponseEntity<?> removeFavoriteBook() {
        return null;
    }

    @PostMapping(value = "/save-favorite-book")
    ResponseEntity<List<BookData>> saveFavoriteBook(@RequestBody BookData book) {
        bookService.saveFavoriteBookData(book);
        return ResponseEntity.ok().build();
    }
}
