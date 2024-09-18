package com.recomendalivrodetails.repository;

import com.recomendalivrodetails.entities.front.BookData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookDataRepository extends MongoRepository<BookData, String> {
}
