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
import java.util.List;
import java.util.Set;
import static org.example.promenergosvet.variables.Const.BASKET_STRING;

@Controller
public class BasketController {

    private final HttpSession httpSession;
    private final ProductService productService;
    private final BasketService basketService;
    private final UserService userService;

    @Autowired
    public BasketController(HttpSession httpSession, ProductService productService, BasketService basketService, UserService userService) {
        this.httpSession = httpSession;
        this.productService = productService;
        this.basketService = basketService;
        this.userService = userService;
    }

    @GetMapping("/basket")
    public String basket(Model model) {

        Basket basket = (Basket) httpSession.getAttribute(BASKET_STRING);
        if (basket == null) {
            basket = new Basket();
        }

        model.addAttribute(BASKET_STRING,  basket.getItems());

        return "basket/basket";
    }

    @PostMapping("/basket/add")
    public String addBasket(@RequestParam(name = "catalog") String catalog,
                            @RequestParam (name = "addition") String addition,
                            @RequestParam (name = "id") Long id) {

        Basket basket = (Basket) httpSession.getAttribute(BASKET_STRING);
        if (basket == null) {
            basket = new Basket();
        }

        Product product = productService.getProductById(id);
        basket.addItem(product);

        httpSession.setAttribute(BASKET_STRING, basket);

        if (addition.isEmpty()) {
            return "redirect:/catalog";
        } else {
            String encodedCatalog = URLEncoder.encode(catalog, StandardCharsets.UTF_8);
            String encodedAddition = URLEncoder.encode(addition, StandardCharsets.UTF_8);
            return "redirect:/catalog/" + encodedCatalog + "/" + encodedAddition;
        }

    }

    @PostMapping("/basket/delete")
    public String deleteBasket(@RequestParam (name = "productId") Long id) {

        Basket basket = (Basket) httpSession.getAttribute(BASKET_STRING);
        basket.removeItem(id);

        httpSession.setAttribute(BASKET_STRING, basket);

        return "redirect:/basket";

    }

    @PostMapping("/basket/design")
    public String designBasket() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userService.findByUsername(username);

        Basket basket = (Basket) httpSession.getAttribute(BASKET_STRING);

        List<Basket> basketList = basketService.getAllBasketByUserId(user.getId());

        for (Basket bas : basketList) {
            if (!bas.getRoles().contains(Basket.Role.ROLE_ISSUED)) {
                List<BasketItem> basketItem = basket.getItems();
                Basket basket1 = basketService.getBasketByUserId(user.getId());

                for (BasketItem item : basketItem) {
                    item.setBasket(basket1);
                    basketService.saveBasketItem(item);
                }
                return "redirect:/";
            }
        }

        for (BasketItem basketItem : basket.getItems()) {
            basketItem.setBasket(basket);
        }

        basket.setUser(user);

        basket.setRoles(Set.of(Basket.Role.ROLE_DESIGN));

        basketService.save(basket);

        httpSession.setAttribute(BASKET_STRING, new Basket());

        return "redirect:/";
    }

}
