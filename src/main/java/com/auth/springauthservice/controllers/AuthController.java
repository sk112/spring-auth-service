package com.auth.springauthservice.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.auth.springauthservice.JwtTokenUtil;
import com.auth.springauthservice.dto.LoginUserDto;
import com.auth.springauthservice.services.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/authenticate")
    public ResponseEntity<LoginUserDto> authenticateUser(@ModelAttribute("model") LoginUserDto user,
            HttpServletResponse response) throws IOException {

        try{
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getId());
       
            try{
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
                Authentication auth = this.authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                return new ResponseEntity<LoginUserDto>(new LoginUserDto(user.getId(), user.getPasswd(),jwtTokenUtil.generateToken(userDetails)), HttpStatus.OK);
            }catch(BadCredentialsException ex){

                response.sendRedirect("/loginError");
                return new ResponseEntity<LoginUserDto>(new LoginUserDto(user.getId(), user.getPasswd()), HttpStatus.NOT_ACCEPTABLE);
            }

        }catch(UsernameNotFoundException ex){
            response.sendRedirect("/loginError");
            return new ResponseEntity<LoginUserDto>(new LoginUserDto(user.getId(), user.getPasswd()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

}