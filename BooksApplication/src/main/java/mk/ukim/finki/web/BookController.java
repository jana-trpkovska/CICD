package mk.ukim.finki.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.model.Author;
import mk.ukim.finki.model.Book;
import mk.ukim.finki.model.User;
import mk.ukim.finki.model.enumerations.Category;
import mk.ukim.finki.model.exceptions.BookNotFoundException;
import mk.ukim.finki.service.AuthService;
import mk.ukim.finki.service.AuthorService;
import mk.ukim.finki.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = {"/" ,"/books"})
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final AuthService authService;


    public BookController(BookService bookService, AuthorService authorService, AuthService authService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.authService = authService;
    }

    @GetMapping
    public String getBooksPage(Model model){
        model.addAttribute("bodyContent", "books");
        model.addAttribute("books", bookService.findAll());
        return "master-template";
    }

    @GetMapping("/add-book")
    public String getAddBookPage(Model model){
        List<Author> authors = authorService.findAll();
        List<Category> categories = Arrays.stream(Category.values()).toList();
        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-book");
        return "master-template";
    }

    @GetMapping("/edit-book/{id}")
    public String getEditBookPage(Model model, @PathVariable Long id){
        Book book = bookService.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        List<Author> authors = authorService.findAll();
        List<Category> categories = Arrays.stream(Category.values()).toList();
        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);
        model.addAttribute("book", book);
        model.addAttribute("bodyContent", "add-book");
        return "master-template";
    }

    @PostMapping("/add")
    public String addBook(@RequestParam String title, @RequestParam String category, @RequestParam Long authorId, @RequestParam Integer availableCopies, @RequestParam String bookId){
        if (bookId!=null && !bookId.isEmpty()){
            bookService.edit(Long.parseLong(bookId),title,category,authorId,availableCopies);
        }
        else {
            bookService.save(title,category,authorId,availableCopies);
        }
        return "redirect:/books";
    }

    @GetMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable Long id){
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/add-to-cart/{bookId}")
    public String addBookToCart(@PathVariable Long bookId, HttpServletRequest request){
        String userId = request.getRemoteUser();
        boolean result = bookService.borrowBook(userId, bookId);
        if (result){
            return "redirect:/cart";
        }
        return "redirect:/no-more-copies";
    }

    @GetMapping("/no-more-copies")
    public String getNoMoreCopiesPage(Model model){
        model.addAttribute("bodyContent", "no-more-copies");
        return "master-template";
    }

}
