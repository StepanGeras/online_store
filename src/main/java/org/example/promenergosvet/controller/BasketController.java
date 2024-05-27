package org.example.promenergosvet.controller;

import lombok.AllArgsConstructor;
import org.example.promenergosvet.entity.Basket;
import org.example.promenergosvet.entity.Product;
import org.example.promenergosvet.entity.User;
import org.example.promenergosvet.service.BasketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    @GetMapping
    public String basket(Model model) {

        model.addAttribute("basket", basketService.getAllBasket());

        return "basket";
    }

    @PostMapping
    public String deleteProductInBasket(Model model,
                                        @RequestParam (name = "productId") Long id,
                                        @RequestParam (name = "productName") String name,
                                        @RequestParam (name = "productText") String text) {

        Product productToRemove = basketService.getAllBasket().stream()
                .filter(p -> p.getId().equals(id) && p.getName().equals(name) && p.getText().equals(text))
                .findFirst()
                .orElse(null);

        basketService.deleteProductBasket(productToRemove);

        model.addAttribute("basket", basketService.getAllBasket());

        return "basket";
    }

    @PostMapping("/design")
    public String design (Model model,
                          @RequestParam (name = "name") String name,
                          @RequestParam (name = "surname") String surname,
                          @RequestParam (name = "telephone") String telephone) {

        User user = new User();

        user.setName(name);
        user.setSurname(surname);
        user.setTelephone(telephone);

        basketService.saveBasket(user);

        return "redirect:/";
    }

}
