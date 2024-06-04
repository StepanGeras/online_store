package org.example.promenergosvet.controller;

import lombok.AllArgsConstructor;
import org.example.promenergosvet.entity.*;
import org.example.promenergosvet.service.*;
import org.example.promenergosvet.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private final HomeService homeService;

    private final AdditionService additionService;

    private final ProductService productService;

    private final CatalogService catalogService;

    private final BasketService basketService;

    private final UserService userService;

    private final HttpSession httpSession;

    @GetMapping("/")
    public String home(Model model, @RequestParam(defaultValue = "1") int page) {

        model.addAttribute("home", homeService.getAllHome());
        Page<Catalog> catalogPage = catalogService.getAllProduct(page - 1, 9, "name");

        model.addAttribute("page", catalogPage.getSize());
        model.addAttribute("catalog", catalogPage);

        return "home";
    }

    @GetMapping("/catalog")
    public String catalog(Model model, @RequestParam(defaultValue = "1") int page) {
        String catalog = "Каталог";
        Page<Catalog> currentPage = catalogService.getAllProduct(page - 1, 16, "name");

        model.addAttribute("page", page);
        model.addAttribute("productList", currentPage);
        model.addAttribute("totalPages", currentPage.getTotalPages());
        model.addAttribute("catalog", catalog);
        return "catalog";
    }

    @GetMapping("/catalog/{catalog}")
    public String addition(@PathVariable String catalog,
                       Model model,
                       @RequestParam(name = "idCatalog", required = false) Long idCatalog) {

        List<Addition> additions = additionService.findAdditionByCatalogId(idCatalog);

        if (additions.size() == 1) {

            String encodedProduct = URLEncoder.encode(catalog, StandardCharsets.UTF_8);

            return "redirect:/catalog/" + encodedProduct + "/" + encodedProduct;

        } else {

            model.addAttribute("catalog", catalog);
            model.addAttribute("addition", additions);

            return "addition";
        }

    }

    @GetMapping("/catalog/{catalog}/{addition}")
    public String product(Model model,
                          @PathVariable (name = "addition") String addition,
                          @PathVariable (name = "catalog") String catalog,
                          @RequestParam(defaultValue = "1") int page) {

        String newAddition;
        String newProduct = catalog.replace("+", " ");

        if (addition.equals("1P+N (двух-полюсные)")) {
            newAddition = addition;
        } else {
            newAddition = addition.replace("+", " ");
        }

        Long id = additionService.findIdByAncillary(newAddition);

        Page<Product> productPage = productService.getAllProductByAdditionId(page - 1, 16, "name", id);

        model.addAttribute("addition", addition);
        model.addAttribute("page", page);
        model.addAttribute("productList", productPage);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("catalog", newProduct);

        return "product";
    }

    @GetMapping("/catalog/{catalog}/{addition}/{id}")
    public String goods (Model model,
                         @PathVariable Long id,
                         @PathVariable String catalog,
                         @PathVariable String addition){

        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("catalog", catalog);
        model.addAttribute("addition", addition);
        model.addAttribute("id", id);

        return "goods";
    }


}
