package com.recomendalivrodetails.entities.front;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Value
@Document(collection = "book-data")
public class BookData {
    String email;
    List<BookDetails> livros;
}
