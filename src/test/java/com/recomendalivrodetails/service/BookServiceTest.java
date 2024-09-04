package com.recomendalivrodetails.service;

import com.recomendalivrodetails.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchBookData_ShouldReturnValidResponseFromGoogleBooksResponseList() {
        String bookTitle = "Title1";
        List<String> bookTitles = List.of(bookTitle);

        VolumeInfo volumeInfo = VolumeInfo.builder()
                .authors(List.of("Author1"))
                .title("Title1")
                .imageLinks(ImageLinks.builder().thumbnail("thumbUrl").build())
                .build();

        Item item = Item.builder().volumeInfo(volumeInfo).build();

        GoogleBooksResponse googleBooksResponse = GoogleBooksResponse.builder().items(Collections.singletonList(item)).build();

        when(restTemplate.getForEntity(anyString(), eq(GoogleBooksResponse.class)))
                .thenReturn(new ResponseEntity<>(googleBooksResponse, HttpStatus.OK));

        List<GoogleBooksResponse> responseList = bookService.searchBookData(bookTitles);

        assertEquals(1, responseList.size());
        assertEquals(googleBooksResponse, responseList.get(0));
    }

//    @Test
//    void retrieveBookInfo_ValidResponse_ReturnsBookInfoList() {
//        String bookTitle = "Title1";
//        List<String> bookTitles = List.of(bookTitle);
//
//        VolumeInfo volumeInfo = VolumeInfo.builder()
//                .authors(List.of("Author1"))
//                .title("Title1")
//                .imageLinks(ImageLinks.builder().thumbnail("thumbUrl").build())
//                .build();
//
//        Item item = Item.builder().volumeInfo(volumeInfo).build();
//
//        GoogleBooksResponse googleBooksResponse = GoogleBooksResponse.builder().items(Collections.singletonList(item)).build();
//
//        when(bookService.searchBookData(bookTitles)).thenReturn(Collections.singletonList(googleBooksResponse));
//
//        List<BookInfo> bookInfoList = bookService.retrieveBookInfo(bookTitles);
//
//        assertEquals(1, bookInfoList.size());
//        assertEquals("Title1", bookInfoList.get(0).getTitle());
//        assertEquals("Author1", bookInfoList.get(0).getAuthors().get(0));
//        assertEquals("thumbnailUrl", bookInfoList.get(0).getThumbnailUrl());
//    }

}