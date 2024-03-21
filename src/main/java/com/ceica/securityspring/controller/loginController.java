package com.ceica.securityspring.controller;

import com.ceica.securityspring.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class loginController {

    @GetMapping("/login")
    public String login(){
        return  "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @PostMapping("/register")
    public String postRegister(@ModelAttribute User user){
        System.out.println(user);
        return "register";
    }

}
