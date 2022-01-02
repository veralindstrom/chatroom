package com.example.springboottutorial.id1212.controller;

import com.example.springboottutorial.id1212.entities.user.User;
import com.example.springboottutorial.id1212.entities.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private User user;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/home")
    public String findUser(@RequestParam String email , @RequestParam String password, Model model) {
        user = userRepository.findUserByEmailAndPassword(email, password);
        if(user != null){
            model.addAttribute("user", user);
            return "chat";
        }
        else {
            String message = "Invalid e-mail/password";
            model.addAttribute("message", message);
        }
        return "index";
    }

    @GetMapping("/home")
    public String test(Model model) {
        if(user != null){
            model.addAttribute("user", user);
            return "home";
        }
        else {
            String message = "You are logged out";
            model.addAttribute("message", message);
        }
        return "index";
    }

    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup";
    }

    @PostMapping("/signup-process")
    public String processRegister(User user) {
        userRepository.save(user);

        return "signup-success";
    }
}
