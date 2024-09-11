package mk.ukim.finki.model.exceptions;


public class UsernameExistsException extends RuntimeException{

    public UsernameExistsException(String username) {
        super(String.format("Username with id %s already exists",username));

    }
}
