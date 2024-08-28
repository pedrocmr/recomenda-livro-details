package com.recomendalivrodetails.entities;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class VolumeInfo {
    String title;
    List<String> authors;
    String publisher;
    String publishedDate;
    String description;
    List<IndustryIdentifier> industryIdentifiers;
    int pageCount;
    ImageLinks imageLinks;
    String language;
    String previewLink;
    String infoLink;
    String canonicalVolumeLink;
}
