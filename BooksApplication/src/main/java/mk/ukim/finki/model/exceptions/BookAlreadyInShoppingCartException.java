package mk.ukim.finki.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BookAlreadyInShoppingCartException extends RuntimeException {

    public BookAlreadyInShoppingCartException(Long bookId, Long userId) {
        super(String.format("Book with id: %d is already in cart for user %d",bookId, userId));
    }
}
