package com.recomendalivrodetails.entities;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class VolumeInfo {
    String title;
    List<String> authors;
    ImageLinks imageLinks;
}
