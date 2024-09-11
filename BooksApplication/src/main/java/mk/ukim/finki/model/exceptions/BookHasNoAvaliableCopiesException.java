package mk.ukim.finki.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)

public class BookHasNoAvaliableCopiesException extends RuntimeException{
    public BookHasNoAvaliableCopiesException(Long id) {
        super(String.format("Book with id: %d has no available copies",id));
    }

}
