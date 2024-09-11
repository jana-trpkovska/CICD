package mk.ukim.finki.repository;

import mk.ukim.finki.model.BorrowedBooksCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BorrowedBooksCartRepository extends JpaRepository<BorrowedBooksCart,Long> {
}
