package org.example.promenergosvet.controller;

import org.example.promenergosvet.entity.basket.Basket;
import org.example.promenergosvet.entity.user.User;
import org.example.promenergosvet.service.basket.BasketService;
import org.example.promenergosvet.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping()
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BasketService basketService;

    @GetMapping("/admin")
    public String adminPanelHome(Model model) {

        List<User> userList = userService.findAll();

        List<User> nonAdminUsers = userList.stream()
                .filter(user -> !user.getRoles().contains(User.Role.ROLE_ADMIN))
                .toList();

        model.addAttribute("users", nonAdminUsers);

        return "admin/admin";
    }

    @GetMapping("/admin/{username}")
    public String adminPanel(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);
        Basket basket = basketService.getBasketByUserId(user.getId());

        model.addAttribute("basket", basket.getItems());
        model.addAttribute("user", user);

        return "admin/user";

    }

    @PostMapping("/admin/{username}")
    public String deleteBasket(@PathVariable String username) {
        User user = userService.findByUsername(username);
        basketService.deleteBasketByUserId(user.getId());
        return "redirect:/admin";
    }

}
