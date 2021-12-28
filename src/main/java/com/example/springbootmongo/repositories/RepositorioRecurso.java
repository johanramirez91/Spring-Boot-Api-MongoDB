package com.example.springbootmongo.repositories;

import com.example.springbootmongo.collections.Recurso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioRecurso extends MongoRepository<Recurso, String> {

}
