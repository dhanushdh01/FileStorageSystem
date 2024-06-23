package com.example.filestoragesystem.controller;

import com.example.filestoragesystem.entity.User;
import com.example.filestoragesystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/login";
    }


    // Add login controller methods if not already implemented
}
