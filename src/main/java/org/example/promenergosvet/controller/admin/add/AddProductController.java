package org.example.promenergosvet.controller.admin.add;


import org.example.promenergosvet.entity.product.Addition;
import org.example.promenergosvet.entity.product.Catalog;
import org.example.promenergosvet.entity.product.Product;
import org.example.promenergosvet.service.product.AdditionService;
import org.example.promenergosvet.service.product.CatalogService;
import org.example.promenergosvet.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping
public class AddProductController {

    private final CatalogService catalogService;
    private final AdditionService additionService;
    private final ProductService productService;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${image.path}")
    private String imagePath;

    @Autowired
    public AddProductController(CatalogService catalogService, AdditionService additionService, ProductService productService) {
        this.catalogService = catalogService;
        this.additionService = additionService;
        this.productService = productService;
    }

    @GetMapping("/admin/catalog")
    public String addAdmin(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<Catalog> currentPage = catalogService.getAllProduct(page - 1, 16, "name");

        model.addAttribute("page", page);
        model.addAttribute("productList", currentPage);
        model.addAttribute("totalPages", currentPage.getTotalPages());
        return "admin/add/catalog";
    }

    @GetMapping("/admin/catalog/{catalog}")
    public String addition(@PathVariable String catalog,
                           Model model,
                           @RequestParam(name = "idCatalog", required = false) Long idCatalog) {

        List<Addition> additions = additionService.findAllAdditionByCatalogId(idCatalog);

        if (additions.size() == 1) {

            String encodedProduct = URLEncoder.encode(catalog, StandardCharsets.UTF_8);

            return "redirect:/admin/catalog/" + encodedProduct + "/" + encodedProduct;

        } else {

            model.addAttribute("catalog", catalog);
            model.addAttribute("addition", additions);

            return "admin/add/addition";
        }

    }

    @GetMapping("/admin/catalog/{catalog}/{addition}")
    public String addition(@PathVariable String catalog,
                           @PathVariable String addition,
                           Model model) {

        Addition findAddition = additionService.findAdditionByAncillary(addition);

        model.addAttribute("catalog", catalog);
        model.addAttribute("addition", addition);
        model.addAttribute("findAddition", findAddition);
        model.addAttribute("product", new Product());

        return "admin/add/product";

    }

    @PostMapping("/admin/catalog/{catalog}/{addition}")
    public String addProduct(@PathVariable(name = "catalog") String catalog,
                             @PathVariable(name = "addition") String addition,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "text") String text,
                             @RequestParam(name = "file") MultipartFile file) throws IOException {

        File dest = new File(uploadPath + File.separator + file.getOriginalFilename());

        file.transferTo(dest);

        Product product = new Product();
        product.setName(name);
        product.setText(text);
        product.setImage(imagePath + File.separator + file.getOriginalFilename());

        Addition findAddition = additionService.findAdditionByAncillary(addition);
        product.setAddition(findAddition);
        productService.save(product);

        return "redirect:/admin";

    }

}
