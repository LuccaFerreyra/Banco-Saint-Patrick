package com.banco.Saint_Patrik.Controller;

import com.banco.Saint_Patrik.Entities.Card;
import com.banco.Saint_Patrik.Entities.Transaction;
import com.banco.Saint_Patrik.Entities.User;
import com.banco.Saint_Patrik.Errors.ServiceError;
import com.banco.Saint_Patrik.Services.CardService;
import com.banco.Saint_Patrik.Services.TransactionService;
import com.banco.Saint_Patrik.Services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;

    @Autowired
    private TransactionService transactionService;

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/newTransaction")
    public String newTransaction(ModelMap model, RedirectAttributes redirectAttributes) throws ServiceError {

        List<User> userList = userService.userListEnabled();
        model.addAttribute("userAlta", userList);

        List<Card> cardList = cardService.cardListEnabled();
        model.addAttribute("userAlta", cardList);
        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/newTransaction")
    public String newTransaction(ModelMap model,
            @RequestParam(required = false) String idUser,
            @RequestParam(required = false) String idUserDestiny,
            @RequestParam(required = false) String idCardOwn,
            @RequestParam(required = false) String idCardDestiy,
            @RequestParam(required = false) Double amount,
            RedirectAttributes redirectAttributes) {

        try {
            transactionService.newTransaction(idUser, idUserDestiny, idCardOwn, idCardDestiy, amount);
            redirectAttributes.addFlashAttribute("success", "LA TRANSACCIÓN SE GENERÓ DE MANERA EXITOSA");
            return "redirect:/";
        } catch (ServiceError e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("idUser", idUser);
            redirectAttributes.addFlashAttribute("idUserDestiny", idUserDestiny);
            redirectAttributes.addFlashAttribute("idCardOwn", idCardOwn);
            redirectAttributes.addFlashAttribute("idCardDestiy", idCardDestiy);
            redirectAttributes.addFlashAttribute("amount", amount);
            return "redirect:/";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/transactionByLast30Days/{idUser}")
    public String transactionByLast30Days(ModelMap model,
            @PathVariable String idUser,
            RedirectAttributes redirectAttributes) throws ServiceError {
        try {
            List<Transaction> listTransaction = transactionService.searchTransactionByLast30Days(idUser);
            model.addAttribute("last30Days", listTransaction);
            return "redirect:/";
        } catch (ServiceError e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("error", "HUBO UN ERROR AL MOSTRAR LAS TRANSACCIONES DE LOS ÚLTIMOS 30 DÍAS");
            return "redirect:/";
        }
    }
}
