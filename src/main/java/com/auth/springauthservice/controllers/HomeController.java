package com.auth.springauthservice.controllers;

import com.auth.springauthservice.dto.LoginUserDto;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String homePage(){
        return "Hello World!";
    }      
   
    @GetMapping(value = "/auth")
    public String loginPage(Model model){
        model.addAttribute("model",new LoginUserDto());
        return "login";
    }

}