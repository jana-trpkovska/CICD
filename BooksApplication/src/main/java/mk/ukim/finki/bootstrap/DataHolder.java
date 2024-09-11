package mk.ukim.finki.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.model.Author;
import mk.ukim.finki.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    @Autowired
    private AuthorRepository authorRepository;


//    @PostConstruct
//    public void init(){
//
//        Author author1 = new Author("J.K.", "Rowling");
//        Author author2 = new Author("George", "Orwell");
//        Author author3 = new Author("J.R.R.", "Tolkien");
//
//        authorRepository.save(author1);
//        authorRepository.save(author2);
//        authorRepository.save(author3);
//    }
}
