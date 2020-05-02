package com.auth.springauthservice.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    DataSource datasource;

    @Autowired
    JdbcUserDetailsManager jdbcUserDetailsManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
        .dataSource(datasource);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/register*").permitAll()
            .antMatchers("/auth*").permitAll()
            .antMatchers("/login**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/auth")
            .and()
            .logout()
            .permitAll();

        http.headers().frameOptions().sameOrigin();

    }

    @Bean
    UserDetailsManager usersDetailsManager(DataSource dataSource){
        
        UserDetails user = User.builder()
                               .username("admin")
                               .password(passwordEncoder().encode("admin"))
                               .roles("USER", "ADMIN")
                               .build();

        jdbcUserDetailsManager.createUser(user);
        return jdbcUserDetailsManager;
    }

    @Bean
    JdbcUserDetailsManager getjdbcUserManagetDetails(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}