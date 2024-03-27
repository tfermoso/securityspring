package com.ceica.securityspring.controller;

import com.ceica.securityspring.model.User;
import com.ceica.securityspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class loginController {
    private UserService userService;

    @Autowired
    public loginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute User user,@RequestParam("fotoUser") MultipartFile foto) {
        //Encriptar contraseña
        user.setPassword(user.getPassword());
        user.setEnabled(true);
        userService.crearUsuario(user,foto);
        System.out.println(user);
        return "register";
    }

}
