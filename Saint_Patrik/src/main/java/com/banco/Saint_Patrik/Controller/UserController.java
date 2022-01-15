package com.banco.Saint_Patrik.Controller;

import com.banco.Saint_Patrik.Entities.User;
import com.banco.Saint_Patrik.Errors.ServiceError;
import com.banco.Saint_Patrik.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    //todas las cartas que posee un usuario
    @GetMapping("/cards")
    public String cards(ModelMap model, @Param("idUser") String idUser) {
        model.addAttribute("cards", service.cardsFromUser(idUser));

        return "html";
    }

    //LISTA DE USUARIOS HABILITADOS
    @GetMapping("/userList")
    public String usersEnabled(ModelMap model) {
        model.addAttribute("users", service.userListEnabled());
        return "html";
    }

    //LISTA DE USUARIOS DE BAJA
    @GetMapping("/usersDisabled")
    public String usersDisabled(ModelMap model) throws ServiceError {
        try {
            model.addAttribute("usersDisabled", service.userListDisabled());
        } catch (ServiceError e) {
            model.addAttribute("error", e.getMessage());
        }
        return "html";
    }

    //BUSCA UN USUARIO ESPECIFICO
    public String user(ModelMap model, @RequestParam String idUser) throws ServiceError {
        try {
            model.addAttribute("user", service.user(idUser));
        } catch (ServiceError e) {
            model.addAttribute("error", e.getMessage());
        }
        return "html";
    }

}