package mk.ukim.finki.service.implementation;

import mk.ukim.finki.model.Book;
import mk.ukim.finki.model.BorrowedBooksCart;
import mk.ukim.finki.model.User;
import mk.ukim.finki.model.exceptions.BookNotFoundException;
import mk.ukim.finki.model.exceptions.CartNotFoundException;
import mk.ukim.finki.model.exceptions.UserNotFoundException;
import mk.ukim.finki.repository.BookRepository;
import mk.ukim.finki.repository.BorrowedBooksCartRepository;
import mk.ukim.finki.repository.UserRepository;
import mk.ukim.finki.service.BorrowedBooksCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowedBooksCartServiceImpl implements BorrowedBooksCartService {

    private final BorrowedBooksCartRepository borrowedBooksCartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BorrowedBooksCartServiceImpl(BorrowedBooksCartRepository borrowedBooksCartRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.borrowedBooksCartRepository = borrowedBooksCartRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> listAllBooksInCart(Long id) {
        Optional<BorrowedBooksCart> borrowedBooksCartOptional = borrowedBooksCartRepository.findById(id);

        if (borrowedBooksCartOptional.isEmpty()) {
            throw new CartNotFoundException(id);
        }
        return borrowedBooksCartOptional.get().getBorrowedBooks();
    }

    @Override
    public BorrowedBooksCart addBookInCart(String userId, Book book) {
        User user = userRepository.findByUsername(userId).orElseThrow(() -> new UserNotFoundException(userId));
        BorrowedBooksCart cart = user.getBooksCart();
        cart.getBorrowedBooks().add(book);
        return borrowedBooksCartRepository.save(cart);
    }

    @Override
    public BorrowedBooksCart returnBook(Long bookId, String userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        User user = userRepository.findByUsername(userId).orElseThrow(() -> new UserNotFoundException(userId));
        BorrowedBooksCart cart = user.getBooksCart();
        cart.getBorrowedBooks().remove(book);
        borrowedBooksCartRepository.save(cart);
        Integer copies = book.getAvailableCopies();
        copies+=1;
        book.setAvailableCopies(copies);
        bookRepository.save(book);
        return cart;
    }

}
