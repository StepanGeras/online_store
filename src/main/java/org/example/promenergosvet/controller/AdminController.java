package org.example.promenergosvet.controller;

import lombok.AllArgsConstructor;
import org.example.promenergosvet.entity.Admin;
import org.example.promenergosvet.service.AdminService;
import org.example.promenergosvet.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private BasketService basketService;

    @GetMapping("/reg")
    public String reg(Model model) {
        model.addAttribute("admin", new Admin());
        return "reg";
    }

    @PostMapping("/reg")
    public String registerAdmin(@ModelAttribute("admin") Admin admin, Model model) {
        adminService.save(admin);
        return "redirect:/admin/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping()
    public String adminPanel(Model model) {

//        model.addAttribute("basket", basketService.findAll());

        return "admin";
    }

}
