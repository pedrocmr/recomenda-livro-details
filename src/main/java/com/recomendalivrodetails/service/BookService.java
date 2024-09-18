package com.recomendalivrodetails.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recomendalivrodetails.entities.front.BookData;
import com.recomendalivrodetails.entities.front.BookDetails;
import com.recomendalivrodetails.entities.front.BookPreference;
import com.recomendalivrodetails.entities.google.BookInfo;
import com.recomendalivrodetails.entities.GoogleBooksResponse;
import com.recomendalivrodetails.repository.BookDataRepository;
import com.recomendalivrodetails.repository.BookPreferenceRepository;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class BookService {

    private final RestTemplate restTemplate;
    private final BookPreferenceRepository bookPreferenceRepository;
    private final BookDataRepository bookDataRepository;

    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=intitle:%s&maxResults=1";
    private static final String BOOK_AI_API= "http://localhost:3000/book_recommendations";

    public BookService(RestTemplate restTemplate, BookPreferenceRepository bookPreferenceRepository, BookDataRepository bookDataRepository) {
        this.restTemplate = restTemplate;
        this.bookPreferenceRepository = bookPreferenceRepository;
        this.bookDataRepository = bookDataRepository;
    }

    public List<GoogleBooksResponse> searchBookData(List<String> bookTitles) {
        List<GoogleBooksResponse> bookInfoList = new ArrayList<>();
        for (String title : bookTitles) {
            String url = String.format(GOOGLE_BOOKS_API_URL, title);
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
    public HttpResponse<String> retrieveBookListFromAI(List<String> genres){

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, List<String>> requestBody = Map.of("genres", genres);
        String jsonBody = objectMapper.writeValueAsString(requestBody);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(BOOK_AI_API))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    }

    public List<BookInfo> populateBookDetails(String email) {

        Optional<BookPreference> bookPreference = bookPreferenceRepository.findById(email);
        HttpResponse<String> response = retrieveBookListFromAI(bookPreference.get().getGenders());
        JSONObject jsonObject = new JSONObject(response.body());

        JSONArray recommendationsArray = jsonObject.getJSONArray("recommendations");
        List<String> namesList = new ArrayList<>();

        for (int i = 0; i < recommendationsArray.length(); i++) {
            JSONObject recommendation = recommendationsArray.getJSONObject(i);
            String name = recommendation.getString("name");
            namesList.add(name);
        }
        return retrieveBookInfo(namesList);
    }

    public Boolean saveBookPreference(BookPreference bookPreference) {
        if (bookPreferenceRepository.existsById(bookPreference.getEmail())) {
            return false;
        }
        bookPreferenceRepository.save(bookPreference);
        return true;
    }

    public Boolean getPreference(String email) {
        return bookPreferenceRepository.existsById(email);
    }


    public Boolean saveFavoriteBookData(BookData bookData) {
        Optional<BookData> data = bookDataRepository.findById(bookData.getEmail());
        if (data.isPresent()) {
            List<BookDetails> bookDetails = data.get().getLivros();
            bookDetails.add(bookData.getLivros().get(0));
            bookDataRepository.save(bookData);
            return true;
        }
        return false;
    }

    public BookData getFavoriteBookData(String email) {
        return bookDataRepository.findById(email).get();
    }
    public Boolean removeFavoriteBookData(String email, String idLivro) {
        Optional<BookData> data = bookDataRepository.findById(email);
        if (data.isPresent()) {
            BookData bookData = data.get();

            bookData.getLivros().forEach(book -> {
                if (book.getIdLivro().equals(idLivro)) {
                    bookData.getLivros().remove(book);
                }
            });
            bookDataRepository.save(bookData);
            return true;
        }
        return false;
    }
}
