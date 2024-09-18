package com.recomendalivrodetails.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recomendalivrodetails.entities.front.BookData;
import com.recomendalivrodetails.entities.front.BookPreference;
import com.recomendalivrodetails.entities.google.BookInfo;
import com.recomendalivrodetails.entities.GoogleBooksResponse;
import com.recomendalivrodetails.repository.BookDataRepository;
import com.recomendalivrodetails.repository.BookPreferenceRepository;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private final RestTemplate restTemplate;
    private final BookPreferenceRepository bookPreferenceRepository;
    private final BookDataRepository bookDataRepository;

    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=intitle:";
    private static final String BOOK_AI_API= "http://localhost:3000/book_recommendations";

    public BookService(RestTemplate restTemplate, BookPreferenceRepository bookPreferenceRepository, BookDataRepository bookDataRepository) {
        this.restTemplate = restTemplate;
        this.bookPreferenceRepository = bookPreferenceRepository;
        this.bookDataRepository = bookDataRepository;
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
//CALL API DECIO
    @SneakyThrows
    public void retrieveBookListFromAI(List<String> genres){

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, List<String>> requestBody = Map.of("genres", genres);
        String jsonBody = objectMapper.writeValueAsString(requestBody);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(BOOK_AI_API))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    }
//SALVAR E REMOVER PREFERENCIAS
    public Boolean saveBookPreference(BookPreference bookPreference) {
        if (bookPreferenceRepository.existsById(bookPreference.getEmail())) {
            return false;
        }
        bookPreferenceRepository.save(bookPreference);
        return true;
    }
    public void removeBookPreference(BookPreference bookPreference) {
        bookPreferenceRepository.delete(bookPreference);
    }

    public Boolean getPreference(String email) {
        return bookPreferenceRepository.existsById(email);
    }


//FAVOTIRAR LIVROS
    public void saveFavoriteBookData(BookData bookData) {
        bookDataRepository.save(bookData);
    }

    public void getFavoriteBookData(String email) {

    }
}
