package com.recomendalivrodetails.entities;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Item {
    String kind;
    String id;
    String etag;
    String selfLink;
    VolumeInfo volumeInfo;
}
