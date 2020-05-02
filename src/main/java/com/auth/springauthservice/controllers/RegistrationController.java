package com.auth.springauthservice.controllers;

import javax.validation.Valid;

import com.auth.springauthservice.dto.LoginUserDto;
import com.auth.springauthservice.dto.UserDto;
import com.auth.springauthservice.services.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RegistrationController {
    
    @Autowired
    private UserServiceImpl userServiceImpl;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping(value="/register")
    public String postMethodName(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto){
      
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        String confirmPasswd = userDto.getConfirmPassword();
        String firstName = userDto.getFirstName();
        String lastName = userDto.getLastName();
        
        

        if(!password.equals(confirmPasswd)){
            UserDto errorUserDto = new UserDto();
            errorUserDto.setPasswdDoesNotMatch(true);
            return new ModelAndView("register", "user", errorUserDto);
        }

        com.auth.springauthservice.models.User user = new com.auth.springauthservice.models.User(firstName, lastName,
                email, password);

        userServiceImpl.save(user);
        
        LoginUserDto loginUser = new LoginUserDto();
        loginUser.setLoginAgain(true);
        return new ModelAndView("login", "model", loginUser);
    }    
}