package mk.ukim.finki.service.implementation;


import mk.ukim.finki.model.Author;
import mk.ukim.finki.model.Book;
import mk.ukim.finki.model.enumerations.Category;
import mk.ukim.finki.model.exceptions.AuthorNotFoundException;
import mk.ukim.finki.model.exceptions.BookNotFoundException;
import mk.ukim.finki.repository.BookRepository;
import mk.ukim.finki.service.AuthorService;
import mk.ukim.finki.service.BookService;
import mk.ukim.finki.service.BorrowedBooksCartService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BorrowedBooksCartService borrowedBooksCartService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, BorrowedBooksCartService borrowedBooksCartService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.borrowedBooksCartService = borrowedBooksCartService;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> save(String name, String categoryName, Long authorId, Integer availableCopies) {
        Category category = Category.valueOf(categoryName);
        Author author = authorService.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
        Book book = new Book(name,category,author,availableCopies);
        bookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public Optional<Book> edit(Long id, String name, String categoryName, Long authorId, Integer availableCopies) {
        Author author = authorService.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
        Category category = Category.valueOf(categoryName);
        Book book = findById(id).orElseThrow(() -> new BookNotFoundException(id));
        book.setName(name);
        book.setCategory(category);
        book.setAuthor(author);
        book.setAvailableCopies(availableCopies);
        bookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public boolean borrowBook(String userId, Long bookId) {
        Book book = findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        if (book.getAvailableCopies() >0){
            Integer copies = book.getAvailableCopies() - 1;
            book.setAvailableCopies(copies);
            bookRepository.save(book);
            borrowedBooksCartService.addBookInCart(userId, book);
            return true;
        }
        return false;
    }

}
