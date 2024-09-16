package com.recomendalivrodetails.entities;

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
}
