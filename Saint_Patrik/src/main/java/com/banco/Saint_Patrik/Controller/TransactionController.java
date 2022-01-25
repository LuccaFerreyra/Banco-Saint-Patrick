package com.banco.Saint_Patrik.Controller;

import com.banco.Saint_Patrik.Entities.Card;
import com.banco.Saint_Patrik.Entities.Transaction;
import com.banco.Saint_Patrik.Entities.User;
import com.banco.Saint_Patrik.Errors.ServiceError;
import com.banco.Saint_Patrik.Services.CardService;
import com.banco.Saint_Patrik.Services.TransactionService;
import com.banco.Saint_Patrik.Services.UserService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_CLIENTE')")
@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;

    @Autowired
    private TransactionService transactionService;
    
    @GetMapping("/transactions")
    public String transactions(){
        return "transactions.html";
    }
    /**
     * MÉTODO PARA REALIZAR UNA TRANSACCIÓN
     *
     * METHOD FOR MAKING A TRANSACTION
     *
     * @param model
     * @param redirectAttributes
     * @return
     * @throws ServiceError
     */
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/newTransaction")
    public String newTransaction(ModelMap model, RedirectAttributes redirectAttributes) throws ServiceError {

        List<User> userList = userService.userListEnabled();
        model.addAttribute("userAlta", userList);

        List<Card> cardList = cardService.cardListEnabled();
        model.addAttribute("userAlta", cardList);
        return "redirect:/newTransaction";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/newTransaction")
    public String newTransaction(HttpSession session, ModelMap model,
            //@RequestParam(required = false) String idUser,
            @RequestParam(required = false) String idUserDestiny,
            @RequestParam(required = false) String idCardOwn,
            @RequestParam(required = false) String idCardDestiny,
            @RequestParam(required = false) Double amount,
            RedirectAttributes redirectAttributes) {

        try {
            User login = (User) session.getAttribute("cardSession");
            if (login == null) {
                return "redirect:/login";
            }

            String idUser = login.getId();

            transactionService.newTransaction(idUser, idUserDestiny, idCardOwn, idCardDestiny, amount);
            redirectAttributes.addFlashAttribute("success", "THE TRANSACTION WAS SUCCESSFULLY GENERATED");
            return "redirect:/newTransaction";
        } catch (ServiceError e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            //redirectAttributes.addFlashAttribute("idUser", idUser);
            redirectAttributes.addFlashAttribute("idUserDestiny", idUserDestiny);
            redirectAttributes.addFlashAttribute("idCardOwn", idCardOwn);
            redirectAttributes.addFlashAttribute("idCardDestiy", idCardDestiny);
            redirectAttributes.addFlashAttribute("amount", amount);
            return "redirect:/newTransaction";
        }
    }

    /**
     * MÉTODO QUE MUESTRA LAS TRANSACCIONES DE LOS ÚLTIMOS 30 DÍAS
     *
     * METHOD SHOWING TRANSACTIONS FROM THE LAST 30 DAYS
     *
     * @param session
     * @param model
     * @param redirectAttributes
     * @return
     * @throws ServiceError
     */
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    //@GetMapping("/transactionByLast30Days/{idUser}")
    @GetMapping("/transactionByLast30Days")
    public String transactionByLast30Days(HttpSession session, ModelMap model,
            //@PathVariable String idUser,
            RedirectAttributes redirectAttributes) throws ServiceError {
        try {
            User login = (User) session.getAttribute("cardSession");
            if (login == null) {
                return "redirect:/login";
            }

            String idUser = login.getId();

            List<Transaction> listTransaction = transactionService.searchTransactionByLast30Days(idUser);
            model.addAttribute("last30Days", listTransaction);
            return "redirect:/transactionByLast30Days";
        } catch (ServiceError e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("error", "THERE WAS AN ERROR DISPLAYING TRANSACTIONS FOR THE LAST 30 DAYS");
            return "redirect:/";
        }
    }
}
