package mk.ukim.finki.service.implementation;


import mk.ukim.finki.model.Author;
import mk.ukim.finki.model.exceptions.AuthorNotFoundException;
import mk.ukim.finki.repository.AuthorRepository;
import mk.ukim.finki.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Optional<Author> save(String name, String surname) {
        Author author = new Author(name,surname);
        authorRepository.save(author);
        return Optional.of(author);
    }

    @Override
    public Optional<Author> edit(Long id, String name, String surname) {
        Author author = findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
        author.setName(name);
        author.setSurname(surname);
        authorRepository.save(author);
        return Optional.of(author);
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
