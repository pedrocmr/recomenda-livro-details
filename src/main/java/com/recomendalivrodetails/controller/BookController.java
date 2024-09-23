package com.recomendalivrodetails.controller;

import com.recomendalivrodetails.entities.front.BookData;
import com.recomendalivrodetails.entities.front.BookPreference;
import com.recomendalivrodetails.entities.front.InputDeleteLivro;
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

    @GetMapping(value = "/get-preference/{email}")
    ResponseEntity<Boolean> getPreference(@PathVariable String email) {
        return new ResponseEntity<>(bookService.getPreference(email), HttpStatus.OK);
    }

    @GetMapping(value = "/get-favorite/{email}")
    ResponseEntity<BookData> getFavoriteBook(@PathVariable String email) throws Exception {
        return new ResponseEntity<>(bookService.getFavoriteBookData(email), HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove-book")
    ResponseEntity<Boolean> removeFavoriteBook(@RequestBody InputDeleteLivro input) {
        return new ResponseEntity<>(bookService.removeFavoriteBookData(input.email(), input.idLivro()), HttpStatus.OK);
    }

    @PostMapping(value = "/save-favorite-book")
    ResponseEntity<BookData> saveFavoriteBook2(@RequestBody BookData book) throws Exception {
        return new ResponseEntity<>(bookService.saveFavoriteBookData2(book), HttpStatus.OK);
    }

    @GetMapping(value = "/get-recomendation/{email}")
    ResponseEntity<List<BookInfo>> getRecomendation(@PathVariable String email){
        System.out.println("53 - Recebida a requisição - " + getClass().getName());
        System.out.println("Email: " + email);
        return new ResponseEntity<>(bookService.populateBookDetails(email), HttpStatus.OK);
    }

    @GetMapping(value = "/get-preference/list/{email}")
    ResponseEntity<?> getPreferencesList(@PathVariable String email) {
        return new ResponseEntity<>(bookService.getPreferenceList(email), HttpStatus.OK);
    }

}
