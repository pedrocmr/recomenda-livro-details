package com.recomendalivrodetails.controller;

import com.recomendalivrodetails.entities.BookInfo;
import com.recomendalivrodetails.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookControllerTest {

    public BookService bookService;
    @InjectMocks
    public BookController bookController;

    @BeforeEach
    void setup() {
        bookService = mock(BookService.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateBookDescription_ShouldReturnBookInfoList() {

        List<String> books = Arrays.asList("Title1", "Title2");
        List<BookInfo> mockBookInfoList = Arrays
                .asList(
                        BookInfo.builder().title("Title1").authors(List.of("author1")).thumbnailUrl("imageUrl1").build()
                        ,BookInfo.builder().title("Title2").authors(List.of("author2")).thumbnailUrl("imageUr2").build());

        when(bookService.retrieveBookInfo(books)).thenReturn(mockBookInfoList);

        ResponseEntity<List<BookInfo>> response = bookController.generateBookDescription(books);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBookInfoList, response.getBody());
    }
}