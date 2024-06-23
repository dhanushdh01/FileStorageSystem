package com.example.filestoragesystem.controller;

import com.example.filestoragesystem.entity.User;
import com.example.filestoragesystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute User user) {
        // Check if username already exists
        if (userService.findByUsername(user.getUsername()) != null) {
            // Handle duplicate username scenario (e.g., return error message)
            return "redirect:/users/signup?error=usernameExists";
        }
        // Save user if username does not exist
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login"; // Renders the login form
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute User user, Model model) {
        // Retrieve user from database based on username
        User existingUser = userService.findByUsername(user.getUsername());

        // Check if user exists and password matches
        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            // Successful login
            model.addAttribute("user", existingUser);
            return "redirect:/files/upload"; // Redirect to the upload page
        } else {
            // Failed login
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Return to login page with error message
        }
    }


    @GetMapping("/logout")
    public String logout() {
        // Implement logout logic if necessary (e.g., invalidate session)
        return "redirect:/login";
    }
}