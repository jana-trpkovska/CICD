package mk.ukim.finki.service;

import mk.ukim.finki.model.User;
import mk.ukim.finki.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;

public interface AuthService extends UserDetailsService {
    User login(String username, String password);
    User register(String username, String password, String repeatPassword, String name, String surname, LocalDate dateOfBirth, Role role);
    User findById(String id);
}
