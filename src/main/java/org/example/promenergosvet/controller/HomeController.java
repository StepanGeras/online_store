package org.example.promenergosvet.controller;

import lombok.AllArgsConstructor;
import org.example.promenergosvet.entity.product.Addition;
import org.example.promenergosvet.entity.product.Catalog;
import org.example.promenergosvet.entity.product.Product;
import org.example.promenergosvet.service.*;
import org.example.promenergosvet.service.product.AdditionService;
import org.example.promenergosvet.service.product.CatalogService;
import org.example.promenergosvet.service.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.example.promenergosvet.variables.Const.*;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final AdditionService additionService;
    private final ProductService productService;
    private final CatalogService catalogService;

    @GetMapping("/")
    public String home(Model model, @RequestParam(defaultValue = "1") int page) {

        model.addAttribute("home", homeService.getAllHome());
        Page<Catalog> catalogPage = catalogService.getAllProduct(page - 1, 9, "name");

        model.addAttribute("page", catalogPage.getSize());
        model.addAttribute(CATALOG_STRING, catalogPage);

        return "home";
    }

    @GetMapping("/catalog")
    public String catalog(Model model, @RequestParam(defaultValue = "1") int page) {
        String catalog = "Каталог";
        Page<Catalog> currentPage = catalogService.getAllProduct(page - 1, 16, "name");

        model.addAttribute("page", page);
        model.addAttribute(PRODUCT_LIST_STRING, currentPage);
        model.addAttribute("totalPages", currentPage.getTotalPages());
        model.addAttribute(CATALOG_STRING, catalog);
        return "product/catalog";
    }

    @GetMapping("/catalog/{catalog}")
    public String addition(@PathVariable String catalog,
                       Model model,
                       @RequestParam(name = "idCatalog", required = false) Long idCatalog) {

        List<Addition> additions = additionService.findAllAdditionByCatalogId(idCatalog);

        if (additions.size() == 1) {

            String encodedProduct = URLEncoder.encode(catalog, StandardCharsets.UTF_8);

            return "redirect:/catalog/" + encodedProduct + "/" + encodedProduct;

        } else {

            model.addAttribute(CATALOG_STRING, catalog);
            model.addAttribute(ADDITION_STRING, additions);

            return "product/addition";
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

        model.addAttribute(ADDITION_STRING, addition);
        model.addAttribute("page", page);
        model.addAttribute(PRODUCT_LIST_STRING, productPage);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute(CATALOG_STRING, newProduct);

        return "product/product";
    }

    @GetMapping("/catalog/{catalog}/{addition}/{id}")
    public String goods (Model model,
                         @PathVariable Long id,
                         @PathVariable String catalog,
                         @PathVariable String addition){

        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute(CATALOG_STRING, catalog);
        model.addAttribute(ADDITION_STRING, addition);
        model.addAttribute("id", id);

        return "product/goods";
    }

    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(name = "search") String search) {
        List<Product> productPage = productService.searchByNameOrText(search);

        model.addAttribute(PRODUCT_LIST_STRING, productPage);
        model.addAttribute(ADDITION_STRING, null);
        model.addAttribute(CATALOG_STRING, search);

        return "product/product";

    }

    @GetMapping("/search/{id}")
    public String search(@PathVariable Long id, Model model) {

        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("id", id);

        return "product/goods";

    }

}
