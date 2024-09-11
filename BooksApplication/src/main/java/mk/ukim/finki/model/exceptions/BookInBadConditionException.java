package mk.ukim.finki.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)

public class BookInBadConditionException extends RuntimeException{
    public BookInBadConditionException(Long id) {
        super(String.format("Book with id: %d is in bad condition",id));
    }

}
