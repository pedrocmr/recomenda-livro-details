package com.recomendalivrodetails.entities.google;

import com.recomendalivrodetails.entities.ImageLinks;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class BookInfo {
    String id;
    String title;
    List<String> authors;
    ImageLinks imageLinks;
    String publishedDate;
    String isbn;
}
