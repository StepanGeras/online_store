package org.example.promenergosvet.controller.admin.delete;

import org.example.promenergosvet.entity.product.Addition;
import org.example.promenergosvet.entity.product.Catalog;
import org.example.promenergosvet.entity.product.Product;
import org.example.promenergosvet.service.product.AdditionService;
import org.example.promenergosvet.service.product.CatalogService;
import org.example.promenergosvet.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping
public class DeleteProductController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private AdditionService additionService;

    @Autowired
    private ProductService productService;

    @GetMapping("/admin/delete/catalog")
    public String deleteAdmin(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<Catalog> currentPage = catalogService.getAllProduct(page - 1, 16, "name");

        model.addAttribute("page", page);
        model.addAttribute("productList", currentPage);
        model.addAttribute("totalPages", currentPage.getTotalPages());
        return "admin/delete/catalog";
    }

    @GetMapping("/admin/delete/catalog/{catalog}")
    public String deleteAddition(@PathVariable String catalog,
                                 Model model,
                                 @RequestParam(name = "idCatalog", required = false) Long idCatalog) {

        List<Addition> additions = additionService.findAllAdditionByCatalogId(idCatalog);

        if (additions.size() == 1) {
            String encodedProduct = URLEncoder.encode(catalog, StandardCharsets.UTF_8);
            return "redirect:/admin/delete/catalog/" + encodedProduct + "/" + encodedProduct;
        } else {
            model.addAttribute("catalog", catalog);
            model.addAttribute("addition", additions);
            return "admin/delete/addition";
        }
    }

    @GetMapping("/admin/delete/catalog/{catalog}/{addition}")
    public String deleteProduct(@PathVariable(name = "catalog") String catalog,
                                @PathVariable(name = "addition") String addition,
                                Model model,
                                @RequestParam(defaultValue = "1") int page){

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

        return "admin/delete/product";

    }

    @PostMapping("/admin/delete/catalog/{catalog}/{addition}/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {

        productService.deleteProductById(id);

        return "redirect:/admin";
    }

}
