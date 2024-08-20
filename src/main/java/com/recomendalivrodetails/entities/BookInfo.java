package com.recomendalivrodetails.entities;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class BookInfo {

    String title;
    List<String> authors;
    String thumbnailUrl;
}
