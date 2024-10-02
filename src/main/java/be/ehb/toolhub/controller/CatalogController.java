package be.ehb.toolhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogController {

    @GetMapping("/product-list") // Route for product list
    public String productList() {
        return "product-list"; // Refers to product-list.html
    }

    @GetMapping("/product-catalog") // Route for product catalog
    public String productCatalog() {
        return "product-catalog"; // Refers to product.catalog.html
    }
}
