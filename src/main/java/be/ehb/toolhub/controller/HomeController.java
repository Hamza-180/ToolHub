package be.ehb.toolhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // Verwijst naar index.html in templates folder
    }
}
