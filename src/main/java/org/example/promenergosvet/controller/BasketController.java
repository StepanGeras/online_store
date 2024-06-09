package org.example.promenergosvet.controller;

import jakarta.servlet.http.HttpSession;
import org.example.promenergosvet.entity.basket.Basket;
import org.example.promenergosvet.entity.basket.BasketItem;
import org.example.promenergosvet.entity.product.Product;
import org.example.promenergosvet.entity.user.User;
import org.example.promenergosvet.service.basket.BasketService;
import org.example.promenergosvet.service.product.ProductService;
import org.example.promenergosvet.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class BasketController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ProductService productService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private UserService userService;

    @GetMapping("/basket")
    public String basket(Model model) {

        Basket basket = (Basket) httpSession.getAttribute("basket");
        if (basket == null) {
            basket = new Basket();
        }

        model.addAttribute("basket",  basket.getItems());

        return "basket/basket";
    }

    @PostMapping("/basket/add")
    public String addBasket(@RequestParam(name = "catalog") String catalog,
                            @RequestParam (name = "addition") String addition,
                            @RequestParam (name = "id") Long id
    ) {

        Basket basket = (Basket) httpSession.getAttribute("basket");
        if (basket == null) {
            basket = new Basket();
        }

        Product product = productService.getProductById(id);
        basket.addItem(product);

        httpSession.setAttribute("basket", basket);

        String encodedCatalog = URLEncoder.encode(catalog, StandardCharsets.UTF_8);
        String encodedAddition = URLEncoder.encode(addition, StandardCharsets.UTF_8);
        return "redirect:/catalog/" + encodedCatalog + "/" + encodedAddition;

    }

    @PostMapping("/basket/delete")
    public String deleteBasket(@RequestParam (name = "productId") Long id) {

        Basket basket = (Basket) httpSession.getAttribute("basket");
        basket.removeItem(id);

        httpSession.setAttribute("basket", basket);

        return "redirect:/basket";

    }

    @PostMapping("/basket/design")
    public String designBasket() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userService.findByUsername(username);

        Basket basket = (Basket) httpSession.getAttribute("basket");

        basket.setUser(user);

        for (BasketItem basketItem : basket.getItems()) {
            basketItem.setBasket(basket);
        }

        basketService.save(basket);

        httpSession.setAttribute("basket", new Basket());

        return "redirect:/";
    }

}
