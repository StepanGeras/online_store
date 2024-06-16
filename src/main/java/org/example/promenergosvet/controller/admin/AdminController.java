package org.example.promenergosvet.controller.admin;

import org.example.promenergosvet.entity.basket.Basket;
import org.example.promenergosvet.entity.basket.BasketItem;
import org.example.promenergosvet.entity.user.User;
import org.example.promenergosvet.service.basket.BasketService;
import org.example.promenergosvet.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping()
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BasketService basketService;

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin/home";
    }

    @GetMapping("/admin/user")
    public String adminPanelHome(Model model) {

        List<User> userList = userService.findAll();

        List<Basket> allBasket = basketService.getAllBaskets();

        List<User> newUserList = userList.stream()
                .filter(user -> user.getRoles().contains(User.Role.ROLE_USER))
                .filter(user -> allBasket.stream()
                        .anyMatch(basket ->
                                basket.getRoles().contains(Basket.Role.ROLE_DESIGN) &&
                                        basket.getUser().equals(user)))
                .collect(Collectors.toList());

        model.addAttribute("users", newUserList);

        return "admin/placingOrders";
    }

    @GetMapping("/admin/user/{username}")
    public String adminPanel(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);
        List<Basket> allBasketByUserId = basketService.getAllBasketByUserId(user.getId());

        for (Basket basket : allBasketByUserId) {
            if (basket.getUser().equals(user)) {
                model.addAttribute("basket", basket.getItems());
            }
        }

        model.addAttribute("user", user);

        return "admin/user";

    }

    @PostMapping("/admin/{username}")
    public String deleteBasket(@PathVariable String username) {
        User user = userService.findByUsername(username);
        List<Basket> basketListByUserId = basketService.getAllBasketByUserId(user.getId());

        for (Basket basket : basketListByUserId) {
            if (basket.getRoles().contains(Basket.Role.ROLE_DESIGN)) {

                for (BasketItem basketItem : basket.getItems()) {
                    basketItem.setDate(new Date());
                }

                basket.setRoles(Set.of(Basket.Role.ROLE_ISSUED));
                basketService.save(basket);

                break;

            }
        }


        return "redirect:/admin";
    }

}
