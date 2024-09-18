package com.recomendalivrodetails.entities.front;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class BookAI {
    List<String> name;
}
