package com.recomendalivrodetails.entities;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class GoogleBooksResponse {

    List<Item> items;
}
