package org.example.promenergosvet.controller;

import lombok.AllArgsConstructor;
import org.example.promenergosvet.entity.Basket;
import org.example.promenergosvet.entity.User;
import org.example.promenergosvet.service.BasketService;
import org.example.promenergosvet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BasketService basketService;

    @PostMapping("/reg")
    public String registerUser(@ModelAttribute User user,
                               Model model,
                               @RequestParam(name = "catalog") String catalog,
                               @RequestParam(name = "addition") String addition) {

        User findUser = userService.findBySurname(user.getSurname());

        if (findUser == null) {
            userService.save(user);
        }

        String encodedCatalog = URLEncoder.encode(catalog, StandardCharsets.UTF_8);
        String encodedAddition = URLEncoder.encode(addition, StandardCharsets.UTF_8);
        user.setBasket(new Basket());
        model.addAttribute("user", user);
        return "redirect:/catalog/" + encodedCatalog + "/" + encodedAddition;
    }

    @GetMapping("/basket")
    public String basket(@SessionAttribute("user") User user, Model model) {

        if (user.getSurname() == null) {
            return "redirect:/reg";
        }

        model.addAttribute("basket", user.getBasket().getItems());
        model.addAttribute("user", user);
        return "basket";
    }

    @PostMapping("/basket/delete")
    public String deleteBasket(@RequestParam (name = "productId") Long id,
                               Model model,
                               @SessionAttribute("user") User user) {

        Basket basket = basketService.removeFromCart(user.getBasket().getId(), id);
        user.setBasket(basket);
        model.addAttribute("user", user);

        return "redirect:/user/basket";

    }

//    @PostMapping("basket/design")
//    public String designBasket(@SessionAttribute("user") User user, Model model) {
//
//    }

}
