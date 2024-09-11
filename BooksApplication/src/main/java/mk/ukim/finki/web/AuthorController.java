package mk.ukim.finki.web;

import mk.ukim.finki.model.Author;
import mk.ukim.finki.model.exceptions.AuthorNotFoundException;
import mk.ukim.finki.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String getAuthorsPage(Model model){
        model.addAttribute("bodyContent", "authors");
        model.addAttribute("authors", authorService.findAll());
        return "master-template";
    }

    @GetMapping("/add-author")
    public String getAddAuthorPage(Model model){
        model.addAttribute("bodyContent", "add-author");
        return "master-template";
    }

    @GetMapping("/edit-author/{id}")
    public String getEditBookPage(Model model, @PathVariable Long id){
        Author author = authorService.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
        model.addAttribute("author", author);
        model.addAttribute("bodyContent", "add-author");
        return "master-template";
    }

    @PostMapping("/add")
    public String addAuthor(@RequestParam String firstName, @RequestParam String lastName, @RequestParam(required = false) Long authorId){
        if (authorId!=null){
            authorService.edit(authorId,firstName,lastName);
        }
        else {
            authorService.save(firstName, lastName);
        }
        return "redirect:/authors";
    }

    @GetMapping("/delete-author/{id}")
    public String deleteBook(@PathVariable Long id){
        authorService.deleteById(id);
        return "redirect:/authors";
    }
}
