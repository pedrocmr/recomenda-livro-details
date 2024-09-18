package com.recomendalivrodetails.repository;

import com.recomendalivrodetails.entities.front.BookPreference;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookPreferenceRepository extends MongoRepository<BookPreference, String> {
}
