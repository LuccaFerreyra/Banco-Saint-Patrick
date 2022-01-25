package com.banco.Saint_Patrik;

import com.banco.Saint_Patrik.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SaintPatrikApplication {

    @Autowired
    CardService cardService;

    public static void main(String[] args) {
        SpringApplication.run(SaintPatrikApplication.class, args);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(cardService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
