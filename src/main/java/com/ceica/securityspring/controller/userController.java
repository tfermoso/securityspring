package com.ceica.securityspring.controller;

import com.ceica.securityspring.model.User;
import com.ceica.securityspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class userController {
    private UserRepository userRepository;

    @Autowired
    public userController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public String user(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByUsername(authentication.getName());
        model.addAttribute("user",user);
        return "user";
    }
}
