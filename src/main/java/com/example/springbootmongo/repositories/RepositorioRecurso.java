package com.example.springbootmongo.repositories;

import com.example.springbootmongo.collections.Recurso;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioRecurso extends MongoRepository<Recurso, String> {

}
