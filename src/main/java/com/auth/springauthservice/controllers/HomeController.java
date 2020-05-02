package com.auth.springauthservice.controllers;

import com.auth.springauthservice.dto.LoginUserDto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
   
    @GetMapping(value = "/auth")
    public String loginPage(Model model){
        model.addAttribute("model",new LoginUserDto());
        return "login";
    }

    @GetMapping("/loginError")
    public String loginError(Model model){
        LoginUserDto user = new LoginUserDto();
        user.setIdOrPasswdNotMatch(true);
        model.addAttribute("model", user);
        return "login";
    }
}