package mk.ukim.finki.service;

import mk.ukim.finki.model.Book;
import mk.ukim.finki.model.enumerations.Category;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();
    Optional<Book> findById(Long id);
    Optional<Book> save(String name, String categoryName, Long authorId, Integer availableCopies);
    Optional<Book> edit(Long id, String name, String categoryName, Long authorId, Integer availableCopies);
    void deleteById(Long id);
    boolean borrowBook(String userId, Long bookId);
}