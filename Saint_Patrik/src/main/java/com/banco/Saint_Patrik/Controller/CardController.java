package com.banco.Saint_Patrik.Controller;

import com.banco.Saint_Patrik.Entities.Transaction;
import com.banco.Saint_Patrik.Entities.User;
import com.banco.Saint_Patrik.Errors.ServiceError;
import com.banco.Saint_Patrik.Services.CardService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    /**
     * MÉTODO QUE MUESTRA EL SALDO DE UNA DE LAS TARJETAS DEL CLIENTE
     *
     * @param session
     * @param model
     * @param cardId
     * @return
     * @throws ServiceError
     */
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/credit")
    public String cardCredit(HttpSession session, ModelMap model, @RequestParam(required = false) String cardId) throws ServiceError {

        User login = (User) session.getAttribute("usersession"); //con esto si el id de login logueado viene nulo, no ejecuta el metodo
        if (login == null) {
            return "redirect:/login";
        }
        Double creditCard = cardService.searchcardAmountByIdCard(cardId);
        model.addAttribute("credit", creditCard);

        return "html";
    }

    /**
     * MÉTODO QUE MUESTRA UNA LISTA DE TODAS LAS TRANSACCIONES DE UNA TARJETA
     * DEL CLIENTE
     *
     * @param session
     * @param model
     * @param cardId
     * @return
     * @throws ServiceError
     */
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/transactions")
    public String cardTransactions(HttpSession session, ModelMap model, @RequestParam(required = false) String cardId) throws ServiceError {

        User login = (User) session.getAttribute("usersession"); //con esto si el id de login logueado viene nulo, no ejecuta el metodo
        if (login == null) {
            return "redirect:/login";
        }
        List<Transaction> allTransactions = cardService.searchAllTransactions(cardId);
        model.addAttribute("transactions", allTransactions);

        return "html";
    }

    /**
     * MÉTODO PARA DAR DE BAJA UNA TARJETA DEL CLIENTE (PARA USUARIOS ROL ADMIN)
     *
     * @param session
     * @param cardId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/disable")
    public String disableCard(HttpSession session, @RequestParam String cardId) {

        try {
            User login = (User) session.getAttribute("usersession");
            cardService.disable(login.getId(), cardId);
        } catch (ServiceError ex) {
            Logger.getLogger(CardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/cards/all";
    }

    /**
     * MÉTODO PARA DAR DE ALTA UNA TARJETA DEL CLIENTE (PARA USUARIOS ROL ADMIN)
     *
     * @param session
     * @param cardId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/enable")
    public String enableCard(HttpSession session, @RequestParam String cardId) {

        try {
            User login = (User) session.getAttribute("usersession");
            cardService.enable(login.getId(), cardId);
        } catch (ServiceError ex) {
            Logger.getLogger(CardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/cards/all";
    }
}
