package mk.ukim.finki.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "borrowed_books_cart")
public class BorrowedBooksCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> borrowedBooks;

    public BorrowedBooksCart() {
        this.borrowedBooks = new ArrayList<>();
    }
}
