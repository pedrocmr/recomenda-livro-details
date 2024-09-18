package com.recomendalivrodetails.entities.front;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Value
@Document(collection = "book-preference")
public class BookPreference {
    @Id
    String email;
    List<String> genders;
}
