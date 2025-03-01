package be.ehb.toolhub.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

@GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

   @GetMapping("/login")
public ModelAndView login(Authentication authentication) {
    if (authentication != null && authentication.isAuthenticated()) {
        // Als de gebruiker al is ingelogd, redirect naar de homepagina  anders naar login
        return new ModelAndView("redirect:/dashboard");
    }
    return new ModelAndView("login");
}

@GetMapping("/register")
public ModelAndView register(Authentication authentication) {
    if (authentication != null && authentication.isAuthenticated()) {
        // Als de gebruiker al is ingelogd, redirect naar de homepagina anders naar register
        return new ModelAndView("redirect:/");
    }
    return new ModelAndView("register");
}

}
