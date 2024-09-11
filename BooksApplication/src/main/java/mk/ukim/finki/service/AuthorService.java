package mk.ukim.finki.service;

import mk.ukim.finki.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> findAll();
    Optional<Author> findById(Long id);
    Optional<Author> save(String name, String surname);
    Optional<Author> edit(Long id, String name, String surname);
    void deleteById(Long id);
}
