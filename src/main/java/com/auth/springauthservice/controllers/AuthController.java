package com.auth.springauthservice.controllers;

import java.util.Collection;

import com.auth.springauthservice.JwtTokenUtil;
import com.auth.springauthservice.dto.LoginUserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<LoginUserDto> authenticateUser(@ModelAttribute("model") LoginUserDto user) {
        UserDetails userDetails;
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getId(), user.getPasswd());
            Authentication auth = this.authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            userDetails = new UserDetails(){
            
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                @Override
                public boolean isEnabled() {
                    // TODO Auto-generated method stub
                    return false;
                }
            
                @Override
                public boolean isCredentialsNonExpired() {
                    // TODO Auto-generated method stub
                    return false;
                }
            
                @Override
                public boolean isAccountNonLocked() {
                    // TODO Auto-generated method stub
                    return false;
                }
            
                @Override
                public boolean isAccountNonExpired() {
                    // TODO Auto-generated method stub
                    return false;
                }
            
                @Override
                public String getUsername() {
                    // TODO Auto-generated method stub
                    return user.getId();
                }
            
                @Override
                public String getPassword() {
                    // TODO Auto-generated method stub
                    return user.getPasswd();
                }
            
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    // TODO Auto-generated method stub
                    return null;
                }
            };
        }catch(BadCredentialsException ex){
            return new ResponseEntity<LoginUserDto>(new LoginUserDto(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<LoginUserDto>(new LoginUserDto(user.getId(), user.getPasswd(),jwtTokenUtil.generateToken(userDetails)), HttpStatus.OK);
    }

}