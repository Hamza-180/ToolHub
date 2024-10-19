package be.ehb.toolhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/add-product")
    public String addProductPage() {
        return "add-product"; // Zorg ervoor dat deze pagina bestaat in de templates map
    }

    @GetMapping("/checkout")
    public String checkoutPage() {
        return "checkout"; // Zorg ervoor dat deze pagina bestaat in de templates map
    }

    @GetMapping("/confirmation")
    public String confirmationPage() {
        return "confirmation"; // Zorg ervoor dat deze pagina bestaat in de templates map
    }
}
