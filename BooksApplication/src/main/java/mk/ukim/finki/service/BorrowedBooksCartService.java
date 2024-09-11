package mk.ukim.finki.service;

import mk.ukim.finki.model.Book;
import mk.ukim.finki.model.BorrowedBooksCart;

import java.util.List;

public interface BorrowedBooksCartService {
    List<Book> listAllBooksInCart(Long id);
    BorrowedBooksCart addBookInCart(String userId, Book book);
    BorrowedBooksCart returnBook(Long bookId, String userId);
}
