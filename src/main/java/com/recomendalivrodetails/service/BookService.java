package com.recomendalivrodetails.service;

import com.recomendalivrodetails.entities.BookInfo;
import com.recomendalivrodetails.entities.GoogleBooksResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final RestTemplate restTemplate;

    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=intitle:";

    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<GoogleBooksResponse> searchBookData(List<String> bookTitles) {
        List<GoogleBooksResponse> bookInfoList = new ArrayList<>();
        for (String title : bookTitles) {
            String url = GOOGLE_BOOKS_API_URL + title;
            ResponseEntity<GoogleBooksResponse> response = restTemplate.getForEntity(url, GoogleBooksResponse.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                bookInfoList.add(response.getBody());
            }
        }
        return bookInfoList;
    }

    public List<BookInfo> retrieveBookInfo(List<String> books) {

        List<GoogleBooksResponse> bookData = searchBookData(books);

        List<BookInfo> bookInfoList = new ArrayList<>();
        bookData.forEach(booksResponse -> {
            booksResponse.getItems().forEach(item -> {
                bookInfoList.add(BookInfo.builder()
                        .id(item.getId())
                        .authors(item.getVolumeInfo().getAuthors())
                        .title(item.getVolumeInfo().getTitle())
                        .imageLinks(item.getVolumeInfo().getImageLinks())
                        .build());
            });
        });
        return bookInfoList;
    }

}
