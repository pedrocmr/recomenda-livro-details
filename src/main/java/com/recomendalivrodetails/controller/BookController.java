package com.recomendalivrodetails.controller;

import com.recomendalivrodetails.entities.front.BookData;
import com.recomendalivrodetails.entities.front.BookPreference;
import com.recomendalivrodetails.entities.google.BookInfo;
import com.recomendalivrodetails.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @GetMapping(value = "/get-preference")
    ResponseEntity<Boolean> getPreference(@RequestBody String email) {
        return new ResponseEntity<>(bookService.getPreference(email), HttpStatus.OK);
    }

    @GetMapping(value = "/get-favorite")
    ResponseEntity<BookData> getFavoriteBook(@RequestBody String email){
        return new ResponseEntity<>(bookService.getFavoriteBookData(email), HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove-book")
    ResponseEntity<Boolean> removeFavoriteBook(@RequestBody String email, String idLivro) {
        return new ResponseEntity<>(bookService.removeFavoriteBookData(email, idLivro), HttpStatus.OK);
    }

    @PostMapping(value = "/save-favorite-book")
    ResponseEntity<Boolean> saveFavoriteBook(@RequestBody BookData book) {
        return new ResponseEntity<>(bookService.saveFavoriteBookData(book), HttpStatus.OK);
    }

    @GetMapping(value = "/get-recomendation")
    ResponseEntity<List<BookInfo>> getRecomendation(@RequestBody String email){
        return new ResponseEntity<>(bookService.populateBookDetails(email), HttpStatus.OK);

    }
}
