package com.banco.Saint_Patrik.Services;

import com.banco.Saint_Patrik.Entities.Card;
import com.banco.Saint_Patrik.Entities.Transaction;
import com.banco.Saint_Patrik.Errors.ServiceError;
import com.banco.Saint_Patrik.Repositories.CardRepository;
import com.banco.Saint_Patrik.Repositories.TransactionRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CardService implements UserDetailsService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * MÉTODO QUE MUESTRA EL SALDO DE UNA DE LAS TARJETAS DEL CLIENTE
     *
     * METHOD SHOWING THE BALANCE OF ONE OF THE CUSTOMER'S CARDS
     *
     * @param id
     * @return
     * @throws ServiceError
     */
    @Transactional(readOnly = true)
    public Double searchcardAmountByIdCard(String id) throws ServiceError {
        try {
            Card card = cardRepository.searchById(id);
            return card.getCredit();
        } catch (Exception e) {
            throw new ServiceError("THERE WAS AN ERROR DISPLAYING THE CARD BALANCE");
        }
    }

    /**
     * MÉTODO QUE MUESTRA UNA LISTA DE TODAS LAS TRANSACCIONES DE UNA TARJETA
     * DEL CLIENTE
     *
     * METHOD THAT DISPLAYS A LIST OF ALL TRANSACTIONS ON A CUSTOMER CARD
     *
     * @param id
     * @return
     * @throws ServiceError
     */
    @Transactional(readOnly = true)
    public List<Transaction> searchAllTransactions(String id) throws ServiceError {
        try {
            List<Transaction> listTransactions = transactionRepository.searchTransactionByCardId(id);
            return listTransactions;
        } catch (Exception e) {
            throw new ServiceError("THERE WAS AN ERROR DISPLAYING ALL TRANSACTIONS ON THIS CARD");
        }
    }

    /**
     * MÉTODO PARA DAR DE BAJA UNA TARJETA DEL CLIENTE (PARA USUARIOS ROL ADMIN)
     *
     * METHOD TO DISABLE A CLIENT CARD (FOR ADMIN ROLE USERS)
     *
     * @param idUser
     * @param cardId
     * @throws ServiceError
     */
    @Transactional
    public void disable(String idUser, String cardId) throws ServiceError {
        try {
            Card card = cardRepository.searchById(cardId);

            card.setEnabled(Boolean.FALSE);
            cardRepository.save(card);

        } catch (Exception e) {
            throw new ServiceError("THERE WAS AN ERROR DISABLING THE CARD");
        }
    }

    /**
     * MÉTODO PARA DAR DE ALTA UNA TARJETA DEL CLIENTE (PARA USUARIOS ROL ADMIN)
     *
     * METHOD TO ENABLE A CLIENT CARD (FOR ADMIN ROLE USERS)
     *
     * @param idUser
     * @param cardId
     * @throws ServiceError
     */
    @Transactional
    public void enable(String idUser, String cardId) throws ServiceError {
        try {
            Card card = cardRepository.searchById(cardId);

            card.setEnabled(Boolean.TRUE);
            cardRepository.save(card);
        } catch (Exception e) {
            throw new ServiceError("THERE WAS AN ERROR ENABLING THE CARD");
        }
    }

    /**
     * MÉTODO PARA MOSTRAR LAS TARJETAS POR ESTADO DE ALTAS
     *
     * METHOD FOR DISPLAYING ENABLED CARDS
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Card> cardListEnabled() {
        return cardRepository.searchCardByEnabled();
    }

    /**
     * MÉTODO PARA MOSTRAR LAS TARJETAS POR ESTADO DE BAJAS
     *
     * METHOD FOR DISPLAYING DISABLED CARDS
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Card> cardListDisabled() {
        return cardRepository.searchCardByDisabled();
    }

    /**
     *
     * @param cardNumber
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String cardNumber) throws UsernameNotFoundException {

        Card card = cardRepository.searchCardByNumberCard(cardNumber);

        if (card != null) {

            List<GrantedAuthority> permissions = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + card.getUser().getTypeRole());
            permissions.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            String bcrypt = new BCryptPasswordEncoder().encode(card.getPin());
            card.setPin(bcrypt);

            session.setAttribute("cardSession", card);

            String encript = new BCryptPasswordEncoder().encode(card.getPin());
            card.setPin(encript);

            User user = new User(card.getNumberCard(), encript, permissions);

            return user;

        } else {

            return null;
        }
    }
}
