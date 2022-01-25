package com.banco.Saint_Patrik.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/")
    public String home() {
        return "home.html";
    }

    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, 
            @RequestParam(required = false) String logout, ModelMap model) {
        
        if (error != null) {
            model.put("error", "INCORRECT USERNAME OR PASSWORD"); //hay que mostrarlo en la vista login.html
        }
        if (logout != null) {
            model.put("logout", "SUCCESSFULLY EXITED THE PLATFORM"); //hay que mostrarlo en la vista login.html
        }
        return "login.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/main")
    public String main() {
        return "index.html";
    }
    
    @GetMapping("/transactions")
    public String goTransactions() {
        return "transaction.html";
    }
}
