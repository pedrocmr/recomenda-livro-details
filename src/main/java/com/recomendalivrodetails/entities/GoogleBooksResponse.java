package com.recomendalivrodetails.entities;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class GoogleBooksResponse {
    String kind;
    int totalItems;
    List<Item> items;
}
