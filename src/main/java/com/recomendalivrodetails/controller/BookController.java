package com.recomendalivrodetails.controller;

import com.recomendalivrodetails.entities.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {


    @PostMapping(value = "/test")
    ResponseEntity<String> generateBookDescription(@RequestBody List<Book> books) {
        System.out.println("bateu");
        return ResponseEntity.ok("Ok");
    }


}
