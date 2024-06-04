package org.example.promenergosvet.controller;

import jakarta.servlet.http.HttpSession;
import org.example.promenergosvet.entity.Basket;
import org.example.promenergosvet.entity.User;
import org.example.promenergosvet.service.BasketService;
import org.example.promenergosvet.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserService userService;

    @Autowired
    private BasketService basketService;

    @GetMapping("/reg")
    public String login(Model model) {

        model.addAttribute("user", new User());

        return "regUser";

    }

    @PostMapping("/reg")
    public String registerUser(@ModelAttribute User user) {

        User newUser = userService.save(user);
        newUser.setBasket(new Basket());
        httpSession.setAttribute("user", newUser);

        return "redirect:/catalog";

    }

    @GetMapping("/basket")
    public String basket(Model model) {

        User user = (User) httpSession.getAttribute("user");

        model.addAttribute("basket", user.getBasket().getItems());
        model.addAttribute("user", user);
        return "basket";
    }

    @PostMapping("/basket/add")
    public String addBasket(@RequestParam (name = "catalog") String catalog,
                            @RequestParam (name = "addition") String addition,
                            @RequestParam (name = "id") Long id
                            ) {

        User user = (User) httpSession.getAttribute("user");

        Basket basket = user.getBasket();
        Basket basket1 = basketService.addToCart(id, basket);
        user.setBasket(basket1);

        User newUser = userService.findBySurname(user.getSurname());
        httpSession.setAttribute("user", newUser);
        String encodedCatalog = URLEncoder.encode(catalog, StandardCharsets.UTF_8);
        String encodedAddition = URLEncoder.encode(addition, StandardCharsets.UTF_8);
        return "redirect:/catalog/" + encodedCatalog + "/" + encodedAddition;

    }

    @PostMapping("/basket/delete")
    public String deleteBasket(@RequestParam (name = "productId") Long id,
                               Model model) {

        User user = (User) httpSession.getAttribute("user");

        Basket basket = basketService.removeFromCart(user.getBasket().getId(), id);
        user.setBasket(basket);
        model.addAttribute("user", user);

        return "redirect:/user/basket";

    }

    @PostMapping("/basket/design")
    public String designBasket() {

        User user = (User) httpSession.getAttribute("user");

        basketService.saveBasketId(user);

        return "redirect:/";
    }

}
