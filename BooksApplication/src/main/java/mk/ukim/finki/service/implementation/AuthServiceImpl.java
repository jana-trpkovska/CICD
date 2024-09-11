package mk.ukim.finki.service.implementation;


import jakarta.transaction.Transactional;
import mk.ukim.finki.model.BorrowedBooksCart;
import mk.ukim.finki.model.User;
import mk.ukim.finki.model.enumerations.Role;
import mk.ukim.finki.model.exceptions.*;
import mk.ukim.finki.repository.UserRepository;
import mk.ukim.finki.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String username, String password) {
        if(username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return this.userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }

    @Transactional
    public User register(String username, String password, String repeatPassword, String name, String surname, LocalDate dateOfBirth, Role role) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()){
            throw new InvalidArgumentsException();
        }
        if (!password.equals(repeatPassword)){
            throw new PasswordsDoNotMatchException();
        }
        if (userRepository.findByUsername(username).isPresent()){
            throw new UsernameExistsException(username);
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, name, surname, dateOfBirth, role);
        BorrowedBooksCart cart = new BorrowedBooksCart();
        user.setBooksCart(cart);

        userRepository.save(user);
        return user;
    }

    @Override
    public User findById(String id) {
        return userRepository.findByUsername(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}