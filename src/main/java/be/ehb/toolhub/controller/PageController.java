package be.ehb.toolhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/add-product")
    public String addProductPage() {
        return "add-product"; // Correspond au nom du fichier HTML sans l'extension
    }
     @GetMapping("/checkout")
    public String checkoutPage() {
        return "checkout"; // Zorg ervoor dat dit verwijst naar checkout.html
    }
}
