package com.recomendalivrodetails.entities;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class IndustryIdentifier {
    String type;
    String identifier;
}
