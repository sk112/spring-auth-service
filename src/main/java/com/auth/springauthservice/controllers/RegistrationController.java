package com.auth.springauthservice.controllers;

import javax.validation.Valid;

import com.auth.springauthservice.dto.UserDto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RegistrationController {
    

    @GetMapping(value="/register")
    public String postMethodName(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto){
       return null;
    }    
}