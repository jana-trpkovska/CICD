package mk.ukim.finki.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.model.BorrowedBooksCart;
import mk.ukim.finki.model.User;
import mk.ukim.finki.service.AuthService;
import mk.ukim.finki.service.BorrowedBooksCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class BorrowedBooksCartController {

    private final AuthService authService;
    private final BorrowedBooksCartService borrowedBooksCartService;

    public BorrowedBooksCartController(AuthService authService, BorrowedBooksCartService borrowedBooksCartService) {
        this.authService = authService;
        this.borrowedBooksCartService = borrowedBooksCartService;
    }

    @GetMapping()
    public String getCartPage(@RequestParam(required = false) String error, HttpServletRequest request, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        String username = request.getRemoteUser();
        User user = authService.findById(username);
        model.addAttribute("bodyContent", "cart");
        model.addAttribute("user", user);
        model.addAttribute("cart", user.getBooksCart());
        return "master-template";
    }

    @GetMapping("/return-book/{bookId}")
    public String returnBook(@PathVariable Long bookId, HttpServletRequest request){
        BorrowedBooksCart cart = borrowedBooksCartService.returnBook(bookId, request.getRemoteUser());
        return "redirect:/cart";
    }

}
