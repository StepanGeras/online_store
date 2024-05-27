package org.example.promenergosvet.controller;

import lombok.AllArgsConstructor;
import org.example.promenergosvet.entity.User;
import org.example.promenergosvet.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(User newUser, Model model) {

        User user = userService.findBySurname(newUser.getSurname());

//        if (Base64.getEncoder().encodeToString(newUser.getPassword().getBytes()).equals(user.get().getPassword())) {
//            model.addAttribute("user", user);
//            return "admin";
//        } else {
//            model.addAttribute("error", "Неправильно введен логин или пароль");
//            return "login";
//        }

        return "login";

    }



}
