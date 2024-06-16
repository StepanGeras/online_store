package org.example.promenergosvet.controller.user;

import jakarta.servlet.http.HttpSession;
import org.example.promenergosvet.entity.basket.Basket;
import org.example.promenergosvet.entity.basket.BasketItem;
import org.example.promenergosvet.entity.user.PasswordResetToken;
import org.example.promenergosvet.entity.user.User;
import org.example.promenergosvet.service.basket.BasketService;
import org.example.promenergosvet.service.user.EmailService;
import org.example.promenergosvet.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private boolean isTokenExpired(PasswordResetToken token) {
        return token.getExpiryDate().before(new Date());
    }

    @GetMapping("/reg")
    public String reg(Model model) {
        model.addAttribute("user", new User());
        return "user/reg";
    }

    @PostMapping("/reg")
    public String reg(@ModelAttribute User user) {
        User findUser = userService.findByEmail(user.getEmail());
        if (findUser == null) {
            userService.save(user);
        }

        return "user/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "user/login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "user/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        User user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "Пользователь не найден");
            return "user/forgot-password";
        }

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String resetLink = "http://localhost:8080/user/reset-password/" + token;
        emailService.sendSimpleMessage(email, "Запрос на сброс пароля", "Нажмите на ссылку, чтобы сбросить пароль: " + resetLink);

        model.addAttribute("message", "Ссылка для сброса пароля отправлена на вашу электронную почту.");
        return "redirect:/user/login";
    }

    @GetMapping("/reset-password/{token}")
    public String showResetPasswordForm(@PathVariable String token, Model model) {
        PasswordResetToken resetToken = emailService.findByToken(token);
        if (resetToken == null || isTokenExpired(resetToken)) {
            model.addAttribute("error", "Недействительный или просроченный токен.");
            return "user/reset-password";
        }

        model.addAttribute("token", token);
        return "user/reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token, @RequestParam("password") String newPassword, Model model) {
        PasswordResetToken resetToken = emailService.findByToken(token);
        if (resetToken == null || isTokenExpired(resetToken)) {
            model.addAttribute("error", "Недействительный или просроченный токен.");
            return "user/reset-password";
        }

        User user = resetToken.getUser();
        user.setPassword(newPassword);
        userService.save(user);

        model.addAttribute("message", "Пароль успешно сброшен.");
        return "redirect:/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/page")
    public String page(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        List<Basket> basket = basketService.getAllBasketByUserId(user.getId());
        List<Long> basketIdList = new ArrayList<>();

        for (Basket basketItems : basket) {
            basketIdList.add(basketItems.getId());
        }

        List<BasketItem> basketItems = new ArrayList<>();

        for (Long basketId : basketIdList) {
            List<BasketItem> items = basketService.getBasketItemByBasketId(basketId);
            basketItems.addAll(items);
        }

        model.addAttribute("basket", basketItems);

        model.addAttribute("user", user);

        return "user/homePage";
    }

    @GetMapping("/setting")
    public String setting(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        model.addAttribute("user", user);
        return "user/setting";
    }

    @PostMapping("/setting")
    public String updateUser(@RequestParam String username,
                             @RequestParam String telephone,
                             @RequestParam String name,
                             @RequestParam String currentPassword,
                             @RequestParam(required = false) String newPassword,
                             @RequestParam(required = false) String confirmNewPassword,
                             Principal principal,
                             Model model) {
        User currentUser = userService.findByUsername(principal.getName());

        if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            model.addAttribute("error", "Неверный текущий пароль");
            model.addAttribute("user", currentUser);
            return "user/setting";
        }
        currentUser.setName(name);
        currentUser.setUsername(username);
        currentUser.setTelephone(telephone);

        if (newPassword != null && !newPassword.isEmpty()) {
            if (!newPassword.equals(confirmNewPassword)) {
                model.addAttribute("error", "Новый пароль и его подтверждение не совпадают");
                model.addAttribute("user", currentUser);
                return "user/setting";
            }
            currentUser.setPassword(newPassword);
        }

        userService.save(currentUser);

        return "redirect:/user/logout";
    }



}
